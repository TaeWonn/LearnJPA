package com.tae.tae.dto.order;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import static org.springframework.data.jpa.domain.Specification.where;
import static com.tae.tae.respository.spec.OrderSpec.memberNameLike;
import static com.tae.tae.respository.spec.OrderSpec.orderStatusEq;

@Getter @Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;

    public Specification<Order> toSpecification() {
        return where(memberNameLike(memberName))
                .and(orderStatusEq(orderStatus));
    }
}
