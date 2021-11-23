package dev.sunilb.datasetu.connectors.shopify;

import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsRequest;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsSpecification;
import dev.sunilb.datasetu.entities.Page;

import java.util.HashMap;
import java.util.Map;

public class ShopifyAdminSpecification {
    private String queryRoot;
    private long rows;
    private String fieldNames;
    private String query;
    private String storeId;
    private Page page;

    private ShopifyAdminSpecification() {
    }

    public static ShopifyAdminSpecification Builder() {
        return new ShopifyAdminSpecification();
    }

    public ShopifyAdminSpecification withQueryRoot(String queryRoot) {
        this.queryRoot = queryRoot;
        return this;
    }

    public void updateCursor(Page page) {
        this.page = page;
    }

    public String build() {
        String shopifyAdminRequestBody = getJsonRequestBody();
        return shopifyAdminRequestBody;
    }

    private String getJsonRequestBody() {

        ShopifyRequestBuilder builder = ShopifyRequestBuilder.Builder()
                .first(this.rows)
                .withQueryRoot(this.queryRoot)
                .withFields(this.fieldNames)
                .withQuery(this.query);

        if(this.page != null) {
            builder = builder.withNextPageCursor(this.page.getNextPageToken());
        }

        String jsonRequestBody = builder.build();

        System.out.println(jsonRequestBody);

        return jsonRequestBody;
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

    public String getStoreId() {
        return this.storeId;
    }
}
