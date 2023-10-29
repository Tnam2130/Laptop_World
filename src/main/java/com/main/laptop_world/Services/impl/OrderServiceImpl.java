package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.Order;
import com.main.laptop_world.Repository.OrderRepository;
import com.main.laptop_world.Services.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }
    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }
}
