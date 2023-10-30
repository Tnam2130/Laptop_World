package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.Order;
import com.main.laptop_world.Entity.OrderItem;
import com.main.laptop_world.Repository.OrderItemRepository;
import com.main.laptop_world.Services.OrderItemService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

    @Override
    public List<OrderItem> getOrderByOrderItem(Order order) {
        return orderItemRepository.findByOrder(order);
    }

    @Override
    public BigDecimal calculateTotalPriceForUser(Long orderId, Long userId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderIdAndOrder_UserId(orderId,userId);
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem orderItem : orderItems) {
            total = total.add(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        }
        return total;
    }


}
