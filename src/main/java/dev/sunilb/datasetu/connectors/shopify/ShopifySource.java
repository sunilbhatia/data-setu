package dev.sunilb.datasetu.connectors.shopify;

import dev.sunilb.datasetu.connectors.DataSetuSource;
import dev.sunilb.datasetu.entities.Page;

import java.util.HashMap;
import java.util.Map;

public class ShopifySource implements DataSetuSource {

    private Page page;
    private String shopifyAccessToken;
    private ShopifyAdminSpecification shopifyAdminSpecification;

    private void ShopifySource() {
    }

    public static ShopifySource Builder() {
        return new ShopifySource();
    }

    public String fetch() {
        ShopifyAdminRequest shopifyRequest =  getShopifyAdminRequest();
        String shopifyAdminJson = ShopifyAdminService.executeAndGetData(shopifyRequest);
        return shopifyAdminJson;
    }

    private ShopifyAdminRequest getShopifyAdminRequest() {
        shopifyAdminSpecification.updateCursor(this.page);
        String shopifyAdminRequestBody = shopifyAdminSpecification.build();
        String apiPath = getShopifyAdminAPI();
        Map<String, String> headers = getShopifyAdminHeaders();

        ShopifyAdminRequest sAdminRequest = new ShopifyAdminRequest(shopifyAdminRequestBody, apiPath, headers);
        return sAdminRequest;
    }

    private Map<String, String> getShopifyAdminHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Shopify-Access-Token", this.shopifyAccessToken);
        return headers;
    }

    private String getShopifyAdminAPI() {
        String apiURL = "https://" + this.shopifyAdminSpecification.getStoreId() + ".myshopify.com/admin/api/2021-07/graphql.json";
        return apiURL;
    }

    @Override
    public void updatePage(Page page) {
        this.page = page;
    }

    @Override
    public String renewAuthToken(String clientId, String clientSecret, String gaRefreshToken) {
        return null;
    }

    public ShopifySource build() {
        return this;
    }

    public ShopifySource withAuthToken(String shopifyAccessToken) {
        this.shopifyAccessToken = shopifyAccessToken;
        return this;
    }

    public ShopifySource withSpecification(ShopifyAdminSpecification shopifyAdminSpecification) {
        this.shopifyAdminSpecification = shopifyAdminSpecification;
        return this;
    }
}
