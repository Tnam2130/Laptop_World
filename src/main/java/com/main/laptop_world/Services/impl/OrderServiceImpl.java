package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.Cart;
import com.main.laptop_world.Entity.DTO.OrderDTO;
import com.main.laptop_world.Entity.Order;
import com.main.laptop_world.Entity.OrderItem;
import com.main.laptop_world.Repository.OrderItemRepository;
import com.main.laptop_world.Repository.OrderRepository;
import com.main.laptop_world.Services.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository){
        this.orderRepository=orderRepository;
        this.orderItemRepository=orderItemRepository;
    }
    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public void updateOrder(Long id, String newStatus) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null){
            order.setStatus(newStatus);
            orderRepository.save(order);
        }
    }

    @Override
    public void updateOrders(Order order) {
        order.setStatus(order.getStatus());
        orderRepository.save(order);
    }

    @Override
    public List<OrderDTO> getRevenueData() {
        List<Object[]> revenueList = orderRepository.getRevenueByMonth();
        return revenueList.stream()
                .map(data -> new OrderDTO((int) data[0], (int) data[1], (BigDecimal) data[2]))
                .collect(Collectors.toList());
    }
}
