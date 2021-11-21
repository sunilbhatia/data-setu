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
                .withQuery("created_at:>2021-10-13")
                .build();

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

        /*AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("ga:date", "ga:users", "ga:newUsers", "ga:sessions", "ga:transactions", "ga:revenuePerTransaction");
        at.addRule();
        for (Row record : r) {
            at.addRow(record.valueOfField("ga:date"),
                    record.valueOfField("ga:users"),
                    record.valueOfField("ga:newUsers"),
                    record.valueOfField("ga:sessions"),
                    record.valueOfField("ga:transactions"),
                    record.valueOfField("ga:revenuePerTransaction")
            );
        }
        at.addRule();

        String records = at.render();
        System.out.println(records);*/
    }


}
