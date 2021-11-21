package dev.sunilb.datasetu.connectors.shopify;

import dev.sunilb.datasetu.connectors.DataSetuSource;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsSource;
import dev.sunilb.datasetu.entities.Page;

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
        return null;
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
