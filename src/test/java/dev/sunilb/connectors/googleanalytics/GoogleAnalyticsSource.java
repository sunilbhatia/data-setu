package dev.sunilb.connectors.googleanalytics;

import dev.sunilb.connectors.DataSetuSource;
import dev.sunilb.datasetu.entities.Records;

public class GoogleAnalyticsSource implements DataSetuSource {

    private void GoogleAnalyticsSource() {

    }

    public static GoogleAnalyticsSource Builder() {
        return new GoogleAnalyticsSource();
    }

    public GoogleAnalyticsSource withSpecification() {
        return this;
    }

    public GoogleAnalyticsSource build() {
        return this;
    }

    @Override
    public Records fetch() {
        return new Records();
    }

    public GoogleAnalyticsSource withAuth(GoogleAuthentication gAuth) {
        return this;
    }
}
