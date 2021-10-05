package com.example.ratingbadge.service;

import com.example.ratingbadge.dto.ProductSearchRequest;
import com.example.ratingbadge.model.Product;
import com.example.ratingbadge.model.Rating;
import com.example.ratingbadge.repository.ProductRepository;
import com.example.ratingbadge.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DefaultProductService implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating addRating(Rating rating) throws RecordNotFoundException {
        if (rating.getProduct() == null) {
            throw new RecordNotFoundException();
        }
        rating.setId(UUID.randomUUID());
        Rating newRating = ratingRepository.save(rating);
        updateProductAvgStars(rating.getProduct());

        return newRating;
    }

    private void updateProductAvgStars(Product product) {
        Double avgStars = ratingRepository.avgRatingForProduct(product.getId());
        product.setAvgStars(BigDecimal.valueOf(avgStars).setScale(2, RoundingMode.HALF_UP).floatValue());
        productRepository.save(product);
    }

    @Override
    public void deleteRating(UUID ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    @Override
    public Rating updateRating(Rating rating) throws RecordNotFoundException {
        Optional<Rating> ratingOptional = ratingRepository.findById(rating.getId());
        if (ratingOptional.isEmpty()) {
            throw new RecordNotFoundException();
        }
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAllRatings(UUID productId) throws RecordNotFoundException {

        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new RecordNotFoundException();
        }
        return ratingRepository.findAllByProductIdOrderByCreatedOnDesc(productId);
    }

    @Override
    public Product addNewProduct(Product product) {
        product.setId(UUID.randomUUID());
        product.setAvgStars(0);
        return productRepository.save(product);
    }

    @Override
    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProducts(ProductSearchRequest searchRequest) {
        if (searchRequest.getPage() == 0 && searchRequest.getPageSize() == 0) {
            return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                    .collect(Collectors.toList());
        }
        Pageable queryParams = PageRequest.of(searchRequest.getPage(), searchRequest.getPageSize());
        return productRepository.findAll(queryParams).toList();
    }

    @Override
    public Product getProduct(UUID productId) throws RecordNotFoundException {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new RecordNotFoundException();
        }
        return productOptional.get();
    }
}
