package com.example.ratingbadge.repository;

import com.example.ratingbadge.model.Rating;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RatingRepository extends PagingAndSortingRepository<Rating, UUID> {
    List<Rating> findAllByProductId(UUID productId);
}
