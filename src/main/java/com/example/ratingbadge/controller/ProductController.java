package com.example.ratingbadge.controller;

import com.example.ratingbadge.dto.ProductDto;
import com.example.ratingbadge.dto.ProductSearchRequest;
import com.example.ratingbadge.dto.RatingDto;
import com.example.ratingbadge.dto.WSUpdate;
import com.example.ratingbadge.mapper.DefaultMapper;
import com.example.ratingbadge.model.Product;
import com.example.ratingbadge.model.Rating;
import com.example.ratingbadge.service.ProductService;
import com.example.ratingbadge.service.RecordNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/product/{productId}/ratings")
    public void getAllRatings(@DestinationVariable UUID productId) {
        try {
            List<RatingDto> ratings = productService.getAllRatings(productId)
                    .stream()
                    .map(rating -> defaultMapper.toRatingDto(rating))
                    .collect(Collectors.toList());
            sendNewRatingsWSUpdate(productId, ratings);
        } catch (RecordNotFoundException e) {
            log.info("Product not found with id {}", productId, e);
        }
    }

    @MessageMapping("/product/{productId}")
    public void getProduct(@DestinationVariable UUID productId) {
        ProductDto productDto = getProductById(productId);
        sendProductUpdates(productDto);
    }

    private ProductDto getProductById(@PathVariable UUID productId) {
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
            if (ratingDto.getProductId() == null) {
                log.debug("Product id is required");
                return null;
            }
            if (ratingDto.getStars() > 5) {
                log.debug("Invalid stars in new rating");
                return null;
            }
            Rating savedRating = productService.addRating(defaultMapper.toRating(ratingDto));
            RatingDto rating = defaultMapper.toRatingDto(savedRating);

            sendNewRatingsWSUpdate(rating.getProductId(), Collections.singletonList(rating));
            sendProductUpdates(getProductById(rating.getProductId()));
            return rating;
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

    private void sendNewRatingsWSUpdate(UUID productId, List<RatingDto> ratings) {
        WSUpdate<RatingDto> wsUpdate = WSUpdate.<RatingDto>builder().additions(ratings).build();

        simpMessagingTemplate.setUserDestinationPrefix("/topic");
        simpMessagingTemplate.convertAndSend("/topic/product/" + productId + "/ratings", wsUpdate);
    }

    private void sendProductUpdates(ProductDto productDto) {
        if (productDto == null) {
            return;
        }

        simpMessagingTemplate.setUserDestinationPrefix("/topic");
        simpMessagingTemplate.convertAndSend("/topic/product/" + productDto.getId(), productDto);
    }

}
