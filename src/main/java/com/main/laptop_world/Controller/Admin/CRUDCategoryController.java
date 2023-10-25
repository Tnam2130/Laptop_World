package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.Category;

import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Repository.CategoryRepository;
import com.main.laptop_world.Repository.ProductRepository;
import com.main.laptop_world.Services.CategoryService;
import com.main.laptop_world.Services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class CRUDCategoryController {
    CategoryRepository categoryRepository;
    CategoryService categoryService;
    public CRUDCategoryController(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;}
    @GetMapping("/admin/category")
    public String quanLyDanhMucPage(Model model) {
            List<Category> category = categoryRepository.findAll();
            model.addAttribute("category", category);
        return "admin/quanLyDanhMuc";
    }
//    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
//    public String delete(@PathVariable Long id) {
//        categoryRepository.deleteById(id);
//        return "redirect:/admin/quanLyTaiKhoan";
//    }
//    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
//    public String edit(@PathVariable Long id, Model model) {
//        Category category = categoryRepository.findById(id).orElseThrow();
//        model.addAttribute("category", category);
//
//        return "/admin/quanLyTaiKhoan";
//    }
//
//    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
//    public String update(@PathVariable Long id, @ModelAttribute Category category) {
//        categoryRepository.save(category);
//        return "redirect:/admin/quanLyTaiKhoan";
//    }
//
@GetMapping(value ="/admin/category/add")
public String addCate(Model model) {
    Category category = new Category();
    model.addAttribute("category", category);
    return "/admin/quanLyDanhMuc";
}

    @PostMapping(value ="/admin/category/add")
    public String saveCate(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/admin/category";

    }

    @GetMapping(value = "/admin/category/delete/{id}")
    public String delete(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/category";
    }
}
