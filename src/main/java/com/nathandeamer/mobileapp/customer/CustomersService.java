package com.nathandeamer.mobileapp.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomersService {

  private final CustomersClient customersClient;

  public CustomerResponse getCustomer(int customerId) {
      return customersClient.getCustomer(customerId);
  }

}
