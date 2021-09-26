package com.example.ratingbadge.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseUUID {
    @Id
    @Type(type = "uuid-char")
    private UUID id;


    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = ModelUtils.LONG_DATE_TIME_FORMAT)
    private Date createdOn;


    public BaseUUID() {
        this.id = UUID.randomUUID();
    }
}
