package com.test.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDTO {

    private int code;
    private String message;
    private List<ProductDTO> products;
}
