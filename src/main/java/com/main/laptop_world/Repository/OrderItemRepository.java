package com.main.laptop_world.Repository;

import com.main.laptop_world.Entity.Order;
import com.main.laptop_world.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
    List<OrderItem> findByOrderIdAndOrder_UserId(Long orderId, Long userId);
}
