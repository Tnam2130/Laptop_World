package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.ProductImages;
import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Services.GeneralService;
import com.main.laptop_world.Services.ProductImgService;
import com.main.laptop_world.Services.ProductService;
import com.main.laptop_world.Services.UserService;
import com.main.laptop_world.security.oauth2.CustomOAuth2User;
import com.main.laptop_world.util.FileUploadUtil;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;

import static com.main.laptop_world.Constant.UploadDirectory.UPLOAD_DIRECTORY;

@Service
public class GeneralServiceImpl implements GeneralService {
    private UserService userService;
    private ProductService productService;
    private ProductImgService imgService;

    public GeneralServiceImpl(UserService userService, ProductService productService, ProductImgService imgService) {
        this.userService = userService;
        this.productService=productService;
        this.imgService=imgService;
    }

    @Override
    public Long usernameHandler(Principal principal) {
        return getUsername(principal);
    }

    @Override
    public void addImages(MultipartFile[] files, List<ProductImages> imagesList, Products product) throws IOException {
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
            ProductImages images = new ProductImages(fileName, fileContent, product);
            System.out.println("file name: "+ images.getName() + "product: "+ images.getProducts().getName());
            imagesList.add(images);
            FileUploadUtil.saveFile(UPLOAD_DIRECTORY, fileName, file);
            productService.saveProduct(product);
            product.setImages(imagesList);
            imgService.saveImageFilesList(imagesList);
        }
    }

    public Long getUsername(Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            CustomOAuth2User oauthUser = (CustomOAuth2User) ((Authentication) principal).getPrincipal();
            String existingEmail = oauthUser.getUsername();
            user = userService.findByUsername(existingEmail);
            System.out.println(existingEmail);
        }
        return user.getId();
    }
}
