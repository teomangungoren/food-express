package com.foodexpress.restaurantservice.controller;


import com.foodexpress.restaurantservice.domain.model.Category;
import com.foodexpress.restaurantservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<Category> saveBook(@Validated @RequestBody String categoryName) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.saveCategory(categoryName));
    }

}
