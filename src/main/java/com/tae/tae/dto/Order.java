package com.tae.tae.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "MEMBER_PRODUCT")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(MemberProductId.class)
public class Order {

    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member; //MemberProductId.member 와 연결


    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product; //MemberProductId.product 와 연결

    private int orderAmount;
}
