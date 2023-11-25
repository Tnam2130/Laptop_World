package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.Description;
import com.main.laptop_world.Entity.ProductVersion;
import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Entity.Specifications;
import com.main.laptop_world.Repository.DescriptionRepository;
import com.main.laptop_world.Services.DescriptionService;
import com.main.laptop_world.Services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CRUDDescriptionController {
    ProductService productService;
    DescriptionService descriptionService;
    DescriptionRepository descriptionRepository;
    public CRUDDescriptionController(ProductService productService,
                                     DescriptionService descriptionService,
                                     DescriptionRepository descriptionRepository
                                     ){
        this.descriptionRepository = descriptionRepository;
        this.descriptionService = descriptionService;
        this.productService = productService;
    }

    @GetMapping("/admin/desc")
    public String getDescForm(Model model){
        List<Products> productList = productService.findAllProduct();
        List<Description> descriptions = descriptionRepository.findAll();
        model.addAttribute("descriptions", descriptions);
        model.addAttribute("productList", productList);
        model.addAttribute("description", new Description());
        return "admin/QuanLyDesc";
    }

    @PostMapping("/admin/desc/add")
    public String addDesc(@ModelAttribute("description") Description description, RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Save successfully");
        descriptionRepository.save(description);
        return "redirect:/admin/desc";
    }

    @GetMapping("/admin/desc/update/id={id}")
    public String getUpdateSpec(@PathVariable("id") Long id, Model model, RedirectAttributes ra, @ModelAttribute Specifications specs) {
        Description descriptions = descriptionService.getById(id);
        model.addAttribute("descriptions", descriptions);
        List<Products> productList = productService.findAllProduct();
        if (productList.isEmpty()){
            System.out.println("null");
        }
        model.addAttribute("productList", productList);
        return "admin/Update/updateDesc";
    }

    @PostMapping("/admin/desc/update")
    public String updateSpec(Description descriptions, RedirectAttributes ra,
                             @RequestParam("product") Long productId) {
        Products products = productService.getProductById(productId);
        System.out.println("Hi");
        descriptions.setProducts(products);
        descriptionService.updateDesc(descriptions);
        ra.addFlashAttribute("message", "Update successfully");
        return "redirect:/admin/desc";
    }
    @GetMapping(value = "/admin/desc/delete/{id}")
    public String deleteDesc(@PathVariable Long id, RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Delete successfully");
        descriptionRepository.deleteById(id);
        return "redirect:/admin/desc";
    }
}
