package com.main.laptop_world.Repository;


import com.main.laptop_world.Entity.ProductVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVersionRepository extends JpaRepository<ProductVersion, Long> {
}
