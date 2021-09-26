package com.example.ratingbadge.dto;

import com.example.ratingbadge.model.Product;
import com.example.ratingbadge.model.Rating;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = ProductResolver.class)
public interface DefaultMapper {

    @Mapping(source = "productId", target = "product")
    Rating toRating(RatingDto dto);

    @Mapping(source = "product.id", target = "productId")
    RatingDto toRatingDto(Rating rating);

    Product toProduct(ProductDto productDto);

    ProductDto toProductDto(Product product);
}
