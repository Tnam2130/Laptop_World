package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.ProductColor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductColorService {
    List<ProductColor> findAllColor();
    ProductColor getColorById(Long id);
    void saveColor(ProductColor color);
    void updateColor(ProductColor productColor);
    ProductColor findById(Long id);
    void deleteColorById(Long colorId);
}
