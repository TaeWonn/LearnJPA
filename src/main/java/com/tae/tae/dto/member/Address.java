package com.tae.tae.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String street;
    private String city;
    private String state;
    private String zipcode;
//    @Embedded Zipcode zipcode; //임베디드 타입 포함

    public Address(String city){ this.city = city;}
    public Address(String city, String street, String zipcode){
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
    }
}
