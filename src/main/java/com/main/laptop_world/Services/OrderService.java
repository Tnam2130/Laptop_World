package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.Cart;
import com.main.laptop_world.Entity.Order;
import com.main.laptop_world.Entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Order saveOrder(Order order);
    List<Order> findAll();
    Order findOrderById(Long orderId);
    List<Order> findByUserId(Long userId);

    void updateOrder(Long id, String newStatus);

    void updateOrders(Order order);
}
