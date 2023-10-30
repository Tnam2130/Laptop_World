package com.main.laptop_world.Services;


import com.main.laptop_world.Entity.ProductColor;

public interface ProductColorService {
    public ProductColor getColorById(Long id);
    void updateColor(ProductColor productColor);
}
