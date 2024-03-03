package com.test.demo.dao;

import com.test.demo.dto.ProductDTO;
import com.test.demo.dto.ResponseDTO;

import java.util.List;

public interface ProductDAO {

    public List<ProductDTO> listAllProducts();

    public ResponseDTO createProductByStoredProcedure(ProductDTO productDTO);
}
