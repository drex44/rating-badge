package com.example.ratingbadge.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating extends BaseUUID {

    @OneToOne(fetch = FetchType.LAZY)
    private Product product;

    private String review;

    private int stars;
}
