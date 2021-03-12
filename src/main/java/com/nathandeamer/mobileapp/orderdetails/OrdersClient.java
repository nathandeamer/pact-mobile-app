package com.nathandeamer.mobileapp.orderdetails;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "orders", url = "${application.ordersUrl}")
public interface OrdersClient {

  @GetMapping(value = "/{orderId}", consumes = APPLICATION_JSON_VALUE)
  OrdersResponse getOrder(@PathVariable(name = "orderId") int orderId);

}

