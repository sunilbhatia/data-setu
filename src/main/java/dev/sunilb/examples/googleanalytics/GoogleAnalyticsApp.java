package dev.sunilb.examples.googleanalytics;

import de.vandermeer.asciitable.AsciiTable;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalytics;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsSource;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsSpecification;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAuthentication;
import dev.sunilb.datasetu.entities.Records;
import dev.sunilb.datasetu.entities.Row;
import dev.sunilb.datasetu.exceptions.DataSetuAuthException;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.List;

public class GoogleAnalyticsApp {

    public static void main(String[] args) {
        System.out.println("Please populate REDIS with these keys");
        System.out.println("1) \"ga-token\"\n" +
                "2) \"ga-client-id\"\n" +
                "3) \"ga-client-secret\"\n" +
                "4) \"ga-refresh-token\"\n" +
                "5) \"ga-viewid\"");

        GoogleAnalyticsApp.basicIntegrationTest();
//        GoogleAnalyticsApp.basicIntegrationAuthExpiryTest();
//        GoogleAnalyticsApp.basicIntegrationAuthExpiryTest();
//        GoogleAnalyticsApp.basicIntegrationAuthExpiryWithPaginationTest();
    }

    private static void basicIntegrationTest() {
        Jedis jedis = new Jedis();
        String gaAuthToken = jedis.get("ga-token");
        String gaViewId = jedis.get("ga-viewid");

        GoogleAuthentication gaAuth = new GoogleAuthentication(gaAuthToken);
        GoogleAnalyticsSpecification gaSpecification = GoogleAnalyticsSpecification.Builder()
                .forView(gaViewId)
                .forDateRange("2021-01-01", "2021-01-03")
                .dimensions(Arrays.asList("ga:date"))
                .metrics(Arrays.asList("ga:users, ga:newUsers, ga:sessions, ga:bounces, ga:sessionDuration, ga:transactions, ga:transactionRevenue, ga:revenuePerTransaction".split(",")))
                .withAuthentication(gaAuth)
                .pageSize(100);

        GoogleAnalyticsSource gaSource = GoogleAnalyticsSource.Builder()
                .withAuth(gaAuth)
                .withSpecification(gaSpecification)
                .build();

        GoogleAnalytics ga = new GoogleAnalytics(gaSource);
        Records r = ga.getRecords();
        System.out.println(r.count());

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

    private static void basicIntegrationAuthExpiryTest() {
        Jedis jedis = new Jedis();
        String gaAuthToken = jedis.get("ga-token");
        String gaViewId = jedis.get("ga-viewid");
        String gaRefreshToken = jedis.get("ga-refresh-token");

        GoogleAuthentication gaAuth = new GoogleAuthentication(gaAuthToken);
        GoogleAnalyticsSpecification gaSpecification = GoogleAnalyticsSpecification.Builder()
                .forView(gaViewId)
                .forDateRange("2021-07-01", "2021-07-31")
                .dimensions(Arrays.asList("ga:date"))
                .metrics(Arrays.asList("ga:users", "ga:newUsers", "ga:sessions", "ga:transactions"))
                .withAuthentication(gaAuth)
                .pageSize(100);

        GoogleAnalyticsSource gaSource = GoogleAnalyticsSource.Builder()
                .withAuth(gaAuth)
                .withSpecification(gaSpecification)
                .build();

        GoogleAnalytics ga = new GoogleAnalytics(gaSource);
        Records r = null;
        boolean hasSucceded = false;

        while (!hasSucceded) {

            try {
                r = ga.getRecords();
                hasSucceded = true;
            } catch (DataSetuAuthException e) {
                String clientId = jedis.get("ga-client-id");
                String clientSecret = jedis.get("ga-client-secret");
                gaAuthToken = ga.renewAuthToken(clientId, clientSecret, gaRefreshToken);
                jedis.set("ga-token", gaAuthToken);
                gaSpecification.updateGoogleAuthentication(new GoogleAuthentication(gaAuthToken));
            }
        }


        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("ga:date", "ga:users", "ga:newUsers", "ga:sessions", "ga:transactions");
        at.addRule();
        for (Row record : r) {
            at.addRow(record.valueOfField("ga:date"),
                    record.valueOfField("ga:users"),
                    record.valueOfField("ga:newUsers"),
                    record.valueOfField("ga:sessions"),
                    record.valueOfField("ga:transactions")
            );
        }
        at.addRule();

        String records = at.render();
        System.out.println(records);
    }

    private static void basicIntegrationAuthExpiryWithPaginationTest() {
        Jedis jedis = new Jedis();
        String gaAuthToken = jedis.get("ga-token");
        String gaViewId = jedis.get("ga-viewid");
        String gaRefreshToken = jedis.get("ga-refresh-token");

        GoogleAuthentication gaAuth = new GoogleAuthentication(gaAuthToken);
        GoogleAnalyticsSpecification gaSpecification = GoogleAnalyticsSpecification.Builder()
                .forView(gaViewId)
                .forDateRange("2021-07-01", "2021-07-31")
                .dimensions(Arrays.asList("ga:date"))
                .metrics(Arrays.asList("ga:users", "ga:newUsers", "ga:sessions", "ga:transactions"))
                .withAuthentication(gaAuth)
                .pageSize(10);

        GoogleAnalyticsSource gaSource = GoogleAnalyticsSource.Builder()
                .withAuth(gaAuth)
                .withSpecification(gaSpecification)
                .build();

        GoogleAnalytics ga = new GoogleAnalytics(gaSource);
        Records r = null;

        while(ga.hasNext()) {
            boolean hasSucceded = false;
            while (!hasSucceded) {
                try {
                    r = ga.getRecords();

                    AsciiTable at = new AsciiTable();
                    at.addRule();
                    at.addRow("ga:date", "ga:users", "ga:newUsers", "ga:sessions", "ga:transactions");
                    at.addRule();
                    for (Row record : r) {
                        at.addRow(record.valueOfField("ga:date"),
                                record.valueOfField("ga:users"),
                                record.valueOfField("ga:newUsers"),
                                record.valueOfField("ga:sessions"),
                                record.valueOfField("ga:transactions")
                        );
                    }
                    at.addRule();

                    String records = at.render();
                    System.out.println(records);


                    hasSucceded = true;
                } catch (DataSetuAuthException e) {
                    String clientId = jedis.get("ga-client-id");
                    String clientSecret = jedis.get("ga-client-secret");
                    gaAuthToken = ga.renewAuthToken(clientId, clientSecret, gaRefreshToken);
                    jedis.set("ga-token", gaAuthToken);
                    gaSpecification.updateGoogleAuthentication(new GoogleAuthentication(gaAuthToken));
                }
            }
        }

    }

}
