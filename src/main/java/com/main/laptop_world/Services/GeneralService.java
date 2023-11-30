package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.ProductImages;
import com.main.laptop_world.Entity.Products;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface GeneralService {
    Long usernameHandler(Principal principal);
    void addImages(@RequestParam("files") MultipartFile[] files, List<ProductImages> imagesList, Products product) throws IOException;
}
