package com.foodexpress.restaurantservice.repository;

import com.foodexpress.restaurantservice.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,String>{

}
