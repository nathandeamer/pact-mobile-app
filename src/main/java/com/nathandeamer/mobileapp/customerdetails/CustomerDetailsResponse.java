package com.nathandeamer.mobileapp.customerdetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailsResponse {

  private String firstName;
  private String lastName;


}
