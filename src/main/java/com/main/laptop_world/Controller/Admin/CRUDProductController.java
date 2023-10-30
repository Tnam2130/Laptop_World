package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.*;
import com.main.laptop_world.Repository.ProductColorRepository;
import com.main.laptop_world.Repository.ProductRepository;
import com.main.laptop_world.Repository.ProductVersionRepository;
import com.main.laptop_world.Services.CategoryService;
import com.main.laptop_world.Services.ProductImgService;
import com.main.laptop_world.Services.ProductService;
import com.main.laptop_world.Services.ProductVersionService;
import com.main.laptop_world.util.FileUploadUtil;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    ProductRepository productRepository;
    ProductVersionRepository productVersionRepository;
    ProductColorRepository productColorRepository;
    ProductVersionService productVersionService;

    public CRUDProductController(
            ProductService productService,
            CategoryService categoryService,
            ProductImgService imgService,
            ProductRepository productRepository,
            ProductVersionRepository productVersionRepository,
            ProductColorRepository productColorRepository) {
        this.productRepository = productRepository;
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
    public String save(@Valid @ModelAttribute("product") Products product, BindingResult result,
                       @RequestParam("files") MultipartFile[] files) throws IOException {
        if(result.hasErrors()){
            return "admin/QuanLySanPham";
        }
//        if (productRepository.findByName(name).isPresent()) {
//            List<Products> productList = productService.findAllProduct();
//            model.addAttribute("productList", productList);
//            result.rejectValue("name", "error.product",
//                    "product name không được trùng!");
//            return "admin/QuanLySanPham";
//        }
        if (files != null && files.length > 0) {
            try {
                List<ProductImages> imagesList = new ArrayList<>();
                for (MultipartFile file : files) {
                    String fileName = file.getOriginalFilename();
                    String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
                    ProductImages images = new ProductImages(fileName, fileContent, product);
                    imagesList.add(images);
                    FileUploadUtil.saveFile(UPLOAD_DIRECTORY, fileName, file);
                }
                productService.saveProduct(product);
                product.setImages(imagesList);
                imgService.saveImageFilesList(imagesList);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/update/id={id}")
    public String getUpdateProduct(@PathVariable("id") Long id, Model model, RedirectAttributes ra, @ModelAttribute Products products) {
        Products product = productService.getProductById(id);
        model.addAttribute("product",product);
        return "admin/CRUDupdate/updateProduct";
    }
    @PostMapping("/admin/products/update")
    public String updateProduct(Products products) {
        productService.updateProduct(products);
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
    public String addVersion(@ModelAttribute("versions") ProductVersion productVersion,
                             String name, Model model, BindingResult result) {
        if (productVersion.getName() == null || productVersion.getName().isEmpty()) {
            List<ProductVersion> version = productVersionRepository.findAll();
            model.addAttribute("version", version);
            result.rejectValue("name", "error.version",
                    "Không được để trống Version name!");
            return "admin/ProductVersion";
        }
        if (productVersionRepository.findByName(name).isPresent()) {
            List<ProductVersion> version = productVersionRepository.findAll();
            model.addAttribute("version", version);
            result.rejectValue("name", "error.version",
                    "Version name không được trùng!");
            return "admin/ProductVersion";
        }
        productVersionRepository.save(productVersion);
        return "redirect:/admin/productsVersion";
    }

    @RequestMapping(value = "/admin/productsVersion/update/{id}")
    public String updateVersion(@PathVariable Long id, Model model) {
        ProductVersion productVersion = productVersionService.getProductById(id);
        model.addAttribute("version", productVersion);
        productVersionRepository.save(productVersion);
        return "admin/updateVersion";
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
    public String addColor(@ModelAttribute("colors") ProductColor productColor,
                           String color, Model model, BindingResult result) {
        if (productColor.getColor() == null || productColor.getColor().isEmpty()) {
            List<ProductColor> colors = productColorRepository.findAll();
            model.addAttribute("color", colors);
            result.rejectValue("color", "error.color",
                    "Không được để trống Color name!");
            return "admin/ProductColor";
        }
//        if (productColorRepository.findByName(color).isPresent()) {
//            List<ProductColor> colors = productColorRepository.findAll();
//            model.addAttribute("color", colors);
//            result.rejectValue("color", "error.color",
//                    "Color name không được trùng!");
//            return "admin/ProductColor";
//        }
        productColorRepository.save(productColor);
        return "redirect:/admin/productsColor";
    }

    @GetMapping(value = "/admin/productsColor/delete/{id}")
    public String deleteColor(@PathVariable Long id) {

        productColorRepository.deleteById(id);
        return "redirect:/admin/productsColor";
    }

}
