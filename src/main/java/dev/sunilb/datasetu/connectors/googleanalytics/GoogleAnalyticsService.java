package dev.sunilb.datasetu.connectors.googleanalytics;

import dev.sunilb.datasetu.exceptions.DataSetuAccessTokenExpiredException;
import dev.sunilb.datasetu.exceptions.DataSetuAuthException;
import dev.sunilb.datasetu.exceptions.DataSetuException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class GoogleAnalyticsService {

    public static String executeAndGetData(GoogleAnalyticsRequest gaRequest) throws DataSetuAuthException {

        final String jsonBody = gaRequest.getRequestJsonBody();
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

        if (response.statusCode() != 200) {
            if (response.statusCode() == 401) {
                throw new DataSetuAccessTokenExpiredException("Google Analytics Access Token Has Expired. Need to renew: " + response.body());
            } else if (response.statusCode() == 403) {
                throw new DataSetuAuthException("Google Analytics: User does not have access to property: " + response.body());
            } else if (response.statusCode() == 400) {
                throw new DataSetuException("Bad Request. Response from GoogleAnalytics: " + response.body());
            } else {
                throw new DataSetuException("Unknown Error response from GoogleAnalytics: " + response.body());
            }
        }

        return response.body();
    }

    public static String executeAndGetNewAuthToken(GoogleAnalyticsRequest gaRefreshTokenRequest) {

        final String jsonBody = gaRefreshTokenRequest.getRequestJsonBody();
        final String apiURL = gaRefreshTokenRequest.getGaAPIURL();
        final Map<String, String> headers = gaRefreshTokenRequest.getHeaders();

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

        if (response.statusCode() != 200) {
            if (response.statusCode() == 401 || response.statusCode() == 403) {
                throw new DataSetuAuthException("Google Auth Exception");
            }
        }

        return response.body();

    }
}
