package com.main.laptop_world.Repository;

import com.main.laptop_world.Entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payments, Long> {
    Payments findByOrderId(Long orderId);
}
