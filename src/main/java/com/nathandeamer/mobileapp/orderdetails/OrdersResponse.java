package com.nathandeamer.mobileapp.orderdetails;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OrdersResponse {

  private int id;
  private String name;
  private List<Item> items;

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Item {
    private String sku;
    private String description;
    private int qty;
  }

}
