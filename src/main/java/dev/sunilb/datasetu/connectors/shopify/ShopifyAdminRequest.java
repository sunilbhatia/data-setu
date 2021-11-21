package dev.sunilb.datasetu.connectors.shopify;

import java.util.Map;

public class ShopifyAdminRequest {

    private final String shopifyRequestBody;
    private final String shopifyAdminAPI;
    private final Map<String, String> headers;

    public ShopifyAdminRequest(String shopifyRequestBody, String shopifyAdminAPI, Map<String, String> headers) {
        this.shopifyRequestBody = shopifyRequestBody;
        this.shopifyAdminAPI = shopifyAdminAPI;
        this.headers = headers;
    }

    public String getRequestJsonBody() {
        return shopifyRequestBody;
    }

    public String getShopifyAdminAPI() {
        return shopifyAdminAPI;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
