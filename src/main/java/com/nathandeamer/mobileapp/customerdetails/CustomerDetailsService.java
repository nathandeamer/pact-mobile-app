package com.nathandeamer.mobileapp.customerdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerDetailsService {

  private final CustomerDetailsClient customerDetailsClient;

  public CustomerDetailsResponse getCustomerDetails(String customerId) {
      return customerDetailsClient.getCustomer(customerId);
  }

}
