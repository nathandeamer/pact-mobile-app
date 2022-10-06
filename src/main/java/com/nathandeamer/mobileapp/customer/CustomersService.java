package com.nathandeamer.mobileapp.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomersService {

  private final CustomersClient customersClient;

  public CustomerResponse getCustomer(UUID customerId) {
      return customersClient.getCustomer(customerId);
  }

}
