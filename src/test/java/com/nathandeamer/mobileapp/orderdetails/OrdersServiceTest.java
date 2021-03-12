package com.nathandeamer.mobileapp.orderdetails;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrdersServiceTest {

  private static final int ORDER_ID = 1234;

  @Mock
  private OrdersClient ordersClientMock;

  @InjectMocks
  private OrdersService underTest;

  @Test
  public void shouldGetOrder() {
    when(ordersClientMock.getOrder(anyInt())).thenReturn(
        OrdersResponse.builder()
                .id(ORDER_ID)
                .items(ImmutableList.of(OrdersResponse.Item.builder()
                        .sku("NYC")
                        .qty(1)
                        .description("New York City Pass")
                        .build()))
        .build());

    OrdersResponse result = underTest.getOrderDetails(ORDER_ID);

    assertThat(result.getId()).isEqualTo(ORDER_ID);
    assertThat(result.getItems()).hasSize(1);
  }

}