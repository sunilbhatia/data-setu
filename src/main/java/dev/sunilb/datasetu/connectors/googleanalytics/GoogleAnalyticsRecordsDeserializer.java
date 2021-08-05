package dev.sunilb.datasetu.connectors.googleanalytics;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import dev.sunilb.datasetu.entities.Records;
import dev.sunilb.datasetu.entities.Row;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleAnalyticsRecordsDeserializer extends StdDeserializer<Records> {

    public GoogleAnalyticsRecordsDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Records deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        List<String> fields = extractFields(node);
        Records records = new Records(fields);

        insertRows(node, records);

        return records;
    }

    private void insertRows(JsonNode rootNode, Records records) {

        JsonNode rowsNode = rootNode.get("reports").get(0).get("data").get("rows");

        ArrayList<String> data = new ArrayList<>();
        for(JsonNode row: rowsNode) {
            for(JsonNode dimension: row.get("dimensions")) {
                data.add(dimension.textValue());
            }

            for(JsonNode metric: row.get("metrics")) {
                data.add(metric.get("values").get(0).textValue());
            }

            String []rowData = new String[data.size()];
            records.insert(data.toArray(rowData));
        }

    }

    private List<String> extractFields(JsonNode rootNode) {

        ArrayList<String> fields = new ArrayList<>();
        JsonNode columnNode = rootNode.get("reports").get(0).get("columnHeader");

        for(JsonNode dimension: columnNode.get("dimensions")) {
            fields.add(dimension.textValue());
        }

        for(JsonNode metric: columnNode.get("metricHeader").get("metricHeaderEntries")) {
            fields.add(metric.get("name").textValue());
        }

        return fields;
    }
}
