package dev.sunilb.datasetu.connectors.googleanalytics;

import dev.sunilb.datasetu.entities.Page;
import dev.sunilb.datasetu.entities.Records;

public class GoogleAnalyticsRecordsDeserializerResponse {

    Records records;
    Page page;

    public GoogleAnalyticsRecordsDeserializerResponse() {
    }

    public GoogleAnalyticsRecordsDeserializerResponse(Records records, Page page) {
        this.records = records;
        this.page = page;
    }

    public Records getRecords() {
        return this.records;
    }

    public Page getPage() {
        return this.page;
    }
}
