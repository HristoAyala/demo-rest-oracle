package com.test.demo.dao.entity;

import lombok.Data;

import java.util.List;

@Data
public class Response {

    private int code;
    private String message;
    private List<ProductEntity> products;
}
