package dev.sunilb.examples.shopify;

import dev.sunilb.datasetu.connectors.shopify.ShopifyAdmin;
import dev.sunilb.datasetu.connectors.shopify.ShopifyAdminSpecification;
import dev.sunilb.datasetu.connectors.shopify.ShopifySource;
import dev.sunilb.datasetu.entities.Records;
import redis.clients.jedis.Jedis;

public class ShopifyAdminApp {
    public static void main(String[] args) {
        System.out.println("Please populate REDIS with these keys");
        System.out.println("1) \"X-Shopify-Access-Token\"\n" +
                "2) \"store-id\"\n");

        basicIntegrationTest();

    }

    private static void basicIntegrationTest() {
        Jedis jedis = new Jedis();
        String shopifyAccessToken = jedis.get("X-Shopify-Access-Token");
        String storeId = jedis.get("store-id");

        ShopifyAdminSpecification shopifyAdminSpecification = ShopifyAdminSpecification.Builder()
                .forStore(storeId)
                .withQueryRoot("orders")
                .first(100)
                .withFields("id name discountCode")
                .withQuery("created_at:>2020-01-01");

        ShopifySource sSource = ShopifySource.Builder()
                .withAuthToken(shopifyAccessToken)
                .withSpecification(shopifyAdminSpecification)
                .build();

        ShopifyAdmin sa = new ShopifyAdmin(sSource);
        Records r = sa.getRecords();
        System.out.println(r.count());
        for (int i = 0; i < r.count(); i++) {
            System.out.println(r.getRow(i));
        }
    }


}
