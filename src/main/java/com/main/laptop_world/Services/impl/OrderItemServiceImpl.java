package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.OrderItem;
import com.main.laptop_world.Repository.OrderItemRepository;
import com.main.laptop_world.Services.OrderItemService;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    OrderItemRepository orderItemRepository;
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository){
        this.orderItemRepository=orderItemRepository;
    }
    @Override
    public void save(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }
}
