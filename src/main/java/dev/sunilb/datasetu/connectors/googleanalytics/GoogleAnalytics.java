package dev.sunilb.datasetu.connectors.googleanalytics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.sunilb.datasetu.connectors.DataSetuSource;
import dev.sunilb.datasetu.entities.Page;
import dev.sunilb.datasetu.entities.Records;

public class GoogleAnalytics {

    private final DataSetuSource gaSource;
    private Page page;

    public GoogleAnalytics(DataSetuSource gaSource) {
        this.gaSource = gaSource;
    }

    private GoogleAnalyticsRecordsDeserializerResponse getGAResponse() throws JsonProcessingException {
        String json = gaSource.fetch();

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(GoogleAnalyticsRecordsDeserializerResponse.class, new GoogleAnalyticsRecordsDeserializer(GoogleAnalyticsRecordsDeserializerResponse.class));
        mapper.registerModule(module);

        return mapper.readValue(json, GoogleAnalyticsRecordsDeserializerResponse.class);
    }

    public Records getRecords() {

        GoogleAnalyticsRecordsDeserializerResponse gaResponse = null;
        Records records = null;

        try {
            gaResponse = getGAResponse();
            records = gaResponse.getRecords();

            this.page = gaResponse.getPage();
            this.gaSource.updatePage(this.page);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //TODO: Raise DataSetuException
        }

        return records;
    }

    public boolean hasNext() {
        if (this.page == null) return true; // should return true if the first cursor has not been fetched
        return this.page.hasNext();
    }

    public String renewAuthToken(String clientId, String clientSecret, String gaRefreshToken) {
        return gaSource.renewAuthToken(clientId, clientSecret, gaRefreshToken);
    }
}
