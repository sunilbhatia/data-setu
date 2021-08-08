package dev.sunilb.connectors.googleanalytics;

import dev.sunilb.datasetu.connectors.googleanalytics.*;
import dev.sunilb.datasetu.entities.Records;
import dev.sunilb.datasetu.entities.Row;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static dev.sunilb.helpers.TestHelpers.*;

public class GoogleAnalyticsTest {

    GoogleAnalytics gaBasic;
    GoogleAnalytics gaEnhanced;
    GoogleAnalytics gaEnhanced2;

    @BeforeClass
    public void setup() {

        try {
            String jsonBasic = getResourceStreamAsString("googleanalytics/responses/country-users-response.json");
            String jsonEnhanced = getResourceStreamAsString("googleanalytics/responses/misc-ga-response.json");
            String jsonEnhanced2 = getResourceStreamAsString("googleanalytics/responses/misc-ga-response2.json");

            GoogleAnalyticsSource gaBasicSource = mock(GoogleAnalyticsSource.class);
            when(gaBasicSource.fetch()).thenReturn(jsonBasic);
            this.gaBasic = new GoogleAnalytics(gaBasicSource);

            GoogleAnalyticsSource gaEnhancedSource = mock(GoogleAnalyticsSource.class);
            when(gaEnhancedSource.fetch()).thenReturn(jsonEnhanced);
            this.gaEnhanced = new GoogleAnalytics(gaEnhancedSource);

            GoogleAnalyticsSource gaEnhancedSource2 = mock(GoogleAnalyticsSource.class);
            when(gaEnhancedSource2.fetch()).thenReturn(jsonEnhanced2);
            this.gaEnhanced2 = new GoogleAnalytics(gaEnhancedSource2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetRecordsGivenAGoogleAnalyticsJsonResponseThatHasRecords() {
        Records records = gaBasic.getRecords();
        assertEquals(records.fieldsCount(), 2);
        assertEquals(records.getFieldNameAtPosition(0), "ga:country");
        assertEquals(records.getFieldNameAtPosition(1), "ga:users");
        assertEquals(records.count(), 73);
    }

    @Test
    public void shouldGetRecordsWithNineFieldsAndTenRecords() {
        Records records = gaEnhanced.getRecords();
        assertEquals(records.fieldsCount(), 9);

        String[] fieldList = {"ga:date", "ga:country", "ga:browser", "ga:userType", "ga:city", "ga:source", "ga:users", "ga:sessions", "ga:pageviews"};
        int fieldPosition = 0;

        // Validate if the fields have been loaded properly
        for (String fieldName : fieldList) {
            assertEquals(records.getFieldNameAtPosition(fieldPosition), fieldName);
            fieldPosition = fieldPosition + 1;
        }
        assertEquals(records.count(), 10);
    }


    @Test
    public void shouldGetRecordsAndCompareSecondSixthAndTenthRecord() {
        Records records = gaEnhanced.getRecords();

        // Second, Sixth and TenthRecords from misc-ga-response.json file - these are random records for validation
        int[] recordPositions = {1, 5, 9};
        String[][] sampleRecords = {
                {"19900101", "(not set)", "Android Webview", "Returning Visitor", "(not set)", "facebook", "1", "1", "6"},
                {"19900101", "(not set)", "Chrome", "New Visitor", "(not set)", "in.search.yahoo.com", "1", "1", "3"},
                {"19900101", "(not set)", "Firefox", "New Visitor", "(not set)", "google", "1", "1", "1"}
        };

        String[] fieldList = {"ga:date", "ga:country", "ga:browser", "ga:userType", "ga:city", "ga:source", "ga:users", "ga:sessions", "ga:pageviews"};


        int index = 0;
        for (String[] sampleRecord : sampleRecords) {
            int recordNumber = recordPositions[index];
            Row row = records.getRow(recordNumber);

            for (String fieldName : fieldList) {
                assertEquals(row.valueOfField(fieldName), sampleRecord[records.getFieldPositionForGivenName(fieldName)]);
            }

            index = index + 1;

        }

    }


    @Test
    public void shouldGetMoreRecordsAndCompareLastRecord() {
        Records records = gaEnhanced2.getRecords();

        String[] response = {"19900101", "Returning Visitor", "4322", "0", "5412", "3095", "812502.0", "12", "0.0", "0.0", "0.0"};

        String[] fieldList = {"ga:date", "ga:userType", "ga:users", "ga:newUsers", "ga:sessions", "ga:bounces", "ga:sessionDuration", "ga:transactions", "ga:transactionRevenue", "ga:revenuePerTransaction", "ga:percentNewSessions"};

        Row row = records.getRow(1);
        for (String fieldName : fieldList) {
            assertEquals(row.valueOfField(fieldName), response[records.getFieldPositionForGivenName(fieldName)]);
        }

    }

}
