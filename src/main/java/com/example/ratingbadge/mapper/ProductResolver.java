package com.example.ratingbadge.mapper;

import com.example.ratingbadge.model.Product;
import com.example.ratingbadge.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class ProductResolver {
    @Autowired
    private ProductRepository productRepository;

    Product findById(UUID productId) {
        return productRepository.findById(productId).orElse(null);
    }
}
