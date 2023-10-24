package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class CRUDProductController {
    private ProductService productService;

    public CRUDProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public String getProductForm(Model model) {
        Products product = new Products();
        model.addAttribute("product", product);
        return "admin/quanLySanPham";
    }

    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute("product") Products product) {
        productService.saveProduct(product);
        return "redirect:/admin/quanLySanPham";
    }

}
