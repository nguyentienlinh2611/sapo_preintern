package com.sapo.test.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class VariantModel {
    private Integer variantId;
    private List<AttributeModel> attributes;
    private Integer activeFlg;
}
