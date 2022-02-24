package com.nathandeamer.mobileapp.orderdetails;

import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@ExtendWith(SpringExtension.class)
@PactTestFor(providerName = "orders", port = "8888")
@SpringBootTest({
        "application.ordersUrl: localhost:8888/orders"
})
public class OrdersClientConsumerPactTest {

    private static final String CONSUMER_NAME = "mobileapp";

    private static final int ORDER_ID = 1234;
    private static final String DESCRIPTION = "New York City Pass";
    private static final int QUANTITY = 1;
    private static final String SKU = "NYC";

    @Autowired
    private OrdersClient ordersClient;

    @Pact(consumer = CONSUMER_NAME)
    public V4Pact getOrderById(PactBuilder builder) {
        return builder
                .usingLegacyDsl() // TODO: There is little documentation on writing v4 pacts currently.
                .given("An order exists")
                .uponReceiving("A request to get an order")
                .pathFromProviderState("/orders/${orderNumber}", "/orders/" + ORDER_ID)
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(Objects.requireNonNull(new PactDslJsonBody()
                        .integerType("id", ORDER_ID)
                        .minArrayLike("items", 1)
                        .stringType("description", DESCRIPTION)
                        .integerType("qty", QUANTITY)
                        .stringType("sku", SKU)
                        .closeArray())
                ).toPact(V4Pact.class);
    }

    @PactTestFor(pactMethod = "getOrderById")
    @Test
    public void shouldGetOrderById() {
        OrdersResponse result = ordersClient.getOrder(ORDER_ID);
        assertThat(result)
                .isEqualTo(
                        OrdersResponse.builder()
                                .id(ORDER_ID)
                                .items(ImmutableList.of(OrdersResponse.Item.builder()
                                        .description(DESCRIPTION)
                                        .qty(QUANTITY)
                                        .sku(SKU)
                                        .build()))
                                .build());
    }

}