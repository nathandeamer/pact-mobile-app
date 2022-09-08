package com.nathandeamer.mobileapp.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "customers", url = "${application.customersUrl}")
public interface CustomersClient {

  @GetMapping(value = "/{customerId}", consumes = APPLICATION_JSON_VALUE)
  CustomerResponse getCustomer(@PathVariable(name = "customerId") int customerId);

}

