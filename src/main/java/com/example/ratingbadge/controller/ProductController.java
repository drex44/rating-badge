package com.example.ratingbadge.controller;

import com.example.ratingbadge.dto.DefaultMapper;
import com.example.ratingbadge.dto.ProductDto;
import com.example.ratingbadge.dto.RatingDto;
import com.example.ratingbadge.model.Product;
import com.example.ratingbadge.service.ProductService;
import com.example.ratingbadge.service.RecordNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private DefaultMapper defaultMapper;

    @GetMapping("/{productId}/ratings")
    public List<RatingDto> getAllRatings(@PathVariable UUID productId) {
        try {
            return productService.getAllRatings(productId)
                    .stream()
                    .map(rating -> defaultMapper.toRating(rating))
                    .collect(Collectors.toList());
        } catch (RecordNotFoundException e) {
            log.info("Product not found with id {}", productId, e);
            return Collections.emptyList();
        }
    }

    @PostMapping("/new")
    public ProductDto addNewProduct(@RequestBody ProductDto product) {
        Product newProduct = productService.addNewProduct(defaultMapper.toProduct(product));
        return defaultMapper.toProductDto(newProduct);
    }

}
