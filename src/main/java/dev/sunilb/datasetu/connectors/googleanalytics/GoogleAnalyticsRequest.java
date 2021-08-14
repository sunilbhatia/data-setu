package dev.sunilb.datasetu.connectors.googleanalytics;

public class GoogleAnalyticsRequest {

    private final String gaRequestJsonBody;

    public GoogleAnalyticsRequest(String gaRequestJsonBody) {
        this.gaRequestJsonBody = gaRequestJsonBody;
    }

    public String getRequestJsonBody() {
        return gaRequestJsonBody;
    }
}
