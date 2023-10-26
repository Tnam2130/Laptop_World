package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.Category;
import com.main.laptop_world.Repository.CategoryRepository;
import com.main.laptop_world.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CateService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productsRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id){
        return categoryRepository.findById(id).orElse(null);
    }
}
