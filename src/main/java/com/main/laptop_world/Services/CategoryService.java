package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.Category;
import com.main.laptop_world.Entity.Products;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategory();
    public Category getCategoryById(Long id);
    void saveCategory(Category category);
}
