package dev.sunilb.connectors.googleanalytics;


import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsRequest;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsSpecification;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;

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
                        .metrics(Arrays.asList("ga:users", "ga:sessions", "ga:pageviews"))
                        .dimensions(Arrays.asList("ga:date", "ga:country", "ga:browser", "ga:userType", "ga:city", "ga:source"))
                        .pageSize(10);

        GoogleAnalyticsRequest gaRequest = gaSpecification.build();
        assertTrue(isJsonEqual(sampleRequestJson, gaRequest.getRequestJsonBody()));

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
                        .metrics(Arrays.asList("ga:users", "ga:sessions", "ga:pageviews"))
                        .dimensions(Arrays.asList("ga:date", "ga:country", "ga:browser", "ga:userType", "ga:city", "ga:source"))
                        .pageSize(10);

        GoogleAnalyticsRequest gaRequest = gaSpecification.build();
        assertTrue(isJsonEqual(sampleRequestJson, gaRequest.getRequestJsonBody()));

    }

    @Test
    public void shouldReturnRequestBodyForPagination() throws IOException {

        String sampleRequestJson = getResourceStreamAsString("googleanalytics/pagination/requests/second-page-request.json");

        GoogleAnalyticsSpecification gaSpecification =
                GoogleAnalyticsSpecification
                        .Builder()
                        .forView("112233445566778899")
                        .forDateRange("1998-07-01", "1998-07-31")
                        .metrics(Arrays.asList("ga:users", "ga:newUsers", "ga:sessions", "ga:bounces", "ga:sessionDuration", "ga:transactions", "ga:transactionRevenue", "ga:revenuePerTransaction", "ga:percentNewSessions"))
                        .dimensions(Arrays.asList("ga:date"))
                        .setNextPageToken("10")
                        .pageSize(10);

        GoogleAnalyticsRequest gaRequest = gaSpecification.build();
        assertTrue(isJsonEqual(sampleRequestJson, gaRequest.getRequestJsonBody()));

    }


    @Test
    public void shouldReturnRequestBodyForAllPagination() throws IOException {

        String sampleRequestJsonForPageOne = getResourceStreamAsString("googleanalytics/pagination/requests/first-page-request.json");
        String sampleRequestJsonForPageTwo = getResourceStreamAsString("googleanalytics/pagination/requests/second-page-request.json");
        String sampleRequestJsonForPageThree = getResourceStreamAsString("googleanalytics/pagination/requests/third-page-request.json");
        String sampleRequestJsonForPageLast = getResourceStreamAsString("googleanalytics/pagination/requests/last-page-request.json");

        GoogleAnalyticsSpecification gaSpecification =
                GoogleAnalyticsSpecification
                        .Builder()
                        .forView("112233445566778899")
                        .forDateRange("1998-07-01", "1998-07-31")
                        .metrics(Arrays.asList("ga:users", "ga:newUsers", "ga:sessions", "ga:bounces", "ga:sessionDuration", "ga:transactions", "ga:transactionRevenue", "ga:revenuePerTransaction", "ga:percentNewSessions"))
                        .dimensions(Arrays.asList("ga:date"))
                        .pageSize(10);

        GoogleAnalyticsRequest gaRequest = gaSpecification.build();
        String requestBodyJsonForPageOne = gaRequest.getRequestJsonBody();
        assertTrue(isJsonEqual(sampleRequestJsonForPageOne, requestBodyJsonForPageOne));

        gaSpecification.setNextPageToken("10");
        gaRequest = gaSpecification.build();
        String requestBodyJsonForPageTwo = gaRequest.getRequestJsonBody();
        assertTrue(isJsonEqual(sampleRequestJsonForPageTwo, requestBodyJsonForPageTwo));

        gaSpecification.setNextPageToken("20");
        gaRequest = gaSpecification.build();
        String requestBodyJsonForPageThree = gaRequest.getRequestJsonBody();
        assertTrue(isJsonEqual(sampleRequestJsonForPageThree, requestBodyJsonForPageThree));

        gaSpecification.setNextPageToken("30");
        gaRequest = gaSpecification.build();
        String requestBodyJsonForPageLast = gaRequest.getRequestJsonBody();
        assertTrue(isJsonEqual(sampleRequestJsonForPageLast, requestBodyJsonForPageLast));

    }


}
