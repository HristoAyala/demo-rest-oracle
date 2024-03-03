package com.test.demo.service.impl;

import com.test.demo.dao.ProductDAO;
import com.test.demo.dto.ProductDTO;
import com.test.demo.dto.ResponseDTO;
import com.test.demo.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Override
    public List<ProductDTO> getAllProducts() {
        return productDAO.listAllProducts();
    }

    @Override
    public ResponseDTO createProduct(ProductDTO product) {
        return productDAO.createProductByStoredProcedure(product);
    }


}
