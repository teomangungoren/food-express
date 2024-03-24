package com.foodexpress.restaurantservice.domain.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String categoryId;
}
