package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.ProductVersion;
import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Repository.ProductVersionRepository;
import com.main.laptop_world.Services.ProductVersionService;

import java.util.Optional;

public class ProductVerionServiceImpl implements ProductVersionService {
    ProductVersionRepository repository;
    @Override
    public ProductVersion getProductById(Long id) {
        Optional<ProductVersion> optional=repository.findById(id);
        ProductVersion productVersion = null;
        if(optional.isPresent()){
            productVersion=optional.get();
        }else {
            throw new RuntimeException(" Product not found for id :: " + id);
        }
        return productVersion;
    }
}
