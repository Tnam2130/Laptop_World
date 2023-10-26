package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Repository.ProductRepository;
import com.main.laptop_world.Services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    public ProductServiceImpl(ProductRepository repository){
        this.repository=repository;
    }

    @Override
    public List<Products> findAllProduct() {
        return repository.findAll();
    }

    @Override
    public Products getProductById(Long id) {
        Optional<Products> optional=repository.findById(id);
        Products product = null;
        if(optional.isPresent()){
            product=optional.get();
        }else {
            throw new RuntimeException(" Product not found for id :: " + id);
        }
        return product;
    }

    @Override
    public void saveProduct(Products product) {
        this.repository.save(product);
    }

    @Override
    public void deleteProduct(Products products) {
        this.repository.delete(products);
    }
}
