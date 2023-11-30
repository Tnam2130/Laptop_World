package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.ProductImages;

import java.util.List;

public interface ProductImgService {
    List<ProductImages> getAllImages();
    void saveImageFilesList(List<ProductImages> imagesList);
    void updateImage(ProductImages productImages);
    ProductImages getImageById(Long id);
    public List<ProductImages> countImageByProductId(Long id);
    public List<ProductImages> findByProductId(Long id);
    void deleteImage(ProductImages productImages);

}
