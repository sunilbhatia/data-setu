package dev.sunilb.connectors.googleanalytics;

import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalytics;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsSource;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static dev.sunilb.helpers.TestHelpers.getResourceStreamAsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class GoogleAnalyticsPaginationTest {

    GoogleAnalytics gaBasicPagination;

    @BeforeClass
    public void setup() {
        try {

            String firstPageJson = getResourceStreamAsString("googleanalytics/pagination/responses/first-page-response.json");
            String secondPageJson = getResourceStreamAsString("googleanalytics/pagination/responses/second-page-response.json");
            String thirdPageJson = getResourceStreamAsString("googleanalytics/pagination/responses/third-page-response.json");
            String lastPageJson = getResourceStreamAsString("googleanalytics/pagination/responses/last-page-response.json");

            GoogleAnalyticsSource gaBasicSource = mock(GoogleAnalyticsSource.class);
            when(gaBasicSource.fetch()).thenReturn(firstPageJson).thenReturn(secondPageJson).thenReturn(thirdPageJson).thenReturn(lastPageJson);

            this.gaBasicPagination = new GoogleAnalytics(gaBasicSource);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void hasNextShouldReturnTrueForFirstCallAndFalseAfterAllRecordsHaveBeenRead() {

        assertTrue(gaBasicPagination.hasNext());
        assertEquals(gaBasicPagination.getRecords().count(), 10);
        assertTrue(gaBasicPagination.hasNext());
        assertEquals(gaBasicPagination.getRecords().count(), 10);
        assertTrue(gaBasicPagination.hasNext());
        assertEquals(gaBasicPagination.getRecords().count(), 10);
        assertFalse(gaBasicPagination.hasNext());
        assertEquals(gaBasicPagination.getRecords().count(), 10);

    }

}
