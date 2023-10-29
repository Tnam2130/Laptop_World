package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.Category;

import com.main.laptop_world.Repository.CategoryRepository;
import com.main.laptop_world.Services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class CRUDCategoryController {
    CategoryService categoryService;
    CategoryRepository categoryRepository;

    public CRUDCategoryController(CategoryService categoryService,
                                  CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/admin/category")
    public String quanLyDanhMucPage(Model model) {
        List<Category> categories = categoryService.findAllCategory();
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categories);
        return "admin/QuanLyDanhMuc";
    }

    @PostMapping("/admin/category/add")
    public String addCate(@ModelAttribute Category category, String mainName, Model model, BindingResult result) {


        if (category.getMainName() == null || category.getMainName().isEmpty()) {
            List<Category> categories = categoryService.findAllCategory();
            model.addAttribute("categories", categories);
            result.rejectValue("mainName", "error.category",
                    "Không được để trống category name!");
            return "admin/QuanLyDanhMuc";
        }
        if (categoryRepository.findByName(mainName).isPresent()) {
            List<Category> categories = categoryService.findAllCategory();
            model.addAttribute("categories", categories);
            result.rejectValue("mainName", "error.category",
                    "Category name không được trùng!");
            return "admin/QuanLyDanhMuc";
        }
        categoryService.saveCategory(category);
        return "redirect:/admin/category";
    }

    @GetMapping(value = "/admin/category/delete/{id}")
    public String delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/category";
    }
}
