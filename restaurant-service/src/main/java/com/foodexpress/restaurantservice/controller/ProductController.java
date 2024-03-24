package com.foodexpress.restaurantservice.controller;

import com.foodexpress.restaurantservice.domain.request.ProductRequest;
import com.foodexpress.restaurantservice.domain.response.ProductResponse;
import com.foodexpress.restaurantservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<ProductResponse> saveBook(@Validated @RequestBody ProductRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.saveProduct(request));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> listBook() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getBook(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }


}