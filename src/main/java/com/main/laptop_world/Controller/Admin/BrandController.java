package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.Brand;
import com.main.laptop_world.Entity.Category;
import com.main.laptop_world.Repository.BrandRepository;
import com.main.laptop_world.Services.BrandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BrandController {
    BrandRepository brandRepository;
    BrandService brandService;
    public BrandController(BrandService brandService,
                           BrandRepository brandRepository){
        this.brandRepository = brandRepository;
        this.brandService = brandService;
    }

    @GetMapping("/admin/brand")
    public String quanLyBrandPage(Model model) {
        List<Brand> brands = brandService.findAllBrand();
        model.addAttribute("brand", new Brand());
        model.addAttribute("brands", brands);
        return "admin/QuanLyBrand";
    }

    @GetMapping("/admin/brand/add")
    public String addCategory(@ModelAttribute("brand") Brand brand, Model model, BindingResult result, String title, RedirectAttributes ra) {
        if (brand.getTitle() == null || brand.getTitle().isEmpty()) {
            List<Brand> brands = brandService.findAllBrand();
            model.addAttribute("brands", brands);
            result.rejectValue("title", "error.brand",
                    "Không được để trống Title name!");
            return "admin/QuanLyBrand";
        }
        if (brandRepository.findByTitle(title).isPresent()) {
            List<Brand> brands = brandService.findAllBrand();
            model.addAttribute("brands", brands);
            result.rejectValue("title", "error.brand",
                    "Title name không được trùng!");
            return "admin/QuanLyBrand";
        }
        ra.addFlashAttribute("message", "Save successfully");
        brandRepository.save(brand);
        return "redirect:/admin/brand";
    }

    @GetMapping("/admin/brand/update/id={id}")
    public String getUpdateCategory(@PathVariable("id") Long id, Model model, RedirectAttributes ra, @ModelAttribute Brand brand) {
        Brand brands = brandService.getBrandById(id);
        model.addAttribute("brands",brands);
        return "admin/Update/updateBrand";
    }
    @PostMapping("/admin/brand/update")
    public String updateCategory(Brand brand, RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Update successfully");
        brandService.updateBrand(brand);
        return "redirect:/admin/brand";
    }
    @GetMapping(value = "/admin/brand/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Delete successfully");
        brandService.deleteBrand(id);
        return "redirect:/admin/brand";
    }
}
