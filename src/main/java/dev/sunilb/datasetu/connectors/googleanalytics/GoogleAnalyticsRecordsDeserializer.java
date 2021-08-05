package dev.sunilb.datasetu.connectors.googleanalytics;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import dev.sunilb.datasetu.entities.Records;

import java.io.IOException;

public class GoogleAnalyticsRecordsDeserializer extends StdDeserializer<Records> {

    public GoogleAnalyticsRecordsDeserializer(Class<?> vc) {
        super(vc);
    }

    protected GoogleAnalyticsRecordsDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected GoogleAnalyticsRecordsDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public Records deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        System.out.println(node.get("reports"));
        /*int id = (Integer) ((IntNode) node.get("id")).numberValue();
        String itemName = node.get("itemName").asText();
        int userId = (Integer) ((IntNode) node.get("createdBy")).numberValue();*/

        return new Records();
    }
}
