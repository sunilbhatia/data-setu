package dev.sunilb.connectors.shopify;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.sunilb.datasetu.connectors.shopify.ShopifyRequestBuilder;

import static org.testng.Assert.*;

import dev.sunilb.datasetu.exceptions.DataSetuException;
import dev.sunilb.helpers.TestHelpers;
import org.testng.annotations.Test;

public class ShopifyRequestBuilderTest {

    @Test
    public void shouldBuildShopifyGraphQLRequestBasic() throws JsonProcessingException {

        ShopifyRequestBuilder shopifyRequestBuilder = ShopifyRequestBuilder
                .Builder()
                .withQueryRoot("orders")
                .first(100)
                .withFields("id name discountCode")
                .build();
        String requestJson = shopifyRequestBuilder.toString();
        assertTrue(TestHelpers.isJsonEqual(requestJson, "{\"query\": \"{orders(first:100){pageInfo {hasNextPage, hasPreviousPage} edges {cursor node {id name discountCode}}}}\"}"));
    }

    @Test
    public void shouldBuildShopifyGraphQLRequestWithQuery() throws JsonProcessingException {

        ShopifyRequestBuilder shopifyRequestBuilder = ShopifyRequestBuilder
                .Builder()
                .withQueryRoot("orders")
                .first(100)
                .withFields("id name discountCode")
                .withQuery("created_at:>2021-10-13")
                .build();
        String requestJson = shopifyRequestBuilder.toString();
        System.out.println(requestJson);
        assertTrue(TestHelpers.isJsonEqual(requestJson, "{\"query\": \"{orders(first:100,query:\\\"created_at:>2021-10-13\\\"){pageInfo {hasNextPage, hasPreviousPage} edges {cursor node {id name discountCode}}}}\"}"));
    }

    @Test(expectedExceptions = DataSetuException.class)
    public void showThrowExceptionWhenQueryRootIsNotSet() throws JsonProcessingException {

        ShopifyRequestBuilder shopifyRequestBuilder = ShopifyRequestBuilder
                .Builder()
                .first(100)
                .withFields("id name discountCode")
                .withQuery("created_at:>2021-10-13")
                .build();
    }

    @Test(expectedExceptions = DataSetuException.class)
    public void showThrowExceptionWhenFirstLastIsNotSet() throws JsonProcessingException {

        ShopifyRequestBuilder shopifyRequestBuilder = ShopifyRequestBuilder
                .Builder()
                .withQueryRoot("orders")
                .withFields("id name discountCode")
                .withQuery("created_at:>2021-10-13")
                .build();
    }

    @Test(expectedExceptions = DataSetuException.class)
    public void showThrowExceptionWhenFieldIsNotSet() throws JsonProcessingException {

        ShopifyRequestBuilder shopifyRequestBuilder = ShopifyRequestBuilder
                .Builder()
                .withQueryRoot("orders")
                .first(100)
                .withQuery("created_at:>2021-10-13")
                .build();
    }

}
