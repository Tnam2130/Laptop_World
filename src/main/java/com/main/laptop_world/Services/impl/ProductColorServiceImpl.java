package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.Category;
import com.main.laptop_world.Entity.ProductColor;
import com.main.laptop_world.Repository.ProductColorRepository;
import com.main.laptop_world.Services.ProductColorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class ProductColorServiceImpl implements ProductColorService {
    ProductColorRepository repository;

    public ProductColorServiceImpl(ProductColorRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductColor> findAllColor() {
        return repository.findAll();
    }

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
    public void saveColor(ProductColor color) {
        repository.save(color);
    }

    @Override
    public void updateColor(ProductColor productColor) {
        productColor.setColor(productColor.getColor());

    }

    @Override
    public ProductColor findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteColorById(Long colorId) {
        repository.deleteById(colorId);
    }
}
