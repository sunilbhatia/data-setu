package dev.sunilb.connectors.googleanalytics;

import dev.sunilb.datasetu.connectors.googleanalytics.*;
import dev.sunilb.datasetu.entities.Records;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static dev.sunilb.helpers.TestHelpers.*;

public class GoogleAnalyticsTest {

    GoogleAnalytics ga;

    @BeforeClass
    public void setup() {

        try {
            String json = getResourceStreamAsString("googleanalytics/responses/country-users-response.json");

            GoogleAnalyticsSource gaSource = mock(GoogleAnalyticsSource.class);
            when(gaSource.fetch()).thenReturn(json);
            this.ga = new GoogleAnalytics(gaSource);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetRecordsGivenAGoogleAnalyticsJsonResponseThatHasRecords() {
        Records records = ga.getRecords();
        assertEquals(records.fieldsCount(), 2);
        assertEquals(records.getFieldNameAtPosition(0), "ga:country");
        assertEquals(records.getFieldNameAtPosition(1), "ga:users");
        assertEquals(records.count(), 73);
    }

}
