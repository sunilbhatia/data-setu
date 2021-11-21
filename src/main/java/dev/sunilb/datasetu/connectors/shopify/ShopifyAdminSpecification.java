package dev.sunilb.datasetu.connectors.shopify;

import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsSpecification;

public class ShopifyAdminSpecification {
    private String queryRoot;
    private long rows;
    private String fieldNames;
    private String query;
    private String storeId;

    private ShopifyAdminSpecification() {
    }

    public static ShopifyAdminSpecification Builder() {
        return new ShopifyAdminSpecification();
    }

    public ShopifyAdminSpecification withQueryRoot(String queryRoot) {
        this.queryRoot = queryRoot;
        return this;
    }


    public ShopifyAdminSpecification build() {
        return new ShopifyAdminSpecification();
    }

    public ShopifyAdminSpecification first(long rows) {
        this.rows = rows;
        return this;
    }

    public ShopifyAdminSpecification withFields(String fieldNames) {
        this.fieldNames = fieldNames;
        return this;
    }

    public ShopifyAdminSpecification withQuery(String query) {
        this.query = query;
        return this;
    }

    public ShopifyAdminSpecification forStore(String storeId) {
        this.storeId = storeId;
        return this;
    }
}
