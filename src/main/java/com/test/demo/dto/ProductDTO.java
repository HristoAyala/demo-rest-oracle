package com.test.demo.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class ProductDTO {

    private int id;
    private String name;
    private Date date_register;
}
