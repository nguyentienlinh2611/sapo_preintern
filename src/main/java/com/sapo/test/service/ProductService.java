package com.sapo.test.service;

import com.sapo.test.entity.*;
import com.sapo.test.model.AttributeModel;
import com.sapo.test.model.CategoryModel;
import com.sapo.test.model.ProductModel;
import com.sapo.test.model.ResponseModel;
import com.sapo.test.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private AttributeValueRepository attributeValueRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    public Categories createCategory(CategoryModel newCategory) {
        Categories categories = new Categories();
        categories.setCategoryName(newCategory.getCategoryName());
        categories.setActiveFlg(1);
        return categoriesRepository.save(categories);
    }

    public AttributeValue checkAttribute(AttributeModel newAttribute) {
        Attribute newAtt = new Attribute();
        AttributeValue newAttValue = new AttributeValue();
        //Neu da ton tai attribute_value
        if (newAttribute.getAttributeValueId() != null) {
            newAttValue = attributeValueRepository.findById(newAttribute.getAttributeValueId()).orElseThrow(() -> new RuntimeException("Không tìm thấy thuộc tính này"));
            if (!newAttValue.getAttribute().getAttributeId().equals(newAttribute.getAttributeId()) || !newAttValue.getAttributeValue().equals(newAttribute.getAttributeValue())) {
                throw new RuntimeException("Du lieu khong phu hop!");
            }
        } else {
            //Neu ton tai attribute nhung chua co attribute_value
            if (newAttribute.getAttributeId() != null) {
                newAtt = attributeRepository.findById(newAttribute.getAttributeId()).orElseThrow(() -> new RuntimeException("Không tìm thấy thuộc tính này"));
                if (!newAtt.getAttributeName().equals(newAttribute.getAttributeName())) {
                    throw new RuntimeException("Du lieu khong phu hop!");
                }
                newAttValue.setAttributeValue(newAttribute.getAttributeValue());
                newAtt.addAttValues(newAttValue);
            } else {
                newAtt.setAttributeName(newAttribute.getAttributeName());
                newAttValue.setAttributeValue(newAttribute.getAttributeValue());
                newAtt.addAttValues(newAttValue);
                attributeRepository.save(newAtt);
            }
        }
        return newAttValue;
    }

    @Transactional
    public ResponseModel createProduct(ProductModel newProduct) {
        Product product = new Product();
        //Them thong tin san pham
        //Tao category neu chua ton tai category nay
        if (newProduct.getProductCategoryId() == null && !StringUtils.isEmpty(newProduct.getProductCategoryName())) {
            Categories newCatergory = createCategory(new CategoryModel(newProduct.getProductCategoryName()));
            product.setProductCategoryId(newCatergory.getCategoryId());
        } else product.setProductCategoryId(newProduct.getProductCategoryId());
        product.setProductName(newProduct.getProductName());
        product.setActiveFlg(1);
        //Them thong tin ve cac phien ban
        newProduct.getVariants().forEach(item -> {
            Variant newVariant = new Variant();
            newVariant.setActiveFlg(1);
            if (item.getAttributes().size() > 3)
                throw new RuntimeException("Khong the tao qua 3 thuoc tinh cho mot phien ban!");
            item.getAttributes().forEach(att -> {
                AttributeValue newAttValue = checkAttribute(att);
                newVariant.addAttValue(newAttValue);
            });
            //variantRespository.save(newVariant);
            product.addVariants(newVariant);
        });
        productRepository.save(product);
        //Luu thong tin san pham
        //Product res = productRepository.save(product);
        return new ResponseModel(HttpStatus.OK.value(), "Them san pham moi thanh cong!");
    }

    public ResponseModel getProducts() {
        List<Product> productList = productRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
        List<ProductModel> products = productList.stream().map(ProductModel::mapper).collect(Collectors.toList());
        return new ResponseModel(HttpStatus.OK.value(), products);
    }

    public ResponseModel getProducts(Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Khong tim thay san pham trong kho!"));
        return new ResponseModel(HttpStatus.OK.value(), ProductModel.mapper(product));
    }

    @Transactional
    public ResponseModel updateProduct(ProductModel updatedProduct) {
        Product product = productRepository.findById(updatedProduct.getProductId()).orElseThrow(() -> new RuntimeException("Khong tim thay san pham can cap nhat!"));
        //Update info product
        if (updatedProduct.getProductName() != null && !product.getProductName().equals(updatedProduct.getProductName())) {
            product.setProductName(updatedProduct.getProductName());
        }
        //Update category of product
        if (updatedProduct.getProductCategoryId() != null && !updatedProduct.getProductCategoryId().equals(product.getProductCategoryId())) {
            Optional<Categories> category = categoriesRepository.findById(updatedProduct.getProductCategoryId());
            if (category.isPresent() && category.get().getCategoryName().equals(updatedProduct.getProductCategoryName())) {
                product.setProductCategoryId(updatedProduct.getProductCategoryId());
            } else if (!category.isPresent()) {
                Categories newCatergory = createCategory(new CategoryModel(updatedProduct.getProductCategoryName()));
                product.setProductCategoryId(newCatergory.getCategoryId());
            } else throw new RuntimeException("Ten danh muc cap nhat khong hop le!");
        }
        //Update variant of product
        updatedProduct.getVariants().forEach(item -> {
            Variant variant = product.getVariants().stream().filter(var -> var.getVariantId().equals(item.getVariantId())).findFirst().orElse(null);
            if (variant != null) {
                variant.removeAllAttValue();
                item.getAttributes().forEach(att->{
                    AttributeValue newAtt = checkAttribute(att);
                    variant.addAttValue(newAtt);
                });
            } else {
                //Tao moi 1 variant cho san pham tren
                Variant newVariant = new Variant();
                newVariant.setActiveFlg(1);
                item.getAttributes().forEach(att->{
                    AttributeValue newAtt = checkAttribute(att);
                    newVariant.addAttValue(newAtt);
                });
                product.addVariants(newVariant);
            }
        });
        return new ResponseModel(HttpStatus.OK.value(), "Cap nhat san pham thanh cong!");
    }

    @DeleteMapping
    public void deleteProduct(Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Khong tim thay san pham can cap nhat!"));
        productRepository.delete(product);
    }
}
