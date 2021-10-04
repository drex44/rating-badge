package com.example.ratingbadge.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Getter
@Builder
public class WSUpdate<T> {
    @Singular
    private List<T> updates;
    @Singular
    private List<T> additions;
    @Singular
    private List<T> deletes;
}
