package dev.sunilb.datasetu.connectors.googleanalytics;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class GoogleAnalyticsService {
    private GoogleAnalyticsRequest gaRequest;

    public GoogleAnalyticsService(GoogleAnalyticsRequest gaRequest) {
        this.gaRequest = gaRequest;
    }

    public String executeAndGetResponse() {

        final String jsonBody = gaRequest.getRequestJsonBody();
        System.out.println(jsonBody);
        final String apiURL = gaRequest.getGaAPIURL();
        final Map<String, String> headers = gaRequest.getHeaders();

        HttpClient client = HttpClient.newHttpClient();

        final HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(apiURL))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody));
        headers.forEach(builder::header);
        final HttpRequest request = builder.build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return response.body();
    }
}
