package com.example.ratingbadge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchRequest {
    private Integer page;
    private Integer pageSize;
}
