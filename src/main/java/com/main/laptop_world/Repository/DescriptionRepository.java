package com.main.laptop_world.Repository;

import com.main.laptop_world.Entity.Description;
import com.main.laptop_world.Entity.ProductVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DescriptionRepository extends JpaRepository<Description, Long> {
    @Query("SELECT c FROM Description c WHERE c.title = :title")
    Optional<ProductVersion> findByName(String title);
}
