package com.example.ratingbadge.controller;

import com.example.ratingbadge.dto.DefaultMapper;
import com.example.ratingbadge.dto.ProductDto;
import com.example.ratingbadge.dto.ProductSearchRequest;
import com.example.ratingbadge.dto.RatingDto;
import com.example.ratingbadge.model.Product;
import com.example.ratingbadge.model.Rating;
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
                    .map(rating -> defaultMapper.toRatingDto(rating))
                    .collect(Collectors.toList());
        } catch (RecordNotFoundException e) {
            log.info("Product not found with id {}", productId, e);
            return Collections.emptyList();
        }
    }

    @GetMapping("/{productId}")
    public ProductDto getProduct(@PathVariable UUID productId) {
        try {
            Product product = productService.getProduct(productId);

            return defaultMapper.toProductDto(product);
        } catch (RecordNotFoundException e) {
            log.info("Product not found with id {}", productId, e);
        }
        return null;
    }

    @PostMapping("/newRating")
    public RatingDto addNewRating(@RequestBody RatingDto ratingDto) {
        try {
            if (ratingDto.getStars() > 5) {
                log.debug("Invalid stars in new rating");
                return null;
            }
            Rating savedRating = productService.addRating(defaultMapper.toRating(ratingDto));

            return defaultMapper.toRatingDto(savedRating);
        } catch (RecordNotFoundException e) {
            log.info("Product not found with id {}", ratingDto.getProductId(), e);
        }
        return null;
    }

    @PostMapping("/new")
    public ProductDto addNewProduct(@RequestBody ProductDto product) {
        Product newProduct = productService.addNewProduct(defaultMapper.toProduct(product));

        return defaultMapper.toProductDto(newProduct);
    }

    @PostMapping("/query")
    public List<ProductDto> getAllProducts(@RequestBody ProductSearchRequest searchRequest) {
        List<Product> products = productService.getAllProducts(searchRequest);

        return products.stream()
                .map(product -> defaultMapper.toProductDto(product))
                .collect(Collectors.toList());
    }

}
