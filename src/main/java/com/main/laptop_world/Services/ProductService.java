package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Entity.Specifications;
import com.main.laptop_world.Repository.ProductRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    List<Products> findAllProduct();
    List<Products> findAllProduct(Specification<Products> spec);
    public Products getProductById(Long id);
    void saveProduct(Products product);
    void deleteProduct(Products products);
    List<Products> productFilter(Long categoryId, String priceSort);
    List<Products> findByKeyword(String keyword);
    List<Products> searchProducts(String searchTerm, String category);


}

