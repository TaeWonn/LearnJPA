package com.tae.tae.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "PRODUCT")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id @Column(name = "PRODUCT_ID")
    private String id;

    private String name;
}
