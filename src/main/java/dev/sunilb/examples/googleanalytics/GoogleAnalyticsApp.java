package dev.sunilb.examples.googleanalytics;

import de.vandermeer.asciitable.AsciiTable;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalytics;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsSource;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsSpecification;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAuthentication;
import dev.sunilb.datasetu.entities.Records;
import dev.sunilb.datasetu.entities.Row;
import redis.clients.jedis.Jedis;

public class GoogleAnalyticsApp {
    public static void main(String[] args) {
        System.out.println("Please populate REDIS with your token under (ga-token), Refresh Token under (ga-refresh-token) and View ID under (ga-viewid)");
        GoogleAnalyticsApp.basicIntegrationTest();
    }

    private static void basicIntegrationTest() {
        Jedis jedis = new Jedis();
        String gaAuthToken = jedis.get("ga-token");
        String gaViewId = jedis.get("ga-viewid");
        String gaRefreshToken = jedis.get("ga-refresh-token");

        GoogleAuthentication gaAuth = new GoogleAuthentication(gaAuthToken);
        GoogleAnalyticsSpecification gaSpecification = GoogleAnalyticsSpecification.Builder()
                .forView(gaViewId)
                .forDateRange("2021-07-01", "2021-07-31")
                .dimensions("ga:date")
                .metrics("ga:users", "ga:newUsers", "ga:sessions", "ga:transactions")
                .withAuthentication(gaAuth)
                .pageSize(100);

        GoogleAnalyticsSource gaSource = GoogleAnalyticsSource.Builder()
                .withAuth(gaAuth)
                .withSpecification(gaSpecification)
                .build();

        GoogleAnalytics ga = new GoogleAnalytics(gaSource);
        Records r = ga.getRecords();

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
}
