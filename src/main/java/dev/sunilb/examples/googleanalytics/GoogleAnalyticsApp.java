package dev.sunilb.examples.googleanalytics;

import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalytics;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsSource;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsSpecification;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAuthentication;
import dev.sunilb.datasetu.entities.Records;
import dev.sunilb.datasetu.entities.Row;

public class GoogleAnalyticsApp {
    public static void main(String[] args) {
        GoogleAuthentication gaAuth = new GoogleAuthentication("");
        GoogleAnalyticsSpecification gaSpecification = GoogleAnalyticsSpecification.Builder()
                .forView("")
                .forDateRange("2021-07-01", "2021-07-31")
                .dimensions("ga:date")
                .metrics("ga:users", "ga:newUsers", "ga:sessios", "ga:transactions")
                .withAuthentication(gaAuth)
                .pageSize(100);

        GoogleAnalyticsSource gaSource = GoogleAnalyticsSource.Builder()
                .withAuth(gaAuth)
                .withSpecification(gaSpecification)
                .build();

        GoogleAnalytics ga = new GoogleAnalytics(gaSource);
        Records r = ga.getRecords();
        System.out.println(r.count());
        for (Row record : r) {
            System.out.println(record.valueOfField("ga:users"));
            System.out.println(record.valueOfField("ga:date"));
        }
    }
}
