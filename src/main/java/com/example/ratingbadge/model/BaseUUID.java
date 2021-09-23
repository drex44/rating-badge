package com.example.ratingbadge.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseUUID {
    @Id
    @Type(type = "uuid-char")
    private UUID id;


    public BaseUUID() {
        this.id = UUID.randomUUID();
    }
}
