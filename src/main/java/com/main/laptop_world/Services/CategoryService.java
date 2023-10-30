package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.Category;
import com.main.laptop_world.Entity.User;


import java.util.List;

public interface CategoryService {
    List<Category> findAllCategory();

    public Category getCategoryById(Long id);
    void saveCategory(Category category);
    void deleteCategory(Long id);
    Category findBymainName(String mainName);

    void updateCategory(Category category);
}
