package com.foodexpress.restaurantservice.service;

import com.foodexpress.restaurantservice.domain.model.Category;
import com.foodexpress.restaurantservice.domain.model.Product;
import com.foodexpress.restaurantservice.domain.request.ProductRequest;
import com.foodexpress.restaurantservice.domain.response.ProductResponse;
import com.foodexpress.restaurantservice.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;


    public Category loadCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category saveCategory(String name) {
        return categoryRepository.save(new Category(name));
    }




}
