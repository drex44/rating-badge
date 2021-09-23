package com.example.ratingbadge.service;

import com.example.ratingbadge.model.Product;
import com.example.ratingbadge.model.Rating;
import com.example.ratingbadge.repository.ProductRepository;
import com.example.ratingbadge.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultProductService implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating addRating(UUID productId, Rating rating) throws RecordNotFoundException {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new RecordNotFoundException();
        }
        rating.setId(UUID.randomUUID());
        rating.setProduct(productOptional.get());
        return ratingRepository.save(rating);
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
        return ratingRepository.findAllByProductId(productId);
    }

    @Override
    public Product addNewProduct(Product product) {
        product.setId(UUID.randomUUID());
        return productRepository.save(product);
    }

    @Override
    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }
}
