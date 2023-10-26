package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.ProductImages;

import java.util.List;

public interface ProductImgService {
    List<ProductImages> getAllImages();
    void saveImageFilesList(List<ProductImages> imagesList);
    ProductImages getImageById(Long id);
    void deleteImage(ProductImages productImages);
}
