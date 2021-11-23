package dev.sunilb.datasetu.connectors.shopify;

import dev.sunilb.datasetu.entities.Page;
import dev.sunilb.datasetu.entities.Records;

public class ShopifyAdminRecordsDeserializerResponse {

    private boolean isThrottled;
    private String throttledMessage;
    private Records records;
    private Page page;
    private ShopifyAdminCost cost;
    private boolean hasNext;

    public ShopifyAdminRecordsDeserializerResponse(boolean isThrottled, String throttledMessage) {
        this.isThrottled = isThrottled;
        this.throttledMessage = throttledMessage;
    }

    public ShopifyAdminRecordsDeserializerResponse(Records records, Page page, ShopifyAdminCost cost, boolean hasNext) {
        this.records = records;
        this.page = page;
        this.cost = cost;
        this.hasNext = hasNext;
    }

    public Records getRecords() {
        return this.records;
    }

    public Page getPage() {
        return this.page;
    }

    public ShopifyAdminCost getCostDetails() {
        return this.cost;
    }

    public boolean isThrottled() {
        return this.isThrottled;
    }

    public String getThrottledMessage() {
        return this.throttledMessage;
    }

    public boolean hasNext() {
        return this.hasNext;
    }
}
