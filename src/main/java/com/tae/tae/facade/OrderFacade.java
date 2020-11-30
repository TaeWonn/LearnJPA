package com.tae.tae.facade;

import com.tae.tae.dto.order.Order;
import com.tae.tae.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderFacade {

    @Autowired
    private OrderService orderService;

    public Order findOrder(Long id) {
        Order order = orderService.findOrder(id);
        order.getMember().getName();
        return  order;
    }
}
