package com.tae.tae.dto.order;

import com.tae.tae.dto.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
//@IdClass(MemberProductId.class)
public class Order {

    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member; //MemberProductId.member 와 연결

    //@ManyToOne
    //@JoinColumn(name = "PRODUCT_ID")
    //private Product product; //MemberProductId.product 와 연결

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems =
            new ArrayList<OrderItem>();

    @OneToOne
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private int orderAmount;

    public void setMember(Member member) {
        //기존 관계 제거
        if(this.member != null)
            this.member.getOrders().remove(this);
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}

enum OrderStatus {
    ORDER, CANCEL
}

