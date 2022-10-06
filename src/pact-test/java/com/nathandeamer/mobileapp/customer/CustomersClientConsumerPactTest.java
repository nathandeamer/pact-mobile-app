package com.nathandeamer.mobileapp.customer;

import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@ExtendWith(SpringExtension.class)
@PactTestFor(providerName = "pact-customer", port = "8888")
@SpringBootTest({
        "application.customersUrl: localhost:8888/customers"
})
public class CustomersClientConsumerPactTest {

    private static final String CONSUMER_NAME = "pact-mobile-app";

    private static final UUID CUSTOMER_ID = UUID.randomUUID();
    private static final String FIRST_NAME = "Nathan";
    private static final String LAST_NAME = "Deamer";
    public static final String ADDRESS = "123 Legend street";

    @Autowired
    private CustomersClient customersClient;

    @Pact(consumer = CONSUMER_NAME)
    public V4Pact getCustomerById(PactBuilder builder) {
        return builder
                .usingLegacyDsl()
                .given("A customer exists")
                .uponReceiving("A request to get a customer")
                .pathFromProviderState("/customers/${customerId}", "/customers/" + CUSTOMER_ID)
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(Objects.requireNonNull(new PactDslJsonBody()
                                .uuid("id", CUSTOMER_ID)
                                .stringType("firstName", FIRST_NAME)
                                .stringType("lastName", LAST_NAME)
                                .stringType("address", ADDRESS)
                        )
                ).toPact(V4Pact.class);
    }

    @PactTestFor(pactMethod = "getCustomerById")
    @Test
    public void shouldGetCustomerById() {
        CustomerResponse result = customersClient.getCustomer(CUSTOMER_ID);
        assertThat(result)
                .isEqualTo(
                        CustomerResponse.builder()
                                .id(CUSTOMER_ID)
                                .firstName(FIRST_NAME)
                                .lastName(LAST_NAME)
                                .address(ADDRESS)
                                .build());
    }

}