package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.Category;
import com.main.laptop_world.Entity.ProductImages;
import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Services.CategoryService;
import com.main.laptop_world.Services.ProductImgService;
import com.main.laptop_world.Services.ProductService;
import com.main.laptop_world.util.FileUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CRUDProductController {
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "./static/images/products";
    ProductService productService;
    CategoryService categoryService;
    ProductImgService imgService;

    public CRUDProductController(
            ProductService productService,
            CategoryService categoryService, ProductImgService imgService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.imgService = imgService;
    }

    @RequestMapping("/admin/products")
    public String getProductForm(Model model) {
        List<Products> productList = productService.findAllProduct();
        List<Category> categories = categoryService.findAllCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("productList", productList);
        model.addAttribute("product", new Products());
        return "admin/QuanLySanPham";
    }

    @PostMapping("/admin/products/add")
    public String save(@ModelAttribute("product") Products products,
                       @RequestParam("files") MultipartFile[] files) throws IOException {
        if (files != null && files.length > 0) {
            try {
                List<ProductImages> imagesList = new ArrayList<>();
                for (MultipartFile file : files) {
                    System.out.println(file.getOriginalFilename());
                    ;
                    String fileName = file.getOriginalFilename();
                    String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
                    ProductImages images = new ProductImages(fileName, fileContent, products);
                    imagesList.add(images);
                    FileUploadUtil.saveFile(UPLOAD_DIRECTORY, fileName, file);
                }
                System.out.println(products);
                productService.saveProduct(products);
                products.setImages(imagesList);
                imgService.saveImageFilesList(imagesList);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return "redirect:/admin/products";
    }

    @RequestMapping(value = "/admin/products/update/{id}")
    public String updateProduct(@PathVariable Long id) {
        Products product = productService.getProductById(id);
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    @RequestMapping(value = "/admin/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        Products product = productService.getProductById(id);
        productService.deleteProduct(product);
        return "redirect:/admin/products";
    }
}
