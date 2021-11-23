package dev.sunilb.datasetu.connectors.shopify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.sunilb.datasetu.connectors.DataSetuSource;
import dev.sunilb.datasetu.entities.Page;
import dev.sunilb.datasetu.entities.Records;
import dev.sunilb.datasetu.exceptions.DataSetuAPIThrottledException;
import dev.sunilb.datasetu.exceptions.DataSetuException;

public class ShopifyAdmin {

    private final DataSetuSource shopifySource;
    private Page page;
    private boolean hasNext;
    private ShopifyAdminCost cost;

    public ShopifyAdmin(ShopifySource shopifySource) {
        this.shopifySource = shopifySource;
    }

    private ShopifyAdminRecordsDeserializerResponse getShopifyAdminResponse() throws JsonProcessingException {
        String json = shopifySource.fetch();

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ShopifyAdminRecordsDeserializerResponse.class, new ShopifyAdminRecordsDeserializer(ShopifyAdminRecordsDeserializerResponse.class));
        mapper.registerModule(module);
        return mapper.readValue(json, ShopifyAdminRecordsDeserializerResponse.class);
    }

    public Records getRecords() throws DataSetuAPIThrottledException{

        ShopifyAdminRecordsDeserializerResponse shopifyResponse = null;
        Records records = null;

        try {
            shopifyResponse = getShopifyAdminResponse();

            if (shopifyResponse.isThrottled() == false && shopifyResponse.getApiErrorMessage() == null) {
                records = shopifyResponse.getRecords();
                this.page = shopifyResponse.getPage();
                this.shopifySource.updatePage(this.page);
                this.hasNext = shopifyResponse.hasNext();
                this.cost = shopifyResponse.getCostDetails();
            } else if (shopifyResponse.isThrottled() == false && shopifyResponse.getApiErrorMessage() != null) {
                throw new DataSetuException("API Failed with message: " + shopifyResponse.getApiErrorMessage());
            }
            else {
                throw new DataSetuAPIThrottledException("Got throttled from Shopify Admin API Exception : " + shopifyResponse.getApiErrorMessage());
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //TODO: Raise DataSetuException
        }

        return records;
    }

    public boolean hasNext() {
        if (this.page == null) return true; // should return true if the first cursor has not been fetched
        return this.hasNext;
    }

    public long getBalanceQueryCost() {
        return this.cost.currentlyAvailable;
    }

    public long getCurrentQueryCost() {
        return this.cost.actualQueryCost;
    }

    public long getRestoreRate() {
        return this.cost.restoreRate;
    }
}
