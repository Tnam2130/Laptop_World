package com.main.laptop_world.Repository;

import com.main.laptop_world.Entity.Brand;
import com.main.laptop_world.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query("SELECT c FROM Brand c WHERE c.title = :title")
    Optional<Brand> findByTitle(String title);
}
