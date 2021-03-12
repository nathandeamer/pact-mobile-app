package com.nathandeamer.mobileapp.orderdetails;

import static au.com.dius.pact.consumer.ConsumerPactBuilder.jsonBody;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.google.common.collect.ImmutableList;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("pact")
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class OrdersClientConsumerPactTest {

  private static final String PROVIDER_NAME = "orders";
  private static final String CONSUMER_NAME = "mobileapp";

  private static final int ORDER_ID = 1234;
  private static final String DESCRIPTION = "New York City Pass";
  private static final int QUANTITY = 1;
  private static final String SKU = "NYC";

  @Rule
  public PactProviderRule mockProvider =
      new PactProviderRule(PROVIDER_NAME, "localhost", 8082, this);

  @Autowired
  private OrdersClient ordersClient;

  @Pact(consumer=CONSUMER_NAME)
  public RequestResponsePact getOrderById(PactDslWithProvider builder) {
    return builder
        .given("An order with id 1234 exists")
        .uponReceiving("A request to get an order")
        .path("/orders/" + ORDER_ID)
        .method("GET")
        .willRespondWith()
        .status(200)
        .body(
            jsonBody()
              .integerType("id", ORDER_ID)
              .minArrayLike("items", 1)
                    .stringType("description", DESCRIPTION)
                    .integerType("qty", QUANTITY)
                    .stringType("sku", SKU)
              .closeArray()
        ).toPact();
  }

  @Test
  @PactVerification(fragment = "getOrderById")
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