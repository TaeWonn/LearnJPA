package com.tae.tae.respository.custom;

import com.tae.tae.dto.order.Order;
import com.tae.tae.dto.order.OrderSearch;

import java.util.List;


public interface OrderRepositoryCustom {

    public List<Order> search(OrderSearch orderSearch);

    public Order findOne(Long id);

    public Long save(Order order);
}
