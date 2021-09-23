package com.example.ratingbadge.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RatingDto {
    private UUID productId;
    private String review;
    private int stars;
}
