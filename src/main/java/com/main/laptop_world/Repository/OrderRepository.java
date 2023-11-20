package com.main.laptop_world.Repository;

import com.main.laptop_world.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    @Query("SELECT MONTH(e.createdAt) as month, YEAR(e.createdAt) as year, SUM(e.total) as totalRevenue " +
            "FROM Order e " +
            "WHERE e.status = 'Delivered' " +
            "GROUP BY MONTH(e.createdAt), YEAR(e.createdAt)")
    List<Object[]> getRevenueByMonth();

    @Query("SELECT c FROM Order c WHERE c.status = :status")
    Optional<Order> findByName(String status);
}
