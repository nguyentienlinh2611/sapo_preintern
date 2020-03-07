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
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Variant {
    @Id
    @Column(name = "variant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer variantId;

    @Basic
    @Column(name = "active_flg")
    private Integer activeFlg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    @JsonIgnore
    private Product product;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "variant_attribute",
            joinColumns = @JoinColumn(name = "variant_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_value_id")
    )
    private List<AttributeValue> attributes = new ArrayList<>();

    public void addAttValue(AttributeValue attributeValue) {
        attributes.add(attributeValue);
        attributeValue.getVariants().add(this);
    }
    public void removeAttValue(AttributeValue attributeValue) {
        attributes.remove(attributeValue);
        attributeValue.getVariants().remove(this);
    }
    public void removeAllAttValue() {
        attributes.removeAll(attributes);
        attributes.forEach(item -> item.getVariants().remove(this));
    }
}
