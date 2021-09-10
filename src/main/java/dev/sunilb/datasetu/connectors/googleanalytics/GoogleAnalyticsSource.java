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
    public String fetch() {
        this.updatePage(this.page);
        GoogleAnalyticsRequest gaRequest = null;
        String response = "";
        try {
            gaRequest = specification.build();
            GoogleAnalyticsService gaService = new GoogleAnalyticsService(gaRequest);
            response = gaService.executeAndGetResponse();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return response;
    }
}
