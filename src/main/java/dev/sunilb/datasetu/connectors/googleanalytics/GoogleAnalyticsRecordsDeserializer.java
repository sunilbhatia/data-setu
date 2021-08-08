package dev.sunilb.datasetu.connectors.googleanalytics;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import dev.sunilb.datasetu.Page;
import dev.sunilb.datasetu.entities.Records;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleAnalyticsRecordsDeserializer extends StdDeserializer<GoogleAnalyticsRecordsDeserializerResponse> {

    public GoogleAnalyticsRecordsDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public GoogleAnalyticsRecordsDeserializerResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

        Records records = convertJsonToRecords(rootNode, extractFields(rootNode));
        Page page = convertJsonToPage(rootNode);

        return new GoogleAnalyticsRecordsDeserializerResponse(records, page);

//        return records;
    }

    private Page convertJsonToPage(JsonNode node) {
        JsonNode rowsNode = node.get("reports").get(0).get("data").get("rowCount");
        JsonNode nextPageTokenNode = node.get("reports").get(0).get("nextPageToken");

        int rowCount = 0;
        if (rowsNode != null)
            Integer.parseInt(rowsNode.textValue());

        String nextPageToken = null;
        if (nextPageTokenNode != null)
            nextPageToken = nextPageTokenNode.textValue();

        return new Page(rowCount, nextPageToken);
    }

    private Records convertJsonToRecords(JsonNode rootNode, List<String> fields) {

        Records records = new Records(fields);

        JsonNode rowsNode = rootNode.get("reports").get(0).get("data").get("rows");

        if (doesRecordsExist(rowsNode)) {
            for (JsonNode row : rowsNode) {
                ArrayList<String> data = new ArrayList<>();
                for (JsonNode dimension : row.get("dimensions")) {
                    data.add(dimension.textValue());
                }

                for (JsonNode metric : row.get("metrics").get(0).get("values")) {
                    data.add(metric.textValue());
                }

                String[] rowData = new String[data.size()];
                records.insert(data.toArray(rowData));
            }
        }

        return records;

    }

    private boolean doesRecordsExist(JsonNode rowsNode) {
        return !(rowsNode == null);
    }

    private List<String> extractFields(JsonNode rootNode) {

        ArrayList<String> fields = new ArrayList<>();
        JsonNode columnNode = rootNode.get("reports").get(0).get("columnHeader");

        for (JsonNode dimension : columnNode.get("dimensions")) {
            fields.add(dimension.textValue());
        }

        for (JsonNode metric : columnNode.get("metricHeader").get("metricHeaderEntries")) {
            fields.add(metric.get("name").textValue());
        }

        return fields;
    }
}
