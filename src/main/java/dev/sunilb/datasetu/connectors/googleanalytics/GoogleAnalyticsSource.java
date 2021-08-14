package dev.sunilb.datasetu.connectors.googleanalytics;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.sunilb.datasetu.entities.Page;
import dev.sunilb.datasetu.connectors.DataSetuSource;

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
    public String fetch() {
        specification.setNextPageToken(page.getNextPageToken());
        try {
            GoogleAnalyticsRequest gaRequest = specification.build();
            String jsonRequestBody = gaRequest.getRequestJsonBody();
//            String requestURL = gaRequest.getRequestURL();
//            String headers
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new String();
    }
}
