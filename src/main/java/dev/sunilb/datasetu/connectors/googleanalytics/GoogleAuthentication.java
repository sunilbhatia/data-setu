package dev.sunilb.datasetu.connectors.googleanalytics;

public class GoogleAuthentication {
    private final String accessToken;

    public GoogleAuthentication(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
