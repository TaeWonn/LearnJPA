package com.tae.tae.dto.member;

import com.tae.tae.dto.BaseEntity;
import com.tae.tae.dto.order.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MEMBER")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "MEMBER_ID")),
        @AttributeOverride(name = "name", column = @Column(name = "MEMBER_NAME"))
})
public class Member extends BaseEntity {

    private String city;
    private String street;
    private String zipcode;

    @ManyToOne
    @JoinColumn(name="TEAM_ID")
    private Team team;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

//    @ManyToMany
//    @JoinTable(name = "MEMBER_PRODUCT",
//                joinColumns = @JoinColumn(name = "MEMBER_ID"),
//                inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
//    private List<Product> products = new ArrayList<Product>();

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<Order>();

    public void setTeam(Team team){

        //기존 팀과 관계를 제거
        if(this.team != null) this.team.getMembers().remove(this);

        this.team = team;
        team.getMembers().add(this);
    }

//    public void addProduct(Product product){
//
//        products.add(product);
//        product.getMembers().add(this);
//    }
}

@Entity
@Table(name = "LOCKER")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
class Locker{

    @Id @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    @Column(name = "LOCKER_NAME")
    private String name;

    @OneToOne(mappedBy = "locker")
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}