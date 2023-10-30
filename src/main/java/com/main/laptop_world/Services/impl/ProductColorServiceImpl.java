package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.Category;
import com.main.laptop_world.Entity.ProductColor;
import com.main.laptop_world.Repository.ProductColorRepository;
import com.main.laptop_world.Services.ProductColorService;

import java.util.Optional;

public class ProductColorServiceImpl implements ProductColorService {
    ProductColorRepository repository;
    @Override
    public ProductColor getColorById(Long id) {
        Optional<ProductColor> optional=repository.findById(id);
        ProductColor productColor = null;
        if(optional.isPresent()){
            productColor=optional.get();
        }else {
            throw new RuntimeException(" Color not found for id :: " + id);
        }
        return productColor;
    }

    @Override
    public void updateColor(ProductColor productColor) {
        productColor.setColor(productColor.getColor());

    }
}
