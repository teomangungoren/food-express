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

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<ProductResponse> saveProduct(@Validated @RequestBody ProductRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.saveProduct(request));
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponse>> listProducts() {
        System.out.println("ss");
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }


}