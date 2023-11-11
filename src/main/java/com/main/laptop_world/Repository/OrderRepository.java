package com.main.laptop_world.Repository;

import com.main.laptop_world.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    @Query("SELECT MONTH(e.createdAt) as month, YEAR(e.createdAt) as year, SUM(e.total) as totalRevenue " +
            "FROM Order e " +
            "GROUP BY MONTH(e.createdAt), YEAR(e.createdAt)")
    List<Object[]> getRevenueByMonth();
}
