package com.example.ratingbadge.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;


@Getter
@Setter
@Entity
@Table(name = "product")
public class Product extends BaseUUID {
    private String name;
    private String description;
    private float avgStars;
}
