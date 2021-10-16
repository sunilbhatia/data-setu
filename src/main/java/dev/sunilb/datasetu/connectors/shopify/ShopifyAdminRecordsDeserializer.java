package dev.sunilb.datasetu.connectors.shopify;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsRecordsDeserializerResponse;
import dev.sunilb.datasetu.entities.Records;
import dev.sunilb.datasetu.entities.Row;

import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ShopifyAdminRecordsDeserializer extends StdDeserializer<ShopifyAdminRecordsDeserializerResponse> {

    public ShopifyAdminRecordsDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ShopifyAdminRecordsDeserializerResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        String queryRoot = getQueryRoot(rootNode);
        boolean hasNext = hasNext(rootNode, queryRoot);
        JsonNode edgesArray = rootNode.get("data").get(queryRoot).get("edges");
        String nextPageCursor = getNextPageCursor(edgesArray);
        List<Map<String, Object>> results = processEdgesAndGetResults(edgesArray);
        Records records = convertResultsToRecords(results);
        ShopifyAdminRecordsDeserializerResponse response = new ShopifyAdminRecordsDeserializerResponse(records);
        return response;
    }

    private Records convertResultsToRecords(List<Map<String, Object>> results) {
        List<String> fieldList = getFieldsList(results);
        Records records = new Records(fieldList);

        results.forEach(resultMap -> {
            List<String> dataList = new ArrayList<>();
            fieldList.forEach(fieldName -> {
                String data = null;
                if(fieldName.endsWith(".edges")) {
                    data = mergeDataEdges((List<Map<String, Object>>) resultMap.get(fieldName));
                } else {
                    data = (String) resultMap.get(fieldName);
                }
                dataList.add(data);
            });

            String[] rowData = dataList.toArray(new String[0]);
            records.insert(rowData);
        });

        return records;
    }

    private String mergeDataEdges(List<Map<String, Object>> edges) {
        return "";
    }

    private List<String> getFieldsList(List<Map<String, Object>> results) {
        List<String> fieldsList = new ArrayList<>(results.get(0).keySet());
        return fieldsList;
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
            JsonNode edgeObject = edgesArray.get(index).get("node");
            Iterator<Map.Entry<String, JsonNode>> fieldIterator = edgeObject.fields();
            Map<String, Object> result = new HashMap<>();
            while (fieldIterator.hasNext()) {
                Map.Entry<String, JsonNode> entry = fieldIterator.next();
                String fieldName = entry.getKey();
                JsonNode value = entry.getValue();

                if (isString(value)) {
                    result.put(fieldName, value.asText());
                } else if (isArray(value)) {
                    processEdgesAndGetResults(value);
                } else if (isObject(value)) {
                    Map<String, Object> valueMap = getObjectDetails(value);
                    valueMap.forEach((objKey, objValue) -> {
                        result.put(fieldName + "." + objKey, objValue);
                    });
                }
            }

            results.add(result);

        }

        return results;

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
        JsonNode pageInfoNode = node.get("data").get("orders").get("pageInfo");
        if (pageInfoNode == null) return false;
        return pageInfoNode.get("hasNextPage").asBoolean();
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
