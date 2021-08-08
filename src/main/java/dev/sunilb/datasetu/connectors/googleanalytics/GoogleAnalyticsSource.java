package dev.sunilb.datasetu.connectors.googleanalytics;

import dev.sunilb.datasetu.Page;
import dev.sunilb.datasetu.connectors.DataSetuSource;
import dev.sunilb.datasetu.entities.Records;

import java.util.ArrayList;
import java.util.List;

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
        this.page = page;
    }

    @Override
    public String fetch() {
        return new String();
    }
}
