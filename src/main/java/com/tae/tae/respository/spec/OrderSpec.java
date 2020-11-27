package com.tae.tae.respository.spec;

import com.tae.tae.dto.member.Member;
import com.tae.tae.dto.order.Order;
import com.tae.tae.dto.order.OrderStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;

public interface OrderSpec {

    public static Specification<Order> memberName(final String memberName) {
        return  new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                if(StringUtils.isEmpty(memberName)) return null;

                Join<Order, Member> m = root.join("member", JoinType.INNER); // 회원과 조인

                return criteriaBuilder.equal(m.get("name"), memberName);
            }
        };
    }

    public static Specification<Order> isOrderStatus() {
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                return criteriaBuilder.equal(root.get("status"), OrderStatus.ORDER);
            }
        };
    }
}
