package com.tae.tae.dto.member;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class MemberProductId implements Serializable {

    private String member;  //MemberProduct.member 와 연결
    private String product; //MemberProduct.product 와 연결

    //hashCode and equals
}
