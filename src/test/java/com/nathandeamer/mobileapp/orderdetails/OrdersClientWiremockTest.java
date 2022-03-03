package com.nathandeamer.mobileapp.orderdetails;

import com.atlassian.ta.wiremockpactgenerator.WireMockPactGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest({
        "application.ordersUrl: localhost:45678/orders" // Same port we've told WireMock to run on.
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrdersClientWiremockTest {

    private static String CONSUMER_NAME = "pact-mobile-app";
    private static String PROVIDER_NAME = "pact-order";

    private static final int ORDER_ID = 1234;
    private static final String DESCRIPTION = "New York City Pass";
    private static final int QUANTITY = 1;
    private static final String SKU = "NYC";

    private WireMockServer wireMockServer;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrdersClient ordersClient;

    @BeforeAll
    public void configureWiremock() {
        wireMockServer = new WireMockServer(45678);

        // Add the WireMockPactGenerator listener
        wireMockServer.addMockServiceRequestListener(
                WireMockPactGenerator
                        .builder(CONSUMER_NAME, PROVIDER_NAME)
                        .withRequestHeaderWhitelist(ACCEPT, CONTENT_TYPE)
                        .build());

        wireMockServer.start();
    }

    @Test
    public void getOrder() throws Exception {
        OrdersResponse response = OrdersResponse.builder()
                .id(ORDER_ID)
                .items(List.of(OrdersResponse.Item.builder()
                        .description(DESCRIPTION)
                        .qty(QUANTITY)
                        .sku(SKU)
                        .build()))
                .build();

        wireMockServer.stubFor(WireMock.get(urlPathEqualTo("/orders/" + ORDER_ID))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))
                ));

        OrdersResponse result = ordersClient.getOrder(ORDER_ID);

        assertThat(result).isEqualTo(response);
    }

}
