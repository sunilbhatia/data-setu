package dev.sunilb.datasetu.connectors.shopify;

import dev.sunilb.datasetu.connectors.DataSetuSource;
import dev.sunilb.datasetu.entities.Page;

public class ShopifySource implements DataSetuSource {
    public String fetch() {
        return null;
    }

    @Override
    public void updatePage(Page page) {

    }

    @Override
    public String renewAuthToken(String clientId, String clientSecret, String gaRefreshToken) {
        return null;
    }
}
