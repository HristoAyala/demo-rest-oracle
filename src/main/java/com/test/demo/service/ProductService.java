package com.test.demo.service;

import com.test.demo.dto.ProductDTO;
import com.test.demo.dto.ResponseDTO;

import java.util.List;

public interface ProductService {

    public List<ProductDTO> getAllProducts();

    public ResponseDTO createProduct(ProductDTO product);
}
