package dev.sunilb.connectors.shopify;

import dev.sunilb.datasetu.connectors.shopify.ShopifyAdmin;
import dev.sunilb.datasetu.connectors.shopify.ShopifySource;
import dev.sunilb.datasetu.entities.Records;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static dev.sunilb.helpers.TestHelpers.getResourceStreamAsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class ShopifyBasicTest {

    ShopifyAdmin saBasic;

    @BeforeClass
    public void setup() {

        try {
            String jsonBasic = getResourceStreamAsString("shopify/responses/order-customer/basic-order-customer.json");
            ShopifySource shopifyBasicSource = mock(ShopifySource.class);
            when(shopifyBasicSource.fetch()).thenReturn(jsonBasic);
            this.saBasic = new ShopifyAdmin(shopifyBasicSource);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetRecordsGivenAShopifyJsonResponseThatHasRecords() {
        Records records = this.saBasic.getRecords();
        assertEquals(records.fieldsCount(), 13);
        assertEquals(records.count(), 50);
    }

}
