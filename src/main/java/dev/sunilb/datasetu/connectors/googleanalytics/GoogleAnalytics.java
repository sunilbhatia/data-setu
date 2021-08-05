package dev.sunilb.datasetu.connectors.googleanalytics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.sunilb.datasetu.entities.Records;

public class GoogleAnalytics {

    private GoogleAnalyticsSource gaSource;

    public GoogleAnalytics(GoogleAnalyticsSource gaSource) {
        this.gaSource = gaSource;
    }

    public Records getRecords() {
        String json = gaSource.fetch();

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Records.class, new GoogleAnalyticsRecordsDeserializer(Records.class));
        mapper.registerModule(module);

        Records records = null;

        try {
            records = mapper.readValue(json, Records.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return records;
    }
}
