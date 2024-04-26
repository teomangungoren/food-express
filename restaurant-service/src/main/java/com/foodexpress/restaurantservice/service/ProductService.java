package com.foodexpress.restaurantservice.service;

import com.foodexpress.restaurantservice.domain.mapper.ProductConverter;
import com.foodexpress.restaurantservice.domain.model.Category;
import com.foodexpress.restaurantservice.domain.model.Product;
import com.foodexpress.restaurantservice.domain.request.ProductRequest;
import com.foodexpress.restaurantservice.domain.response.ProductResponse;
import com.foodexpress.restaurantservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final CategoryService categoryService;

    public ProductResponse saveProduct(ProductRequest request) {
        Category category = categoryService.loadCategory(request.getCategoryId());
        final Product product = productRepository.save(new Product("",request.getName(), request.getDescription(), request.getPrice(), request.getQuantity(), category));
        return productConverter.toProductResponse(product);
    }

    public ProductResponse getProduct(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
          return productConverter.toProductResponse(product);
    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(productConverter::toProductResponse)
                .collect(Collectors.toList());
    }


}
