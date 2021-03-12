package com.nathandeamer.mobileapp.orderdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersService {

  private final OrdersClient ordersClient;

  public OrdersResponse getOrderDetails(int orderId) {
      return ordersClient.getOrder(orderId);
  }

}
