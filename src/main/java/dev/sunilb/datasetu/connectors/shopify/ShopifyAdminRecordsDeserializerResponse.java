package dev.sunilb.datasetu.connectors.shopify;

import dev.sunilb.datasetu.entities.Records;

public class ShopifyAdminRecordsDeserializerResponse {

    private Records records;

    public ShopifyAdminRecordsDeserializerResponse(Records records) {
        this.records = records;
    }

    public Records getRecords() {
        return this.records;
    }
}
