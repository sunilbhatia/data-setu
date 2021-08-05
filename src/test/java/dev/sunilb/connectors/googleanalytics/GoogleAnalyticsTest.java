package dev.sunilb.connectors.googleanalytics;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.sunilb.datasetu.connectors.googleanalytics.*;
import dev.sunilb.datasetu.entities.Records;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class GoogleAnalyticsTest {

    GoogleAnalytics ga;

    @BeforeClass
    public void setup() {
        GoogleAnalyticsSource gaSource = mock(GoogleAnalyticsSource.class);
        when(gaSource.fetch()).thenReturn("Sunil Bhatia");
        this.ga = new GoogleAnalytics(gaSource);

        /*InputStream is = getClass().getClassLoader().getResourceAsStream("googleanalytics/responses/country-users-response.json");
        try (InputStreamReader streamReader =
                     new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("googleanalytics/responses/country-users-response.json")) {
//pass InputStream to JSON-Library, e.g. using Jackson
            ObjectMapper mapper = new ObjectMapper();

            SimpleModule module = new SimpleModule();
            module.addDeserializer(Records.class, new GoogleAnalyticsRecordsDeserializer(Records.class));
            mapper.registerModule(module);

            String json = "{\"reports\":[{\"columnHeader\":{\"dimensions\":[\"ga:country\"],\"metricHeader\":{\"metricHeaderEntries\":[{\"name\":\"ga:users\",\"type\":\"INTEGER\"}]}},\"data\":{\"rows\":[{\"dimensions\":[\"(not set)\"],\"metrics\":[{\"values\":[\"44\"]}]},{\"dimensions\":[\"Algeria\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Australia\"],\"metrics\":[{\"values\":[\"15\"]}]},{\"dimensions\":[\"Austria\"],\"metrics\":[{\"values\":[\"3\"]}]},{\"dimensions\":[\"Bahrain\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Bangladesh\"],\"metrics\":[{\"values\":[\"9\"]}]},{\"dimensions\":[\"Belarus\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Belgium\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Brazil\"],\"metrics\":[{\"values\":[\"3\"]}]},{\"dimensions\":[\"Bulgaria\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Cambodia\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Canada\"],\"metrics\":[{\"values\":[\"21\"]}]},{\"dimensions\":[\"China\"],\"metrics\":[{\"values\":[\"4\"]}]},{\"dimensions\":[\"Colombia\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Costa Rica\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Denmark\"],\"metrics\":[{\"values\":[\"3\"]}]},{\"dimensions\":[\"Egypt\"],\"metrics\":[{\"values\":[\"3\"]}]},{\"dimensions\":[\"Ethiopia\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Finland\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"France\"],\"metrics\":[{\"values\":[\"5\"]}]},{\"dimensions\":[\"Germany\"],\"metrics\":[{\"values\":[\"10\"]}]},{\"dimensions\":[\"Ghana\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Greece\"],\"metrics\":[{\"values\":[\"2\"]}]},{\"dimensions\":[\"Guatemala\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Honduras\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Hong Kong\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"India\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Indonesia\"],\"metrics\":[{\"values\":[\"2\"]}]},{\"dimensions\":[\"Iran\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Iraq\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Ireland\"],\"metrics\":[{\"values\":[\"5\"]}]},{\"dimensions\":[\"Italy\"],\"metrics\":[{\"values\":[\"4\"]}]},{\"dimensions\":[\"Japan\"],\"metrics\":[{\"values\":[\"4\"]}]},{\"dimensions\":[\"Jordan\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Kenya\"],\"metrics\":[{\"values\":[\"4\"]}]},{\"dimensions\":[\"Kuwait\"],\"metrics\":[{\"values\":[\"3\"]}]},{\"dimensions\":[\"Liberia\"],\"metrics\":[{\"values\":[\"2\"]}]},{\"dimensions\":[\"Lithuania\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Malaysia\"],\"metrics\":[{\"values\":[\"8\"]}]},{\"dimensions\":[\"Maldives\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Namibia\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Nepal\"],\"metrics\":[{\"values\":[\"4\"]}]},{\"dimensions\":[\"Netherlands\"],\"metrics\":[{\"values\":[\"10\"]}]},{\"dimensions\":[\"New Zealand\"],\"metrics\":[{\"values\":[\"2\"]}]},{\"dimensions\":[\"Nigeria\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Oman\"],\"metrics\":[{\"values\":[\"3\"]}]},{\"dimensions\":[\"Pakistan\"],\"metrics\":[{\"values\":[\"8\"]}]},{\"dimensions\":[\"Peru\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Philippines\"],\"metrics\":[{\"values\":[\"3\"]}]},{\"dimensions\":[\"Poland\"],\"metrics\":[{\"values\":[\"4\"]}]},{\"dimensions\":[\"Qatar\"],\"metrics\":[{\"values\":[\"2\"]}]},{\"dimensions\":[\"Russia\"],\"metrics\":[{\"values\":[\"2\"]}]},{\"dimensions\":[\"Saudi Arabia\"],\"metrics\":[{\"values\":[\"9\"]}]},{\"dimensions\":[\"Senegal\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Sierra Leone\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Singapore\"],\"metrics\":[{\"values\":[\"33\"]}]},{\"dimensions\":[\"South Africa\"],\"metrics\":[{\"values\":[\"2\"]}]},{\"dimensions\":[\"South Korea\"],\"metrics\":[{\"values\":[\"2\"]}]},{\"dimensions\":[\"Spain\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Sri Lanka\"],\"metrics\":[{\"values\":[\"2\"]}]},{\"dimensions\":[\"St. Kitts & Nevis\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Sweden\"],\"metrics\":[{\"values\":[\"3\"]}]},{\"dimensions\":[\"Switzerland\"],\"metrics\":[{\"values\":[\"2\"]}]},{\"dimensions\":[\"Taiwan\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Thailand\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Tunisia\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Turkey\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Uganda\"],\"metrics\":[{\"values\":[\"1\"]}]},{\"dimensions\":[\"Ukraine\"],\"metrics\":[{\"values\":[\"2\"]}]},{\"dimensions\":[\"United Arab Emirates\"],\"metrics\":[{\"values\":[\"21\"]}]},{\"dimensions\":[\"United Kingdom\"],\"metrics\":[{\"values\":[\"40\"]}]},{\"dimensions\":[\"United States\"],\"metrics\":[{\"values\":[\"229\"]}]},{\"dimensions\":[\"Vietnam\"],\"metrics\":[{\"values\":[\"2\"]}]}],\"totals\":[{\"values\":[\"300\"]}],\"rowCount\":73,\"minimums\":[{\"values\":[\"1\"]}],\"maximums\":[{\"values\":[\"229\"]}],\"isDataGolden\":true}}]}";

            Records records = mapper.readValue(json, Records.class);

            /*JsonNode jsonNode = mapper.readValue(in,
                    JsonNode.class);
            String jsonString = mapper.writeValueAsString(jsonNode);
            System.out.println(jsonString);*/
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        // for static access, uses the class name directly
//        InputStream is = GoogleAnalyticsTest.class.getClassLoader().getResourceAsStream("file.txt");

       /* try {
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();



            // convert JSON file to map
            Map<?, ?> map = mapper.readValue(Paths.get("book.json").toFile(), Map.class);

            // print map entries
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }*/


    }

    @Test
    public void shouldGetRecordsGivenAGoogleAnalyticsJsonResponseThatHasRecords() {
        Records records = ga.getRecords();
        assertEquals(records.count(), 0);
    }

}
