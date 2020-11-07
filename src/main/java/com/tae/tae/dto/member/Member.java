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
//@AttributeOverrides({
//        @AttributeOverride(name = "createDate", column = @Column(name = "CREATE_DATE")),
//        @AttributeOverride(name = "updateDate", column = @Column(name = "UPDATE_DATE"))
//})
public class Member extends BaseEntity {

    @Id //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    private String id;

    @Column(name = "USERNAME")
    private String name;

    @Embedded PhoneNumber phoneNumber;

    @Embedded Address homeAddress;
    @AttributeOverrides({
            @AttributeOverride(name="city", column=@Column(name = "COMPANY_CITY")),
            @AttributeOverride(name="street", column=@Column(name = "COMPANY_STREET")),
            @AttributeOverride(name="zipcode", column=@Column(name = "COMPANY_ZIPCODE"))
    })
    Address companyAddress;


    @ManyToOne
    @JoinColumn(name="TEAM_ID") //nullable = false (이너 조인 사용)
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

    public void addTeam(Team team){

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