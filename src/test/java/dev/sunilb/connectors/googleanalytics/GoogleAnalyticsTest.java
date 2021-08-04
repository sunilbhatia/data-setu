package dev.sunilb.connectors.googleanalytics;

import dev.sunilb.datasetu.entities.Records;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GoogleAnalyticsTest {

    GoogleAnalytics ga;

    @BeforeClass
    public void setup() {

        // TODO: This is a sample code that will be fixed as I reach to the API implementation
        GoogleAnalyticsSpecification gaQuerySpecification =
                GoogleAnalyticsSpecification.Builder()
                        .params("from_date=XX/XX/XXXX")
                        .params("to_date=XX/XX/XXXX")
                        .params("dimensions=XX, YY, ZZ")
                        .params("metrics=page_views,source,city");

        // TODO: This is a sample code that will be fixed as I reach to the API implementation
        GoogleAuthentication gAuth = new GoogleAuthentication("Token");
        // TODO: Need to provide mechanism to let client update the token when the token refreshes as client will need to persist for later use
        String refreshToken = gAuth.getRefreshToken();

        GoogleAnalyticsSource gaSource = GoogleAnalyticsSource
                .Builder()
                .withSpecification()
                .withAuth(gAuth)
                .build();

        this.ga = new GoogleAnalytics(gaSource);

    }

    @Test
    public void shouldGetRecordsGivenAGoogleAnalyticsJsonResponse() {
        Records records = ga.getRecords();
        assertEquals(records.count(), 0);
    }

}
