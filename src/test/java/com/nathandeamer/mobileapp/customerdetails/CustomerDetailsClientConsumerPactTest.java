package com.nathandeamer.mobileapp.customerdetails;

import static au.com.dius.pact.consumer.ConsumerPactBuilder.jsonBody;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import feign.FeignException;
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
public class CustomerDetailsClientConsumerPactTest {

  // Create a make file which runs the pact tests only!

  // Create a make fule which publishes the pact tests

  // Create a can i deploy script which stops me deploying the code to dev!

  // Same but to staging and prod

  private static final String PROVIDER_NAME = "customerdetails";
  private static final String CONSUMER_NAME = "mobileapp";

  public static final String CUSTOMER_ID = "1234";
  public static final String FIRST_NAME = "Harry";
  public static final String LAST_NAME = "Potter";

  @Rule
  public PactProviderRule mockProvider =
      new PactProviderRule(PROVIDER_NAME, "localhost", 8082, this);

  @Autowired
  private CustomerDetailsClient customerDetailsClient;

  @Pact(consumer=CONSUMER_NAME)
  public RequestResponsePact getCustomerDetailsById(PactDslWithProvider builder) {
    return builder
        .given("A customer exists")
        .uponReceiving("A request to get customer details by id")
        .path("/customerdetails/" + CUSTOMER_ID)
        .method("GET")
        .willRespondWith()
        .status(200)
        .body(
            jsonBody()
                .stringType("firstName", FIRST_NAME)
                .stringType("lastName", LAST_NAME)
        )
        .toPact();
  }

  @Test
  @PactVerification(fragment = "getCustomerDetailsById")
  public void shouldGetCustomerDetailsById() {
    CustomerDetailsResponse result = customerDetailsClient.getCustomer(CUSTOMER_ID);
    assertThat(result).isEqualTo(CustomerDetailsResponse.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build());
  }

}