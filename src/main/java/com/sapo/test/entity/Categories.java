package com.sapo.test.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Categories {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer categoryId;
    @Basic
    @Column(name = "category_name")
    private String categoryName;
    @Basic
    @Column(name = "active_flg")
    private Integer activeFlg;
//    @OneToMany(mappedBy = "categoriesByProductCatalogId")
//    private Collection<Product> productsByCategoryId;

}
