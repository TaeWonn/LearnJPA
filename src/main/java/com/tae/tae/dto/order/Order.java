package com.tae.tae.dto.order;

import com.tae.tae.dto.BaseEntity;
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
@AttributeOverrides({
        @AttributeOverride(name = "createDate", column = @Column(name = "CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "UPDATE_DATE"))
})
@SqlResultSetMapping(
        name = "OrderResults",
        entities = {
                @EntityResult(entityClass = Order.class, fields = {
                        @FieldResult(name = "id", column = "order_id"),
                        @FieldResult(name = "order_amount", column = "order_amount"),
                        @FieldResult(name = "item", column = "order_item"),
                })
        },
        columns = { @ColumnResult(name = "name") }
)
@NamedEntityGraph(name = "Order.withAll", attributeNodes = {
        @NamedAttributeNode("member"),
        @NamedAttributeNode(value = "orderItems", subgraph = "orderItems")
        },
        subgraphs = @NamedSubgraph(name = "orderItems", attributeNodes = {
                @NamedAttributeNode("item")
        })
)
public class Order extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MEMBER_ID")
    private Member member; //MemberProductId.member 와 연결

    //@ManyToOne
    //@JoinColumn(name = "PRODUCT_ID")
    //private Product product; //MemberProductId.product 와 연결

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems =
            new ArrayList<OrderItem>();

    @OneToOne(cascade = CascadeType.ALL,
                fetch = FetchType.LAZY)
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

