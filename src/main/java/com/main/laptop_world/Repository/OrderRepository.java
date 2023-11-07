package com.main.laptop_world.Repository;

import com.main.laptop_world.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    @Query("SELECT new Order (MONTH(d.createdAt), SUM(d.total)) FROM Order d GROUP BY MONTH(d.createdAt)")
    List<Order> tinhDoanhThuTheoThang();
}
