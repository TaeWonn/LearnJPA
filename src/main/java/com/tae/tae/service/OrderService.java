package com.tae.tae.service;

import com.tae.tae.dto.order.Order;
import com.tae.tae.respository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order findOrder(Long id) {
        return orderRepository.findOrder(id);
    }
}
