package com.sapo.test.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sapo.test.entity.Product;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductModel {
    private Integer productId;
    @NotBlank
    private String productName;
    private Integer productCategoryId;
    private String productCategoryName;
    private List<VariantModel> variants;

    public static ProductModel mapper(Product product) {
        ProductModel productModel = new ProductModel();
        productModel.setProductId(product.getProductId());
        productModel.setProductName(product.getProductName());
        productModel.setProductCategoryId(product.getProductCategoryId());
        List<VariantModel> variants = new ArrayList<>();
        product.getVariants().forEach(variant->{
            VariantModel resVariant = new VariantModel();
            resVariant.setVariantId(variant.getVariantId());
            List<AttributeModel> attributes = new ArrayList<>();
            variant.getAttributes().forEach(attribute -> {
                AttributeModel resAtt = new AttributeModel();
                resAtt.setAttributeId(attribute.getAttribute().getAttributeId());
                resAtt.setAttributeName(attribute.getAttribute().getAttributeName());
                resAtt.setAttributeValueId(attribute.getAttributeValueId());
                resAtt.setAttributeValue(attribute.getAttributeValue());
                attributes.add(resAtt);
            });
            resVariant.setAttributes(attributes);
            resVariant.setActiveFlg(variant.getActiveFlg());
            variants.add(resVariant);
        });
        productModel.setVariants(variants);
        return productModel;
    }
}


