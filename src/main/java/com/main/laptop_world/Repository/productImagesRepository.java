package com.main.laptop_world.Repository;


import com.main.laptop_world.Entity.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface productImagesRepository extends JpaRepository<ProductImages, Long> {
}
