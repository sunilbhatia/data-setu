package dev.sunilb.datasetu.connectors.shopify;

import dev.sunilb.datasetu.entities.Page;
import dev.sunilb.datasetu.entities.Records;

public class ShopifyAdminRecordsDeserializerResponse {

    private Records records;
    Page page;

    public ShopifyAdminRecordsDeserializerResponse(Records records, Page page) {
        this.records = records;
    }

    public Records getRecords() {
        return this.records;
    }

    public Page getPage() {
        return this.page;
    }
}
