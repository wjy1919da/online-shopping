package com.online_shopping.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private double wholesalePrice;
    private double retailPrice;
    private int quantity;
}
