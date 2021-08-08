package dev.sunilb.connectors.googleanalytics;

import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalytics;
import dev.sunilb.datasetu.connectors.googleanalytics.GoogleAnalyticsSource;
import dev.sunilb.datasetu.entities.Records;
import dev.sunilb.datasetu.entities.Row;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static dev.sunilb.helpers.TestHelpers.getResourceStreamAsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static dev.sunilb.helpers.TestHelpers.*;

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
    public void shouldReturnFalseWhenAllRecordsAreFetched() {

        Records records = null;

        int [] recordCount = {10, 10, 10, 1};
        int index = 0;

        while(gaBasicPagination.hasNext()) {
            records = gaBasicPagination.getRecords();
            assertEquals(records.count(), recordCount[index]);
            index = index + 1;
        }

        /*Records recordsPage1 = gaBasicPagination.getRecords();
        assertEquals(recordsPage1.count(), 10);
        Records recordsPage2 = gaBasicPagination.getRecords();
        assertEquals(recordsPage2.count(), 10);
        Records recordsPage3 = gaBasicPagination.getRecords();
        assertEquals(recordsPage3.count(), 10);
        Records recordsPage4 = gaBasicPagination.getRecords();
        assertEquals(recordsPage4.count(), 1);*/
    }

}
