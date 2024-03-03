package com.test.demo.controller;

import com.test.demo.dto.ProductDTO;
import com.test.demo.dto.ResponseDTO;
import com.test.demo.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public List<ProductDTO> listProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/products")
    public ResponseEntity<ResponseDTO> createProduct(@RequestBody ProductDTO product) {
        ResponseDTO response = productService.createProduct(product);

        if (response.getCode() != 0 || response.getProducts() == null) {
            log.error("Error al crear producto!: " + response.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } else {
            log.warn(response.getMessage());
            return ResponseEntity.ok(response);
        }
    }
}
