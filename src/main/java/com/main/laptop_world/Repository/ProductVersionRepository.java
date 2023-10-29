package com.main.laptop_world.Repository;




import com.main.laptop_world.Entity.ProductVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
 @Repository
public interface ProductVersionRepository extends JpaRepository<ProductVersion, Long> {
     @Query("SELECT c FROM ProductVersion c WHERE c.name = :name")
     Optional<ProductVersion> findByName(String name);
}
