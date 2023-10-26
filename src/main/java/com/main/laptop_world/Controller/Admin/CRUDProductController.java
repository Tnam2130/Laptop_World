package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.*;
import com.main.laptop_world.Repository.ProductColorRepository;
import com.main.laptop_world.Repository.ProductVersionRepository;
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
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/uploads/products";
    ProductService productService;
    CategoryService categoryService;
    ProductImgService imgService;
    ProductVersionRepository productVersionRepository;
    ProductColorRepository productColorRepository;

    public CRUDProductController(
            ProductService productService,
            CategoryService categoryService,
            ProductImgService imgService,
            ProductVersionRepository productVersionRepository,
            ProductColorRepository productColorRepository) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.imgService = imgService;
        this.productVersionRepository = productVersionRepository;
        this.productColorRepository = productColorRepository;
    }

    @RequestMapping("/admin/products")
    public String getProductForm(Model model) {
        List<Products> productList = productService.findAllProduct();
        List<Category> categories = categoryService.findAllCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("productList", productList);
        model.addAttribute("product", new Products());
        boolean selected=true;
        model.addAttribute("selectedValue", selected);
        return "admin/QuanLySanPham";
    }

    @PostMapping("/admin/products/add")
    public String save(@ModelAttribute("product") Products products,
                       @RequestParam("files") MultipartFile[] files) throws IOException {
        if (files != null && files.length > 0) {
            try {
                List<ProductImages> imagesList = new ArrayList<>();
                for (MultipartFile file : files) {
                    String fileName = file.getOriginalFilename();
                    String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
                    ProductImages images = new ProductImages(fileName, fileContent, products);
                    imagesList.add(images);
                    FileUploadUtil.saveFile(UPLOAD_DIRECTORY, fileName, file);
                }
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

    @RequestMapping("/admin/productsVersion")
    public String getProductVersion(Model model) {
        List<ProductVersion> version = productVersionRepository.findAll();
        List<Products> productList = productService.findAllProduct();
        model.addAttribute("version", version);
        model.addAttribute("productList", productList);
        model.addAttribute("versions", new ProductVersion());

        return "admin/ProductVersion";
    }

    @PostMapping("/admin/productsVersion/add")
    public String addVersion(@ModelAttribute("versions") ProductVersion productVersion) {
        productVersionRepository.save(productVersion);
        return "redirect:/admin/productsVersion";
    }

    @GetMapping(value = "/admin/productsVersion/delete/{id}")
    public String deleteVersion(@PathVariable Long id) {

        productVersionRepository.deleteById(id);
        return "redirect:/admin/productsVersion";
    }

    @RequestMapping("/admin/productsColor")
    public String getProductColor(Model model) {
        List<ProductColor> color = productColorRepository.findAll();
        List<ProductVersion> version = productVersionRepository.findAll();
        List<Products> productList = productService.findAllProduct();
        model.addAttribute("color", color);
        model.addAttribute("versions", version);
        model.addAttribute("productList", productList);
        model.addAttribute("colors", new ProductColor());

        return "admin/ProductColor";
    }

    @PostMapping("/admin/productsColor/add")
    public String addColor(@ModelAttribute("colors") ProductColor productColor) {
        productColorRepository.save(productColor);
        return "redirect:/admin/productsColor";
    }

    @GetMapping(value = "/admin/productsColor/delete/{id}")
    public String deleteColor(@PathVariable Long id) {

        productColorRepository.deleteById(id);
        return "redirect:/admin/productsColor";
    }

}
