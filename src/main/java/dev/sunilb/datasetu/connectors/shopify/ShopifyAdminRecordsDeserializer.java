package dev.sunilb.datasetu.connectors.shopify;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsRecordsDeserializerResponse;

import javax.swing.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class ShopifyAdminRecordsDeserializer extends StdDeserializer<ShopifyAdminRecordsDeserializerResponse> {

    public ShopifyAdminRecordsDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ShopifyAdminRecordsDeserializerResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

        String queryRoot = getQueryRoot(rootNode);

        String a = rootNode.getNodeType().name();
        String b = rootNode.get("data").getNodeType().name();
        rootNode.get("data").fields().forEachRemaining(a1 -> {
            System.out.println(a1.getKey());
        });
        String c = rootNode.get("data").get("orders").getNodeType().name();
        rootNode.get("data").fields().forEachRemaining(a2 -> {
            System.out.println("+++ " + a2.getKey());
        });
        String d = rootNode.get("data").get("orders").get("fname").getNodeType().name();
        String s = rootNode.get("data").get("fname").asText();
        System.out.println(rootNode.get("data").asText());
        return null;
    }

    private String getQueryRoot(JsonNode rootNode) {

        String queryRoot = null;

        Iterator dataIterator = rootNode.get("data").fields();
        while(dataIterator.hasNext()) {
            Map.Entry <String, JsonNode> jsonEntry = (Map.Entry<String, JsonNode>) dataIterator.next();
            queryRoot = jsonEntry.getKey();
            break; //as the query root is at the top and we do not expect a second iteration, still added for any future side effects
        }

        return queryRoot;

    }

}
