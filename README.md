# A generic Data Connector Framework

Data Setu is a connector framework that can read records from any data source (API, Flat Files, Databases, S3, etc). The
intent is to create a DSL that makes it easy to interface with any data source

A sample vision of what the DSL will look like and how I envision it is mentioned below.

### Example showcase for Google Analytics

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

        GoogleAuthentication gAuth = new GoogleAuthentication("Token");
        String refreshToken = gAuth.getRefreshToken();

        GoogleAnalyticsSource gaSource = GoogleAnalyticsSource
                .withSpecification(gaQuerySpecification)
                .withAuthorization(gaAuth)
                .buildSource();

        Records records = GoogleAnalytics.getRecords(gaSource);

        for (Row row : records) {
            try {
                System.out.println(row.getField("XX"));
                System.out.println(row.getField("YY"));
                System.out.println(row.getField("XX"));

            } catch (DataSetuAuthException e) {
                gAuth.refreshToken(refreshToken);
            } catch (Exception e) {
                break;
            }
        }
    }
}
````

### Example showcase for MySQL

````java

import java.util.Iterator;

class MySQLApp {
    public static void main(String[] args) {

        MySQLSpecification mySQLSpecification =
                MySQLSpecification.query("SELECT a, b, c FROM table");

        MySQLAuthentication mySQLAuthentication =
                new MySQLAuthentication("hostname", "username", "password");

        MySQLSource mySQLSource = MySQLSourcee
                .withSpecification(mySQLSpecification)
                .withAuthorization(mySQLAuthentication)
                .buildSource();

        Records records = MySQL.getRecords(gaSource);

        for (Row row : records) {
            try {
                System.out.println(row.getField("a"));
                System.out.println(row.getField("b"));
                System.out.println(row.getField("c"));
            } catch (DataSetuAuthException e) {
                // do specific logic to recover
            } catch (Exception e) {
                break;
            }
        }
    }
}
````
