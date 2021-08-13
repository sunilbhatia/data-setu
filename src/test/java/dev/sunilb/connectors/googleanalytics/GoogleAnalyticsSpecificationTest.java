package dev.sunilb.connectors.googleanalytics;


import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsSpecification;
import org.testng.annotations.Test;

import java.io.IOException;

import static dev.sunilb.helpers.TestHelpers.getResourceStreamAsString;
import static dev.sunilb.helpers.TestHelpers.isJsonEqual;
import static org.testng.Assert.assertTrue;

public class GoogleAnalyticsSpecificationTest {

    @Test
    public void shouldReturnRequestBodyForASingleDateSpecification() throws IOException {

        String sampleRequestJson = getResourceStreamAsString("googleanalytics/basicsanity/requests/misc-ga-request.json");

        GoogleAnalyticsSpecification gaSpecification =
                GoogleAnalyticsSpecification
                        .Builder()
                        .forView("112233445566778899")
                        .forDateRange("1998-01-01", "1998-01-01")
                        .metrics("ga:users", "ga:sessions", "ga:pageviews")
                        .dimensions("ga:date", "ga:country", "ga:browser", "ga:userType", "ga:city", "ga:source")
                        .pageSize(10);

        String requestBodyJson = gaSpecification.build();

        assertTrue(isJsonEqual(sampleRequestJson, requestBodyJson));

    }

    @Test
    public void shouldReturnRequestBodyForMultiDateSpecification() throws IOException {

        String sampleRequestJson = getResourceStreamAsString("googleanalytics/basicsanity/requests/misc-ga-request-multi-dates.json");

        GoogleAnalyticsSpecification gaSpecification =
                GoogleAnalyticsSpecification
                        .Builder()
                        .forView("112233445566778899")
                        .forDateRange("1998-01-01", "1998-01-01")
                        .forDateRange("1999-01-01", "1999-01-31")
                        .forDateRange("2000-05-01", "2000-05-31")
                        .metrics("ga:users", "ga:sessions", "ga:pageviews")
                        .dimensions("ga:date", "ga:country", "ga:browser", "ga:userType", "ga:city", "ga:source")
                        .pageSize(10);

        String requestBodyJson = gaSpecification.build();

        assertTrue(isJsonEqual(sampleRequestJson, requestBodyJson));

    }


}
