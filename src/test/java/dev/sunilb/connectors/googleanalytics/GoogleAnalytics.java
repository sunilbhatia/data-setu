package dev.sunilb.connectors.googleanalytics;

import dev.sunilb.datasetu.entities.Records;

public class GoogleAnalytics {

    private GoogleAnalyticsSource gaSource;

    public GoogleAnalytics(GoogleAnalyticsSource gaSource) {
        this.gaSource = gaSource;
    }

    public Records getRecords() {
        Records records = gaSource.fetch();
        return records;
    }
}
