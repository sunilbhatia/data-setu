package dev.sunilb.datasetu.connectors.googleanalytics;

import java.util.Map;

public class GoogleAnalyticsRequest {

    private final String gaRequestJsonBody;
    private final String gaAPIURL;
    private final Map<String, String> headers;

    public GoogleAnalyticsRequest(String gaRequestJsonBody, String gaAPIURL, Map<String, String> headers) {
        this.gaRequestJsonBody = gaRequestJsonBody;
        this.gaAPIURL = gaAPIURL;
        this.headers = headers;
    }

    public String getRequestJsonBody() {
        return gaRequestJsonBody;
    }

    public String getGaAPIURL() {
        return gaAPIURL;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
