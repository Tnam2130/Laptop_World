package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.Category;

import com.main.laptop_world.Repository.CategoryRepository;
import com.main.laptop_world.Services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/admin/category/add")
    public String addCategory(@ModelAttribute("category") Category category, Model model) {
//        model.addAttribute("category", category);
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }

//    @PostMapping("/admin/category/save")
//    public String saveUser(Category category, RedirectAttributes ra, String mainName, Model model, BindingResult result) {
//        if (category.getMainName() == null || category.getMainName().isEmpty()) {
//            List<Category> categories = categoryService.findAllCategory();
//            model.addAttribute("categories", categories);
//            result.rejectValue("mainName", "error.category",
//                    "Không được để trống category name!");
//            return "admin/CRUDCategory/saveCategory";
//        }
//        if (categoryRepository.findByName(mainName).isPresent()) {
//            List<Category> categories = categoryService.findAllCategory();
//            model.addAttribute("categories", categories);
//            result.rejectValue("mainName", "error.category",
//                    "Category name không được trùng!");
//            return "admin/saveCategory";
//        } else
//            categoryService.saveCategory(category);
//        ra.addFlashAttribute("message", "Save successfully");
//        return "redirect:/admin/category";
//    }
    @GetMapping("/admin/category/update/id={id}")
    public String getUpdateCategory(@PathVariable("id") Long id, Model model,RedirectAttributes ra, @ModelAttribute Category category) {
        Category categories = categoryService.getCategoryById(id);
        model.addAttribute("categories",categories);
        return "admin/CRUDupdate/updateCategory";
    }
    @PostMapping("/admin/category/update")
    public String updateCategory(Category category) {
        categoryService.updateCategory(category);
        return "redirect:/admin/category";
    }
    @GetMapping(value = "/admin/category/delete/{id}")
    public String delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/category";
    }
}
