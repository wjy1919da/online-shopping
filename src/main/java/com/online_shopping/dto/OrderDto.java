package com.online_shopping.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private List<OrderItemDto> order;
}
