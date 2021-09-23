package com.example.ratingbadge.dto;

import com.example.ratingbadge.model.Product;
import com.example.ratingbadge.model.Rating;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DefaultMapper {

    Rating toRating(RatingDto dto);

    RatingDto toRating(Rating rating);

    Product toProduct(ProductDto productDto);

    ProductDto toProductDto(Product product);
}
