package com.online_shopping.dto;

import lombok.Data;

@Data
public class OrderResponse {
    private Long id;
    private String datePlaced;
    private String orderStatus;
    private UserDto user;
}
