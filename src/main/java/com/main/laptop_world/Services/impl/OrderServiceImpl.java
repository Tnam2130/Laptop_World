package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.*;
import com.main.laptop_world.Entity.DTO.OrderDTO;
import com.main.laptop_world.Repository.OrderItemRepository;
import com.main.laptop_world.Repository.OrderRepository;
import com.main.laptop_world.Services.OrderService;
import com.main.laptop_world.Services.PaymentService;
import com.main.laptop_world.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    UserService userService;
    PaymentService paymentService;

    @Autowired
    @Lazy
    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserService userService, PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userService = userService;
        this.paymentService = paymentService;
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
        if (order != null) {
            order.setStatus(newStatus);
            orderRepository.save(order);
        }
    }

    @Override
    public void updateOrderWithPayment(Long orderId, Long userId) {
        Order order = findOrderById(orderId);
        User currentUser = userService.findById(userId);
        if (order != null) {
            Payments payments = new Payments();
            payments.setOrder(order);
            payments.setUser(currentUser);
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmm");
            String formattedDate = dateFormat.format(new Date());
            payments.setTradingCode("HD" + formattedDate);
            payments.setMode("CASH");
            payments.setCreatedAt(new Date());
            payments.setStatus(false);
            paymentService.savePayment(payments);
        }
        saveOrder(order);
    }

    @Override
    public List<OrderDTO> getRevenueData() {
        List<Object[]> revenueList = orderRepository.getRevenueByMonth();
        return revenueList.stream()
                .map(data -> new OrderDTO((int) data[0], (int) data[1], (BigDecimal) data[2]))
                .collect(Collectors.toList());
    }
}
