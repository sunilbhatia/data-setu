package dev.sunilb.datasetu.connectors.shopify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.sunilb.datasetu.connectors.DataSetuSource;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsRecordsDeserializer;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsRecordsDeserializerResponse;
import dev.sunilb.datasetu.entities.Records;

public class ShopifyAdmin {

    private final DataSetuSource shopifySource;

    public ShopifyAdmin(ShopifySource shopifySource) {
        this.shopifySource = shopifySource;
    }

    private ShopifyAdminRecordsDeserializerResponse getShopifyAdminResponse() throws JsonProcessingException {
//        String json = "{\"data\": {\"orders\": {\"fname\": \"Sunil\", \"lname\": \"Bhatia\"}}}"; //shopifySource.fetch();
        String json =  shopifySource.fetch();
//        System.out.println(json);

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ShopifyAdminRecordsDeserializerResponse.class, new ShopifyAdminRecordsDeserializer(ShopifyAdminRecordsDeserializerResponse.class));
        mapper.registerModule(module);

        return mapper.readValue(json, ShopifyAdminRecordsDeserializerResponse.class);
    }

    public Records getRecords() {

        ShopifyAdminRecordsDeserializerResponse shopifyResponse = null;
        Records records = null;

        try {
            shopifyResponse = getShopifyAdminResponse();
            records = shopifyResponse.getRecords();

//            this.page = gaResponse.getPage();
//            this.gaSource.updatePage(this.page);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //TODO: Raise DataSetuException
        }

        return records;
    }
}
