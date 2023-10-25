package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.Category;
import com.main.laptop_world.Entity.ProductImages;
import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Repository.CategoryRepository;
import com.main.laptop_world.Repository.ProductRepository;
import com.main.laptop_world.Repository.productImagesRepository;
import com.main.laptop_world.Services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class CRUDProductController {
    ProductRepository productRepository;
    ProductService productService;
    CategoryRepository categoryRepository;

    productImagesRepository productImagesRepository;
    public CRUDProductController(ProductRepository productRepository,
                                 ProductService productService,
                                 CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.categoryRepository = categoryRepository;

    }

    @RequestMapping("/admin/products")
    public String getProductForm(Model model) {
        List<Products> productList = productRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("productList", productList);
        model.addAttribute("product",new Products());
        return "admin/quanLySanPham";
    }

    @PostMapping("/admin/products/add")
    public String save(@ModelAttribute("product") Products products, Model model,@RequestParam MultipartFile url) throws IOException {

        ProductImages productImages = new ProductImages();
        model.addAttribute("productImages",productImages);
        productImages.setUrl(url.getBytes());
        products.getImages().add(productImages);
        productRepository.save(products);
        return "redirect:/admin/products";
    }



    @GetMapping(value = "/admin/products/delete/{id}")
    public String delete(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }
}
