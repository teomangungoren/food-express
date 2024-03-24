package com.foodexpress.restaurantservice.domain.mapper;

import com.foodexpress.restaurantservice.domain.model.Product;
import com.foodexpress.restaurantservice.domain.response.ProductResponse;
import org.mapstruct.Mapper;

@Mapper
public interface ProductConverter {
     Product toProduct(ProductResponse productResponse);
    ProductResponse toProductResponse(Product product);
}
