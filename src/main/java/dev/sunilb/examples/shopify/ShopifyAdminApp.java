package dev.sunilb.examples.shopify;

import dev.sunilb.datasetu.connectors.shopify.ShopifyAdmin;
import dev.sunilb.datasetu.connectors.shopify.ShopifyAdminSpecification;
import dev.sunilb.datasetu.connectors.shopify.ShopifySource;
import dev.sunilb.datasetu.entities.Records;
import dev.sunilb.datasetu.exceptions.DataSetuAPIThrottledException;
import redis.clients.jedis.Jedis;

import java.util.Date;

public class ShopifyAdminApp {
    public static void main(String[] args) {
        System.out.println("Please populate REDIS with these keys");
        System.out.println("1) \"X-Shopify-Access-Token\"\n" +
                "2) \"store-id\"\n");

        basicIntegrationTest();

    }

    private static void basicIntegrationTest() {

        Date t1 = new Date();

        Jedis jedis = new Jedis();
        String shopifyAccessToken = jedis.get("X-Shopify-Access-Token");
        String storeId = jedis.get("store-id");

        ShopifyAdminSpecification shopifyAdminSpecification = ShopifyAdminSpecification.Builder()
                .forStore(storeId)
                .withQueryRoot("customers")
                .first(50)
                .withFields("id firstName lastName email phone acceptsMarketing createdAt updatedAt");
//                .withQuery("updated_at:>'2020-01-01'");

        ShopifySource sSource = ShopifySource.Builder()
                .withAuthToken(shopifyAccessToken)
                .withSpecification(shopifyAdminSpecification)
                .build();

        String printableFieldNames = "id,firstName,lastName,email,phone,acceptsMarketing,createdAt,updatedAt";

        ShopifyAdmin sa = new ShopifyAdmin(sSource, printableFieldNames);

        while (sa.hasNext()) {

            try {

                Records r = sa.getRecords();
                String fields[] = r.getFields();
                for (int i = 0; i < fields.length; i++) {
                    System.out.println(fields[i]);
                }
                System.out.println(sa.getBalanceQueryCost() + ":" + sa.getCurrentQueryCost() + ":" + sa.getRestoreRate());

                for (int i = 0; i < r.count(); i++) {
                    System.out.println(r.getRow(i));
                }

                if (sa.shouldWait()) {
                    try {
                        System.out.println("\n\nSleeping for 20 seconds as we will have a cost over run\n\n");
                        Thread.sleep(20000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    try {
                        System.out.println("Sleeping for 5 second after writing file...");
                        Thread.sleep(5000);
                    } catch (InterruptedException e1) {
                        System.out.println("Interrupted 5 second thread sleep");
                        System.out.println(e1);
                    }
                }


            } catch (DataSetuAPIThrottledException e) {
                try {
                    System.out.println("\n\nSleeping for 20 seconds as we have already Throttled...\n\n");
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }

        }

        Date t2 = new Date();
        System.out.println("Time taken to execute : " + (t2.getTime() - t1.getTime()));

    }


}
