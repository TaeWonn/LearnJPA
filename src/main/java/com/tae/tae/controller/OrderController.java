package com.tae.tae.controller;

import com.tae.tae.dto.order.Order;
import com.tae.tae.respository.OrderRepository;
import com.tae.tae.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @PostMapping("/order")
    public ResponseEntity orderRequest(Order order) {
        Long id = orderService.postOrder(order);// 상품구매

        //리포지토리 계층
        Order orderResult = orderRepository.findOne(id);
        return new ResponseEntity(orderResult, HttpStatus.OK);
    }

}
