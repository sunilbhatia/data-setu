package dev.sunilb.datasetu.connectors.shopify;

import dev.sunilb.datasetu.exceptions.DataSetuAuthException;
import dev.sunilb.datasetu.exceptions.DataSetuException;
import dev.sunilb.datasetu.utils.FileUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Map;

public class ShopifyAdminService {

    public static String executeAndGetData(ShopifyAdminRequest shopifyRequest) throws DataSetuAuthException {

        final String jsonBody = shopifyRequest.getRequestJsonBody();
        final String apiURL = shopifyRequest.getShopifyAdminAPI();
        final Map<String, String> headers = shopifyRequest.getHeaders();

        HttpClient client = HttpClient.newHttpClient();

        final HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(apiURL))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody));
        headers.forEach(builder::header);
        final HttpRequest request = builder.build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (response.statusCode() != 200) {
            if (response.statusCode() == 401 || response.statusCode() == 403) {
                throw new DataSetuAuthException("Shopify Auth Exception");
            } else if (response.statusCode() == 400) {
                throw new DataSetuException("Bad Request. Response from ShopifyAdmin: " + response.body());
            } else {
                throw new DataSetuException("Unknown Error response from ShopifyAdmin: " + response.body());
            }
        }

        /*Date d = new Date();
        String fileName = "/filepath/location/" + d.getTime() + ".txt";
        FileUtils.saveResponseStringToFile(fileName, response);*/

        return response.body();
    }

}
