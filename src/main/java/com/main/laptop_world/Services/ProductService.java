package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    List<Products> findAllProduct();

    public Products getProductById(Long id);
    void saveProduct(Products product);
    void deleteProduct(Products products);
    List<Products> productFilter(Long categoryId, String priceSort);


}

