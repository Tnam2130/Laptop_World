package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.Category;
import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Repository.CategoryRepository;
import com.main.laptop_world.Services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }
    @Override
    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        Optional<Category> optional=categoryRepository.findById(id);
        Category category = null;
        if(optional.isPresent()){
            category=optional.get();
        }else {
            throw new RuntimeException(" Product not found for id :: " + id);
        }
        return category;
    }

    @Override
    public void saveCategory(Category category) {
        if(category != null){
            this.categoryRepository.save(category);
        }else{
            System.out.println("category is null!!!");
        }
    }
}
