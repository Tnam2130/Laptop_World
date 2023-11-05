package com.main.laptop_world.Controller.Admin;


import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Entity.Specifications;
import com.main.laptop_world.Repository.SpecificationRepository;
import com.main.laptop_world.Services.ProductService;
import com.main.laptop_world.Services.SpecificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class SpecificationsController {
    SpecificationRepository specificationRepository;
    SpecificationService specificationService;
    ProductService productService;
    public SpecificationsController(SpecificationRepository specificationRepository,
                                    SpecificationService specificationService,
                                    ProductService productService){
        this.specificationService = specificationService;
        this.specificationRepository = specificationRepository;
        this.productService = productService;
    }

    @GetMapping("/admin/spec")
    public String quanLySpecPage(Model model) {
        List<Specifications> spec = specificationRepository.findAll();
        model.addAttribute("specs", new Specifications());
        model.addAttribute("spec", spec);
        List<Products> productList = productService.findAllProduct();
        model.addAttribute("productList", productList);
        model.addAttribute("title","Quản lý Thông số");
        return "admin/QuanLySpec";
    }

    @GetMapping("/admin/spec/add")
    public String addSpec(@ModelAttribute("specs") Specifications specifications,RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Save successfully");
        specificationRepository.save(specifications);
        return "redirect:/admin/spec";
    }

    @GetMapping("/admin/spec/update/id={id}")
    public String getUpdateSpec(@PathVariable("id") Long id, Model model, RedirectAttributes ra, @ModelAttribute Specifications specs) {
        Specifications specifications = specificationRepository.getById(id);
        model.addAttribute("spec", specifications);
        List<Products> productList = productService.findAllProduct();
        model.addAttribute("productList", productList);
        return "admin/Update/updateSpec";
    }

    @PostMapping("/admin/spec/update")
    public String updateSpec(Specifications specifications, RedirectAttributes ra,
                             @RequestParam("product") Long productId) {
        Products products = productService.getProductById(productId);
        ra.addFlashAttribute("message", "Update successfully");
        specifications.setProducts(products);
        specificationRepository.save(specifications);
        return "redirect:/admin/spec";
    }

    @GetMapping(value = "/admin/spec/delete/{id}")
    public String deleteSpec(@PathVariable Long id, RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Delete successfully");
        specificationRepository.deleteById(id);
        return "redirect:/admin/spec";
    }
}
