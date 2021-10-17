package dev.sunilb.datasetu.connectors.shopify;

import dev.sunilb.datasetu.connectors.DataSetuSource;
import dev.sunilb.datasetu.entities.Page;

public class ShopifySource implements DataSetuSource {

    private Page page;

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
}
