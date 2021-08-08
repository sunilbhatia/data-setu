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

public class GoogleAnalyticsPaginationTest {

    GoogleAnalytics gaBasic;

    @BeforeClass
    public void setup() {

    }

    @Test
    public void shouldReturnFalseWhenAllRecordsAreFetched() {

    }

}
