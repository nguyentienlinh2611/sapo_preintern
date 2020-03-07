package com.sapo.test.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Basic
    @Column(name = "product_name")
    private String productName;

    @Basic
    @Column(name = "product_category_id")
    private Integer productCategoryId;

    @Basic
    @Column(name = "created_date", insertable = false)
    private Timestamp createdDate;

    @Basic
    @Column(name = "updated_date", insertable = false)
    private Timestamp updatedDate;

    @Basic
    @Column(name = "active_flg")
    private Integer activeFlg;

    @OneToMany(
            mappedBy = "product",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            orphanRemoval= true
    )
    private List<Variant> variants = new ArrayList<>();

    public void addVariants(Variant variant) {
        variants.add(variant);
        variant.setProduct(this);
    }

    public void removeVariants(Variant variant) {
        variants.remove(variant);
        variant.setProduct(null);
    }
    //    @ManyToOne
    //    @JoinColumn(name = "product_catalog_id", referencedColumnName = "category_id")
    //    private Categories categoriesByProductCatalogId;
    //    @OneToMany(mappedBy = "productByProductId")
    //    private Collection<ProductAttribute> productAttributesByProductId;


}
