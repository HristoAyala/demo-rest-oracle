package com.test.demo.dao.impl;

import com.test.demo.dao.ProductDAO;
import com.test.demo.dao.entity.AuditEntity;
import com.test.demo.dao.entity.ProductEntity;
import com.test.demo.dao.entity.Response;
import com.test.demo.dao.repository.AuditRepository;
import com.test.demo.dao.repository.ProductRepository;
import com.test.demo.dto.ProductDTO;
import com.test.demo.dto.ResponseDTO;
import com.test.demo.mapper.ProductEntityMapper;
import com.test.demo.mapper.ResponseMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {

    private ProductEntityMapper productMapper = Mappers.getMapper(ProductEntityMapper.class);

    private ResponseMapper responseMapper = Mappers.getMapper(ResponseMapper.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AuditRepository auditRepository;

    @Override
    public List<ProductDTO> listAllProducts() {
        return productMapper.convertListEntityToDTO(productRepository.findAll());
    }

    @Override
    public ResponseDTO createProductByStoredProcedure(ProductDTO product) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("sp_ejemplo");

        storedProcedure.registerStoredProcedureParameter("sp_id", Integer.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("sp_nombre", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("sp_fec_registro", Date.class, ParameterMode.IN);

        storedProcedure.registerStoredProcedureParameter("sp_cursor", void.class, ParameterMode.REF_CURSOR);
        storedProcedure.registerStoredProcedureParameter("sp_codigo", Integer.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("sp_mensaje", String.class, ParameterMode.OUT);

        storedProcedure.setParameter("sp_id", product.getId());
        storedProcedure.setParameter("sp_nombre", product.getName());
        storedProcedure.setParameter("sp_fec_registro", product.getDate_register());

        storedProcedure.execute();

        int code = (Integer) storedProcedure.getOutputParameterValue("sp_codigo");
        String message = (String) storedProcedure.getOutputParameterValue("sp_mensaje");
        List<Object[]> results;

        results = storedProcedure.getResultList();

        if (code == 0) {
            AuditEntity audit = generateAudit(message + " - ID:" + product.getId());
            auditRepository.save(audit);
        }

        Response responseSp = new Response();
        responseSp.setCode(code);
        responseSp.setMessage(message);
        responseSp.setProducts(getSpProducts(results));

        return responseMapper.convertClassToDTO(responseSp);
    }

    protected List<ProductEntity> getSpProducts(List<Object[]> results) {
        if (results != null || results.isEmpty()) {
            List<ProductEntity> products = new ArrayList<>();

            for (Object[] result : results) {
                ProductEntity newProduct = new ProductEntity();
                newProduct.setId(((BigDecimal) result[0]).intValue());
                newProduct.setName((String) result[1]);

                Timestamp timestampValue = (Timestamp) result[2];
                LocalDateTime localDateTime = timestampValue.toLocalDateTime();
                java.sql.Date dateValue = java.sql.Date.valueOf(localDateTime.toLocalDate());
                newProduct.setDate_register(dateValue);
                products.add(newProduct);
            }
            return products;
        }
        return Collections.emptyList();
    }

    protected static AuditEntity generateAudit(String opertion) {
        AuditEntity audit = new AuditEntity();
        audit.setDateAudit(LocalDateTime.now());
        audit.setOperation(opertion);
        return audit;
    }
}


