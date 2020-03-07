package com.sapo.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Attribute {
    @Id
    @Column(name = "attribute_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attributeId;
    @Basic
    @Column(name = "attribute_name")
    private String attributeName;
    @OneToMany(
            mappedBy = "attribute",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<AttributeValue> attributeValues = new ArrayList<>();

    public void addAttValues(AttributeValue value) {
        attributeValues.add(value);
        value.setAttribute(this);
    }

    public void removeValues(AttributeValue value) {
        attributeValues.remove(value);
        value.setAttribute(null);
    }
}
