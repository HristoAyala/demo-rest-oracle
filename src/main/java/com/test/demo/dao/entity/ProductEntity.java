package com.test.demo.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "producto")
@Data
public class ProductEntity {

    @Id
    private int id;

    @Column(name = "nombre")
    private String name;

    @Column(name = "fec_registro")
    private Date date_register;
}
