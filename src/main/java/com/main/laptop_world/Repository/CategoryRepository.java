package com.main.laptop_world.Repository;


import com.main.laptop_world.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
