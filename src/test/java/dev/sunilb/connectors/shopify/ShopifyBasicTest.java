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

    private ShopifyAdmin saOrderCustomer;
    private ShopifyAdmin saCustomer;
    private ShopifyAdmin saOrderCustomerWithRefunds;

    @BeforeClass
    public void setup() {

//        String jsonBasic = getResourceStreamAsString("shopify/responses/order-customer/basic-customer.json");

        try {
            String jsonOrderCustomer = getResourceStreamAsString("shopify/responses/order-customer/basic-order-customer.json");
            ShopifySource shopifyOrderCustomerSource = mock(ShopifySource.class);
            when(shopifyOrderCustomerSource.fetch()).thenReturn(jsonOrderCustomer);
            this.saOrderCustomer = new ShopifyAdmin(shopifyOrderCustomerSource, "");

            String jsonOrderCustomerWithRefunds = getResourceStreamAsString("shopify/responses/order-customer/basic-order-customer-with-refund-array.json");
            ShopifySource shopifyOrderCustomerSourceWithRefunds = mock(ShopifySource.class);
            when(shopifyOrderCustomerSourceWithRefunds.fetch()).thenReturn(jsonOrderCustomerWithRefunds);
            this.saOrderCustomerWithRefunds = new ShopifyAdmin(shopifyOrderCustomerSourceWithRefunds, "");

            String jsonCustomer = getResourceStreamAsString("shopify/responses/order-customer/basic-customer.json");
            ShopifySource shopifyCustomerSource = mock(ShopifySource.class);
            when(shopifyCustomerSource.fetch()).thenReturn(jsonCustomer);
            this.saCustomer = new ShopifyAdmin(shopifyCustomerSource, "");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetRecordsGivenAShopifyJsonResponseThatHasRecords() {
        Records orderCustomer = this.saOrderCustomer.getRecords();
        assertEquals(orderCustomer.fieldsCount(), 13);
        assertEquals(orderCustomer.count(), 50);


        Records orderCustomerWithRefunds = this.saOrderCustomerWithRefunds.getRecords();
        assertEquals(orderCustomerWithRefunds.fieldsCount(), 15);
        assertEquals(orderCustomerWithRefunds.count(), 50);


        Records customer = this.saCustomer.getRecords();
        assertEquals(customer.fieldsCount(), 5);
        assertEquals(customer.count(), 40);
    }

}
