package com.main.laptop_world.Repository;


import com.main.laptop_world.Entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findBymainName(String mainName);

    @Query("SELECT c FROM Category c WHERE c.mainName = :mainName")
    Optional<Category> findByName(String mainName);}
