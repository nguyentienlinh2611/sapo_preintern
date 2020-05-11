package com.sapo.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapo.test.controller.productController;
import com.sapo.test.entity.Product;
import com.sapo.test.model.ProductModel;
import com.sapo.test.model.ResponseModel;
import com.sapo.test.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(productController.class)
public class ProductRestTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenProductModel_thenCreateProduct() throws Exception {
        ProductModel productModel = new ProductModel();
        productModel.setProductName("Name");

        Product product = new Product();
        product.setProductName("Name");

        given(productService.createProduct(Mockito.any(ProductModel.class))).willReturn(new ResponseModel(HttpStatus.OK.value(), "Them san pham moi thanh cong!"));
        mockMvc.perform(post("/product")
                .content(mapper.writeValueAsString(productModel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$data.name", is(product.getProductName())));
        reset(productService);
    }

    @Test
    public void givenInvalidProductModel_thenCreateProduct() throws Exception {
        ProductModel productModel = new ProductModel();

        mockMvc.perform(post("/product")
                .content(mapper.writeValueAsString(productModel)))
                .andExpect(status().isBadRequest());
        reset(productService);
    }

    @Test
    public void whenServiceCreateProductThrowException_thenCreateProduct() throws Exception {
        ProductModel dummyProductModel = new ProductModel();
        dummyProductModel.setProductName("Whatever name");
        given(productService.createProduct(Mockito.any(ProductModel.class))).willThrow(RuntimeException.class);
        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dummyProductModel)))
                .andExpect(status().is4xxClientError());
        reset(productService);
    }

    @Test
    public void givenProduct_thenGetProducts() throws Exception {
        Product product = new Product();
        product.setProductName("Name");

        given(productService.getProducts()).willReturn(new ResponseModel(HttpStatus.OK.value(), Collections.singletonList(product)));
        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name", is(product.getProductName())));
        reset(productService);
    }

    @Test
    public void whenProductNotFound_thenGetProduct() throws Exception {
        given(productService.getProducts(Mockito.anyInt())).willThrow(RuntimeException.class);
        mockMvc.perform(get("/product"))
                .andExpect(status().is4xxClientError());
        reset(productService);
    }

    @Test
    public void givenProductModel_thenUpdateProduct() throws Exception {
        ProductModel dummyProductModel = new ProductModel();
        dummyProductModel.setProductName("Whatever name");

        given(productService.updateProduct(Mockito.any(ProductModel.class))).willReturn(new ResponseModel(HttpStatus.OK.value(), dummyProductModel));
        mockMvc.perform(put("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dummyProductModel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is(dummyProductModel.getProductName())));
        reset(productService);
    }

    @Test
    public void whenServiceUpdateProductThrowException_thenUpdateProduct() throws Exception{
        ProductModel dummyProductModel = new ProductModel();
        dummyProductModel.setProductName("Whatever name");

        given(productService.updateProduct(Mockito.any(ProductModel.class))).willThrow(RuntimeException.class);
        mockMvc.perform(put("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dummyProductModel)))
                .andExpect(status().is4xxClientError());
        reset(productService);
    }

    @Test
    public void whenDeleteProductNotFound_thenDeleteProduct() throws Exception {
        Mockito.doThrow(RuntimeException.class).when(productService).deleteProduct(Mockito.anyInt());

        mockMvc.perform(delete("/product/{product_id}", Mockito.anyInt()))
                .andExpect(status().isNotFound());
        reset(productService);
    }
}
