package com.main.laptop_world.Repository;

import com.main.laptop_world.Entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findByProducts_Id(Long productId);
}
