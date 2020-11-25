package com.tae.tae.dto.order;

import com.tae.tae.dto.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "PRODUCT")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id @Column(name = "PRODUCT_ID")
    private String id;

    private String name;

//    @ManyToMany(mappedBy = "products") //역방향 추가
//    private List<Member> members;
}
