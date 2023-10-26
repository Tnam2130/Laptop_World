package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.Category;

import com.main.laptop_world.Repository.CategoryRepository;
import com.main.laptop_world.Services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class CRUDCategoryController {
    CategoryRepository categoryRepository;
    CategoryService categoryService;

    public CRUDCategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/admin/category")
    public String quanLyDanhMucPage(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "admin/QuanLyDanhMuc";
    }

    @PostMapping("/admin/category/add")
    public String addCate(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping(value = "/admin/category/delete/{id}")
    public String delete(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/category";
    }
}
