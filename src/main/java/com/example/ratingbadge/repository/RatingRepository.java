package com.example.ratingbadge.repository;

import com.example.ratingbadge.model.Rating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RatingRepository extends PagingAndSortingRepository<Rating, UUID> {
    List<Rating> findAllByProductIdOrderByCreatedOnDesc(UUID productId);

    @Query(value = "SELECT AVG(stars) FROM Rating WHERE product.id = ?1")
    Double avgRatingForProduct(UUID productId);
}
