package com.tae.tae.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {

    String street;
    String city;
    String state;
    @Embedded Zipcode zipcode; //임베디드 타입 포함

    public Address(String city){ this.city = city;}
}
