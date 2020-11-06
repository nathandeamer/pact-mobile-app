package com.nathandeamer.mobileapp.customerdetails;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customerdetails", url = "${application.customerDetailsUrl}")
public interface CustomerDetailsClient {

  @GetMapping(value = "/{customerId}", consumes = APPLICATION_JSON_VALUE)
  CustomerDetailsResponse getCustomer(@PathVariable(name = "customerId") String customerId);

}

