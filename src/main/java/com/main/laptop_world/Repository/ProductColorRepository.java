package com.main.laptop_world.Repository;


import com.main.laptop_world.Entity.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductColorRepository extends JpaRepository<ProductColor, Long> {
}
