package com.sapo.test.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AttributeModel {
    private Integer attributeId;
    private String attributeName;
    private Integer attributeValueId;
    private String attributeValue;
}
