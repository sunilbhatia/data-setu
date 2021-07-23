# A generic Data Connector Framework

Data Setu is a connector framework that can read records from any data source (API, Flat Files, Databases, S3, etc). The
intent is to create a DSL that makes it easy to interface with any data source

A sample vision of what the DSL will look like and how I envision it is mentioned below.

````java

import java.util.Iterator;

class GoogleAnalyticsApp {
    public static void main(String[] args) {

        GoogleAnalyticsSpecification gaQuerySpecification =
                GoogleAnalyticsSpecification
                        .params("from_date=XX/XX/XXXX")
                        .params("to_date=XX/XX/XXXX")
                        .params("dimensions=XX, YY, ZZ")
                        .params("metrics=page_views,source,city");

        GoogleAuthentication gAuth = new gAuth("Token");
        String refreshToken = gAuth.getRefreshToken();

        GoogleAnalyticsSource gaSource = GoogleAnalyticsSource
                .withSpecification(gaQuerySpecification)
                .withAuthorization(gaAuth)
                .buildSource();

        Records records = GoogleAnalytics.getRecords(gaSource);

        Iterator itr = records.iterator();
        while (itr.hasNext()) {
            try {
                // Logic to process data
            }
            catch(DataSetuAuthException e) {
                gAuth.refreshToken(refreshToken);
            }
            catch (Exception e) {
                break;
            }
        }
    }
}
````
