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
    public String addDesc(@ModelAttribute("description") Description description,
                             String title, Model model, BindingResult result, RedirectAttributes ra) {
//        if (description.getTitle() == null || description.getTitle().isEmpty()) {
//            List<Description> descriptions = descriptionRepository.findAll();
//            model.addAttribute("descriptions", descriptions);
//            result.rejectValue("title", "error.descriptions",
//                    "Không được để trống title name!");
//            return "admin/QuanLyDesc";
//        }
//        if (descriptionRepository.findByName(title).isPresent()) {
//            List<Description> descriptions = descriptionRepository.findAll();
//            model.addAttribute("descriptions", descriptions);
//            result.rejectValue("title", "error.descriptions",
//                    "title name không được trùng!");
//            return "admin/QuanLyDesc";
//        }
        ra.addFlashAttribute("message", "Save successfully");
        descriptionRepository.save(description);
        return "redirect:/admin/desc";
    }

    @GetMapping(value = "/admin/desc/delete/{id}")
    public String deleteDesc(@PathVariable Long id, RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Delete successfully");
        descriptionRepository.deleteById(id);
        return "redirect:/admin/desc";
    }

    @GetMapping("/admin/desc/update/id={id}")
    public String getUpdateSpec(@PathVariable("id") Long id, Model model, RedirectAttributes ra, @ModelAttribute Specifications specs) {
        Description descriptions = descriptionRepository.getById(id);
        model.addAttribute("descriptions", descriptions);
        List<Products> productList = productService.findAllProduct();
        model.addAttribute("productList", productList);
        return "admin/Update/updateDesc";
    }

    @PostMapping("/admin/desc/update")
    public String updateSpec(Description description, RedirectAttributes ra,
                             @RequestParam("product") Long productId) {
        Products products = productService.getProductById(productId);
        ra.addFlashAttribute("message", "Update successfully");
        description.setProducts(products);
        descriptionRepository.save(description);
        return "redirect:/admin/desc";
    }
}
