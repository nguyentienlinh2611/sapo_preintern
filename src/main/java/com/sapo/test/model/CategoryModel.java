package com.sapo.test.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryModel {
    private Integer categoryId;
    @NotBlank
    private String categoryName;

    private Integer activeFlg = 1;

    public CategoryModel(@NotBlank String categoryName) {
        this.categoryName = categoryName;
    }
}
