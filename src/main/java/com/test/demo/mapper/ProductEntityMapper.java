package com.test.demo.mapper;

import com.test.demo.dao.entity.ProductEntity;
import com.test.demo.dto.ProductDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProductEntityMapper {

    ProductEntity convertDTOToEntity(ProductDTO product);

    ProductDTO convertEntityToDTO(ProductEntity product);

    List<ProductEntity> convertListDTOToEntity(List<ProductDTO> list);

    List<ProductDTO> convertListEntityToDTO(List<ProductEntity> list);
}
