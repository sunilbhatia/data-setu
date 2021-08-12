package dev.sunilb.connectors.googleanalytics;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsSpecification;
import dev.sunilb.datasetu.entities.Page;
import org.testng.annotations.Test;

import java.io.IOException;

import static dev.sunilb.helpers.TestHelpers.getResourceStreamAsString;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GoogleAnalyticsSpecificationTest {

    @Test
    public void shouldReturnURLForGivenDateSpecification() throws IOException {

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

    private boolean isJsonEqual(String compareJson, String withJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(compareJson).equals(mapper.readTree(withJson));
    }
}
