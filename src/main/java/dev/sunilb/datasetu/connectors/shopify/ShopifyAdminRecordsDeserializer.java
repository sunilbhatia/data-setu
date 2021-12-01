package dev.sunilb.datasetu.connectors.shopify;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import dev.sunilb.datasetu.entities.Page;
import dev.sunilb.datasetu.entities.Records;
import dev.sunilb.datasetu.exceptions.DataSetuAPIThrottledException;

import java.io.IOException;
import java.util.*;

public class ShopifyAdminRecordsDeserializer extends StdDeserializer<ShopifyAdminRecordsDeserializerResponse> {

    private final String printableFieldNames;

    public ShopifyAdminRecordsDeserializer(Class<?> vc, String printableFieldNames) {
        super(vc);
        this.printableFieldNames = printableFieldNames;
    }

    @Override
    public ShopifyAdminRecordsDeserializerResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        return initiateProcessing(rootNode);
    }

    private ShopifyAdminRecordsDeserializerResponse initiateProcessing(JsonNode rootNode) {

        ShopifyAdminRecordsDeserializerResponse response;

        if (rootNode.get("errors") != null) {
            String message = rootNode.get("errors").get(0).get("message").asText();

            if (message.equals("Throttled"))
                response = new ShopifyAdminRecordsDeserializerResponse(true, message);
            else
                response = new ShopifyAdminRecordsDeserializerResponse(false, message);

        } else {

            String queryRoot = getQueryRoot(rootNode);
            ShopifyAdminCost cost = getCostDetails(rootNode);
            JsonNode edgesArray = rootNode.get("data").get(queryRoot).get("edges");
            String nextPageCursor = getNextPageCursor(edgesArray);
            List<Map<String, Object>> results = processEdgesAndGetResults(edgesArray);
            Records records = convertResultsToRecords(results);
            Page page = new Page(records.count(), nextPageCursor);
            boolean hasNext = hasNext(rootNode, queryRoot);
            response = new ShopifyAdminRecordsDeserializerResponse(records, page, cost, hasNext);

        }
        return response;
    }

    private Records convertResultsToRecords(List<Map<String, Object>> results) {

        if (results.size() == 0) return new Records();

        List<String> fieldList = getFieldsList(results);
        Records records = new Records(fieldList);

        results.forEach(resultMap -> {
            List<String> dataList = new ArrayList<>();
            fieldList.forEach(fieldName -> {
                String data = null;
                if (fieldName.endsWith(".edges")) {
                    data = mergeDataEdges((List<Map<String, Object>>) resultMap.get(fieldName));
                } else {
                    data = (String) resultMap.get(fieldName);
                    data = (data == null) ? "" : data;
                }
                dataList.add(data);
            });

            String[] rowData = dataList.toArray(new String[0]);
            records.insert(rowData);
        });

        return records;
    }

    private String mergeDataEdges(List<Map<String, Object>> edges) {

        StringBuffer fieldBuffer = new StringBuffer();
        fieldBuffer.append("~~~");
        edges.forEach(mapOfData -> {
            StringBuffer objectData = new StringBuffer();
            objectData.append("^^^");
            mapOfData.forEach((key, value) -> {
                objectData.append(key);
                objectData.append("=");
                objectData.append((String) value);
                objectData.append("^^^");
            });
            fieldBuffer.append(objectData);
            fieldBuffer.append("~~~");
        });

        return fieldBuffer.toString();
    }

    private List<String> getFieldsList(List<Map<String, Object>> results) {

        List<String> fieldsList = null;
        if (this.printableFieldNames.equals("")) {
            Set<String> maxFieldSet = getMaxFieldList(results);
            fieldsList = new ArrayList<>(maxFieldSet);
        } else {
            fieldsList = getPrintableFieldList();
        }

        return fieldsList;
    }

    private List<String> getPrintableFieldList() {
        List<String> fieldsList = new ArrayList<>();
        String[] fields = this.printableFieldNames.split(",");
        fieldsList.addAll(List.of(fields));
        return fieldsList;
    }

    // This is needed as the shape of all rows may be different
    // hence we need the max set for building the table
    private Set<String> getMaxFieldList(List<Map<String, Object>> results) {
        Set<String> maxFieldSet = null;
        int maxFieldLength = 0;
        int mapSize = results.size();
        for (int i = 0; i < mapSize; i++) {
            int currentKeySize = results.get(i).keySet().size();
            if (currentKeySize > maxFieldLength) {
                maxFieldLength = currentKeySize;
                maxFieldSet = results.get(i).keySet();
            }
        }
        return maxFieldSet;
    }

    private String getNextPageCursor(JsonNode edgesArray) {
        String nextPageCursor = null;
        int arrayLength = edgesArray.size();
        JsonNode lastElement = edgesArray.get(arrayLength - 1);
        if (lastElement != null) {
            JsonNode cursor = edgesArray.get(arrayLength - 1).get("cursor");
            if (cursor != null) {
                nextPageCursor = cursor.asText();
            }
        }
        return nextPageCursor;
    }

    private List<Map<String, Object>> processEdgesAndGetResults(JsonNode edgesArray) {

        int arrayLength = edgesArray.size();
        List<Map<String, Object>> results = new ArrayList<>();
        for (int index = 0; index < arrayLength; index++) {
            JsonNode edgeObject = getEdgeNodeOrFirstObject(edgesArray, index);
            Iterator<Map.Entry<String, JsonNode>> fieldIterator = edgeObject.fields();
            Map<String, Object> result = new HashMap<>();
            while (fieldIterator.hasNext()) {
                Map.Entry<String, JsonNode> entry = fieldIterator.next();
                String fieldName = entry.getKey();
                JsonNode value = entry.getValue();

                if (value.isNull()) {
                    result.put(fieldName, "");
                } else if (isString(value)) {
                    result.put(fieldName, value.asText());
                } else if (isArray(value)) {
                    List<Map<String, Object>> resultArray = processEdgesAndGetResults(value);
                    resultArray.forEach(stringObjectMap -> {
                        Set<String> mapKeySet = stringObjectMap.keySet();
                        mapKeySet.forEach(key -> {
                            result.put(fieldName + "." + key, stringObjectMap.get(key));
                        });
                    });
                } else if (isObject(value)) {
                    Map<String, Object> valueMap = getObjectDetails(value);
                    valueMap.forEach((objKey, objValue) -> {
                        result.put(fieldName + "." + objKey, objValue);
                    });
                } else {
                    result.put(fieldName, value.asText());
                }
            }

            results.add(result);

        }

        return results;

    }

    private JsonNode getEdgeNodeOrFirstObject(JsonNode edgesArray, int index) {

        JsonNode node = edgesArray.get(index).get("node");

        if (node == null) {
            // due to refunds[] that does not have 'node' but 'totalRefundSet' hence if node is not there
            // then get the first key, which in our case is 'totalRefundSet
            String firstKeyInObject = edgesArray.get(index).fields().next().getKey();
            node = edgesArray.get(index).get(firstKeyInObject);
        }

        return node;
    }

    private boolean isArray(JsonNode value) {
        return value.getNodeType().name().equals("ARRAY");
    }

    private boolean isObject(JsonNode value) {
        return value.getNodeType().name().equals("OBJECT");
    }

    private boolean isString(JsonNode value) {
        return value.getNodeType().name().equals("STRING");
    }

    private Map<String, Object> getObjectDetails(JsonNode node) {
        Map<String, Object> objectMap = new HashMap<>();
        Iterator<Map.Entry<String, JsonNode>> fieldIterator = node.fields();
        while (fieldIterator.hasNext()) {
            Map.Entry<String, JsonNode> data = fieldIterator.next();
            if (isArray(data.getValue())) {
                objectMap.put(data.getKey(), processEdgesAndGetResults(data.getValue()));
            } else if (isObject(data.getValue())) {
                Map<String, Object> subObjectMap = getObjectDetails(data.getValue());
                subObjectMap.forEach((key, value) -> {
                    objectMap.put(data.getKey() + "." + key, value);
                });
            } else {
                objectMap.put(data.getKey(), data.getValue().asText());
            }
        }
        return objectMap;
    }

    private boolean hasNext(JsonNode node, String queryRoot) {
        JsonNode pageInfoNode = node.get("data").get(queryRoot).get("pageInfo");
        if (pageInfoNode == null) return false;
        return pageInfoNode.get("hasNextPage").asBoolean();
    }

    private ShopifyAdminCost getCostDetails(JsonNode node) {
        JsonNode costNode = node.get("extensions").get("cost");
        if (costNode == null) return null;

        ShopifyAdminCost cost = new ShopifyAdminCost();
        cost.actualQueryCost = costNode.get("actualQueryCost").asInt();
        cost.currentlyAvailable = costNode.get("throttleStatus").get("currentlyAvailable").asInt();
        cost.restoreRate = costNode.get("throttleStatus").get("restoreRate").asInt();

        return cost;
    }

    private String getQueryRoot(JsonNode rootNode) {

        String queryRoot = null;

        Iterator dataIterator = rootNode.get("data").fields();
        while (dataIterator.hasNext()) {
            Map.Entry<String, JsonNode> jsonEntry = (Map.Entry<String, JsonNode>) dataIterator.next();
            queryRoot = jsonEntry.getKey();
            break; //as the query root is at the top and we do not expect a second iteration, still added for any future side effects
        }

        return queryRoot;

    }

}
