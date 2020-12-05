package com.tae.tae.respository;

import com.tae.tae.dto.order.Order;
import com.tae.tae.respository.custom.OrderRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository
        extends JpaRepository<Order, Long>,
                JpaSpecificationExecutor<Order> ,
                OrderRepositoryCustom {

    Order findOne(Long id);

    Long saveOrder(Order order);
}
