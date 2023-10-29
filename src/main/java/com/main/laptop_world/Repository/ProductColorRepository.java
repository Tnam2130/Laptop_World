package com.main.laptop_world.Repository;

import com.main.laptop_world.Entity.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductColorRepository extends JpaRepository<ProductColor, Long> {
    @Query("SELECT c FROM ProductColor c WHERE c.color = :color")
    Optional<ProductColor> findByName(String color);
}
