package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.DTO.OrderDTO;
import com.main.laptop_world.Entity.Order;

import java.text.ParseException;
import java.util.List;

public interface OrderService {
    Order saveOrder(Order order);
    List<Order> findAll();
    Order findOrderById(Long orderId);
    void deleteOrderById(Long orderId);
    List<Order> findByUserId(Long userId);
    void updateOrder(Long id, String newStatus);
    void updateOrderWithPayment(Long orderId, Long userId) throws ParseException;
    List<OrderDTO> getRevenueData();
}
