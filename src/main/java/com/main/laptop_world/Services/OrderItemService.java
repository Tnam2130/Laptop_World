package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.Order;
import com.main.laptop_world.Entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItemService {
     void save(OrderItem orderItem);
     List<OrderItem> getOrderByOrderItem(Order order);
     BigDecimal calculateTotalPriceForUser(Long orderId, Long userId);

}
