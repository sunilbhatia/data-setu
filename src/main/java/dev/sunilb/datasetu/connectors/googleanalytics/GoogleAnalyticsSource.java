package dev.sunilb.datasetu.connectors.googleanalytics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sunilb.datasetu.entities.Page;
import dev.sunilb.datasetu.connectors.DataSetuSource;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GoogleAnalyticsSource implements DataSetuSource {

    private GoogleAnalyticsSpecification specification;
    private GoogleAuthentication authentication;
    private Page page;

    private void GoogleAnalyticsSource() {

    }

    public static GoogleAnalyticsSource Builder() {
        return new GoogleAnalyticsSource();
    }

    public GoogleAnalyticsSource withSpecification(GoogleAnalyticsSpecification gaQuerySpecification) {
        this.specification = gaQuerySpecification;
        return this;
    }

    public GoogleAnalyticsSource build() {
        if(this.page == null) {
            this.page = new Page(10);
        }
        return this;
    }

    public GoogleAnalyticsSource withAuth(GoogleAuthentication authentication) {
        this.authentication = authentication;
        return this;
    }

    @Override
    public void updatePage(Page page) {
        this.specification.setNextPageToken(page.getNextPageToken());
        this.page = page;
    }

    @Override
    public String renewAuthToken(String clientId, String clientSecret, String gaRefreshToken) {

        Map<String, String> formData = new HashMap<>();
        formData.put("client_id", clientId);
        formData.put("client_secret", clientSecret);
        formData.put("refresh_token", gaRefreshToken);
        formData.put("grant_type", "refresh_token");

        String requestBody = formData.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(formData.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        Map<String, String> headers = new HashMap<>();

        headers.put("Content-Type", "application/x-www-form-urlencoded");

        GoogleAnalyticsRequest gaRefreshTokenRequest = new GoogleAnalyticsRequest(
                requestBody,
                "https://www.googleapis.com/oauth2/v4/token",
                headers
        );

        String response = "";
        response = GoogleAnalyticsService.executeAndGetNewAuthToken(gaRefreshTokenRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String new_access_token = jsonNode.get("access_token").asText();

        return new_access_token;
    }

    @Override
    public String fetch() {
        this.updatePage(this.page);
        GoogleAnalyticsRequest gaRequest = null;
        String response = "";
        try {
            gaRequest = specification.build();
            response = GoogleAnalyticsService.executeAndGetData(gaRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return response;
    }
}
