package com.main.laptop_world.Repository;

import com.main.laptop_world.Entity.Category;
import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long>, JpaSpecificationExecutor<Products> {
    @Query("SELECT p FROM Products p WHERE p.name LIKE %:searchTerm%")
    List<Products> findBySearchTerm(@Param("searchTerm") String searchTerm);
    @Query("SELECT p FROM Products p WHERE p.name LIKE %:searchTerm% AND p.category = :category")
    List<Products> findBySearchTermAndCategory(@Param("searchTerm") String searchTerm, @Param("category") String category);

    @Query("SELECT p FROM Products p WHERE p.name LIKE %?1%")
    public List<Products> findAll(String keyword);
    @Query("SELECT c FROM Products c WHERE c.name = :name")
    Optional<Products> findByName(String name);
}
