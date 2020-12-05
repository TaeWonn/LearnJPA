package com.tae.tae.respository.impl;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.tae.tae.dto.member.QMember;
import com.tae.tae.dto.order.Order;
import com.tae.tae.dto.order.OrderSearch;
import com.tae.tae.dto.order.QOrder;
import com.tae.tae.respository.custom.OrderRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderRepositoryImpl
                    extends QuerydslRepositorySupport
                    implements OrderRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    public OrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> search(OrderSearch orderSearch) {

        QOrder order = QOrder.order;
        QMember member = QMember.member;

        JPQLQuery query = from(order);

        if(StringUtils.hasText(orderSearch.getMemberName())) {
            query.leftJoin(order.member, member)
                    .where(member.name.contains(orderSearch.getMemberName()));
        }

        if(orderSearch.getOrderStatus() != null) {
            query.where(order.status.eq(orderSearch.getOrderStatus()));
        }

        return query.fetch();
    }
    @Override
    public Order findOne(Long id) {
        Order order = em.find(Order.class,id);
        return order;
    }

    @Override
    public Long saveOrder(Order order) {
        em.persist(order);
        return order.getId();
    }

    @Override
    public Order findOrder(Long id) {
        JPQLQuery<Order> query = from(QOrder.order);

        query.where(QOrder.order.id.eq(id));
        return query.fetchOne();
    }
}
