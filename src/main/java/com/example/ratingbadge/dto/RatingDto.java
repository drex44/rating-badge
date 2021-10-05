package com.example.ratingbadge.dto;

import com.example.ratingbadge.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RatingDto {
    @Nullable
    private UUID id;
    private UUID productId;
    private String review;
    private float stars;
}
