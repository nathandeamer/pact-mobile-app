package com.nathandeamer.mobileapp.customerdetails;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerDetailsResponseServiceTest {

  @Mock
  private CustomerDetailsClient customerDetailsClientMock;

  @InjectMocks
  private CustomerDetailsService underTest;

  @Test
  public void shouldGetCustomerDetails() {
    when(customerDetailsClientMock.getCustomer(any())).thenReturn(
        CustomerDetailsResponse.builder()
            .firstName("Harry")
            .lastName("Potter")
        .build());

    CustomerDetailsResponse result = underTest.getCustomerDetails("1234");

    assertThat(result.getFirstName()).isEqualTo("Harry");
    assertThat(result.getLastName()).isEqualTo("Potter");
  }

}