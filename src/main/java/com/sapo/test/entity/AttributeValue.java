package com.sapo.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "attribute_value", schema = "sapo_test")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AttributeValue {
    @Id
    @Column(name = "attribute_value_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attributeValueId;

    @Basic
    @Column(name = "attribute_value")
    private String attributeValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "attribute_id", referencedColumnName = "attribute_id")
    private Attribute attribute;

    @ManyToMany(mappedBy = "attributes")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Variant> variants = new ArrayList<>();

}
