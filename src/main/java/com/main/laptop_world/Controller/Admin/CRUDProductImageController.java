package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.ProductImages;
import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Services.GeneralService;
import com.main.laptop_world.Services.ProductImgService;
import com.main.laptop_world.Services.ProductService;
import com.main.laptop_world.util.FileUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.main.laptop_world.Constant.UploadDirectory.UPLOAD_DIRECTORY;

@Controller
public class CRUDProductImageController {
    ProductImgService imgService;
    ProductService productService;
    GeneralService generalService;

    public CRUDProductImageController(ProductImgService imgService,
                                      ProductService productService, GeneralService generalService) {
        this.imgService = imgService;
        this.productService = productService;
        this.generalService = generalService;
    }

    @GetMapping("/admin/images")
    public String getProductImagesForm(Model model) {
        List<ProductImages> productImageList = imgService.getAllImages();
        List<Products> productList = productService.findAllProduct();
        productImageList.sort(Comparator.comparing(img -> img.getProducts().getName()));
        model.addAttribute("productImageList", productImageList);
        model.addAttribute("productList", productList);
        model.addAttribute("productImages", new ProductImages());

        return "admin/QuanLyHinhAnh";
    }

    @PostMapping("/admin/images/add")
    public String addImagesToProduct(@ModelAttribute("productImages") ProductImages productImages,
                                     @RequestParam("files") MultipartFile[] files,
                                     BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "admin/QuanLyHinhAnh";
        }
        System.out.println(productImages.getProducts().getName());
        if (files != null && files.length > 0) {
            try {
                List<ProductImages> imagesList = new ArrayList<>();
                Products product = productImages.getProducts();
                generalService.addImages(files, imagesList, product);
                ra.addFlashAttribute("message", "Save successfully");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return "redirect:/admin/images";
    }

    @GetMapping("/admin/images/update/id={id}")
    public String getFormUpdateImages(@PathVariable Long id, Model model, @ModelAttribute ProductImages image) {
        ProductImages productImages = imgService.getImageById(id);
        List<Products> productList = productService.findAllProduct();
        model.addAttribute("productImage", productImages);
        model.addAttribute("productList", productList);
        return "admin/Update/updateImage";
    }

    @PostMapping("/admin/images/update/id={id}")
    public String updateImages(@PathVariable Long id, ProductImages image, @RequestParam("file") MultipartFile file, RedirectAttributes ra) throws IOException {
        ProductImages existingImage = imgService.getImageById(id);
        if(existingImage != null){
            String fileName = file.getName();
            String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
            image.setName(fileName);
            image.setUrl(fileContent);
            imgService.updateImage(image);
            FileUploadUtil.saveFile(UPLOAD_DIRECTORY, fileName, file);
            ra.addFlashAttribute("message", "Update successfully");
        }

        return "redirect:/admin/images";
    }

    @RequestMapping(value = "/admin/images/delete/{id}")
    public String deleteImages(@PathVariable Long id, RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Delete successfully");
        ProductImages productImages = imgService.getImageById(id);
        imgService.deleteImage(productImages);
        return "redirect:/admin/images";
    }
}
