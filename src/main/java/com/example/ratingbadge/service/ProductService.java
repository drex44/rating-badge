package com.example.ratingbadge.service;

import com.example.ratingbadge.dto.ProductSearchRequest;
import com.example.ratingbadge.model.Product;
import com.example.ratingbadge.model.Rating;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Rating addRating(Rating rating) throws RecordNotFoundException;

    void deleteRating(UUID ratingId);

    Rating updateRating(Rating ratingId) throws RecordNotFoundException;

    List<Rating> getAllRatings(UUID productId) throws RecordNotFoundException;

    Product addNewProduct(Product product);

    Iterable<Product> getProducts();

    List<Product> getAllProducts(ProductSearchRequest searchRequest);

    Product getProduct(UUID productId) throws RecordNotFoundException;
}
