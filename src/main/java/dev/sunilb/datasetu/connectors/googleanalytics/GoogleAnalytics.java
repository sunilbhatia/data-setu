package dev.sunilb.datasetu.connectors.googleanalytics;

import dev.sunilb.datasetu.entities.Records;

public class GoogleAnalytics {

    private GoogleAnalyticsSource gaSource;

    public GoogleAnalytics(GoogleAnalyticsSource gaSource) {
        this.gaSource = gaSource;
    }

    public Records getRecords() {
        String jsonString = gaSource.fetch();
        System.out.println(jsonString);
        Records records = new Records();
        return records;
    }
}
