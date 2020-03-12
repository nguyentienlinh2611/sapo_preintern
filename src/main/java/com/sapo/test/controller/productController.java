package com.sapo.test.controller;


import com.sapo.test.model.ProductModel;
import com.sapo.test.model.ResponseModel;
import com.sapo.test.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/product")
public class productController {

    @Autowired
    ProductService productService;

    @PostMapping("/test")
    public String test() {
        return "Hello";
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductModel newProduct) {
        ResponseModel responseModel = productService.createProduct(newProduct);
        return new ResponseEntity<>((String) responseModel.getData(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getProducts() {
        ResponseModel responseModel = productService.getProducts();
        return new ResponseEntity<>(responseModel.getData(), HttpStatus.OK);
    }

    @GetMapping(path="/{product_id}")
    public ResponseEntity<?> getProducts( @PathVariable("product_id") Integer productId) {
        ResponseModel responseModel = productService.getProducts(productId);
        return new ResponseEntity(responseModel.getData(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody @Valid ProductModel updatedProduct) {
        ResponseModel responseModel = productService.updateProduct(updatedProduct);
        return new ResponseEntity(responseModel.getData(), HttpStatus.OK);
    }

    @DeleteMapping(path="/{product_id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("product_id") Integer productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
