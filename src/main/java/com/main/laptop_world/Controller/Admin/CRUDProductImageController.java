package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.ProductImages;
import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Repository.ProductRepository;
import com.main.laptop_world.Repository.productImagesRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Controller
public class CRUDProductImageController {
    productImagesRepository productImagesRepository;
    ProductRepository productRepository;
    public CRUDProductImageController(productImagesRepository productImagesRepository,
                                      ProductRepository productRepository){
        this.productImagesRepository = productImagesRepository;
        this.productRepository = productRepository;
    }

    @RequestMapping("/admin/images")
    public String getProductForm(Model model) {
        List<ProductImages> productImages = productImagesRepository.findAll();
        List<Products> productList = productRepository.findAll();
        model.addAttribute("productImages", productImages);
        model.addAttribute("productImage",new ProductImages());
        model.addAttribute("productList", productList);

        return "admin/quanLyNhanHieu";
    }
    @PostMapping("/admin/images/add")
    public String save(@ModelAttribute("productImage") ProductImages productImages, @RequestParam MultipartFile file) throws IOException {
        productImages.setName(file.getOriginalFilename());
        productImages.setUrl(file.getBytes());
        productImagesRepository.save(productImages);
        return "redirect:/admin/images";
    }

}
