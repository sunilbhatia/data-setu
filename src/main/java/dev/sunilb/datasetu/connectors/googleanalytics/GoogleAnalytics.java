package dev.sunilb.datasetu.connectors.googleanalytics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.sunilb.datasetu.Page;
import dev.sunilb.datasetu.entities.Records;

public class GoogleAnalytics {

    private GoogleAnalyticsSource gaSource;
    private Page page;

    public GoogleAnalytics(GoogleAnalyticsSource gaSource) {
        this.gaSource = gaSource;
    }

    public Records getRecords() {
        String json = gaSource.fetch();

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(GoogleAnalyticsRecordsDeserializerResponse.class, new GoogleAnalyticsRecordsDeserializer(GoogleAnalyticsRecordsDeserializerResponse.class));
        mapper.registerModule(module);

        GoogleAnalyticsRecordsDeserializerResponse gaResponse = null;
        Records records = null;

        try {
            gaResponse = mapper.readValue(json, GoogleAnalyticsRecordsDeserializerResponse.class);
            records = gaResponse.getRecords();
            this.page = gaResponse.getPage();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //TODO: Raise DataSetuException
        }

        return records;
    }

    public boolean hasNext() {
        if (this.page == null) return true;
        return this.page.hasNext();
    }
}
