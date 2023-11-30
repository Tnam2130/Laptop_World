package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.ProductImages;
import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Repository.ProductImagesRepository;
import com.main.laptop_world.Services.ProductImgService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImgServiceImpl implements ProductImgService {
    private final ProductImagesRepository repository;

    public ProductImgServiceImpl(ProductImagesRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductImages> getAllImages() {
        return repository.findAll();
    }

    @Override
    public void saveImageFilesList(List<ProductImages> imagesList) {
        repository.saveAll(imagesList);
    }

    @Override
    public void updateImage(ProductImages productImages) {
        ProductImages existingImage = getImageById(productImages.getId());
        if (existingImage != null) {
            if (productImages.getProducts() == null || productImages.getProducts().equals(existingImage.getProducts())) {
                productImages.setProducts(existingImage.getProducts());
            } else {
                productImages.setProducts(productImages.getProducts());
            }
            repository.save(productImages);
        }
    }

    @Override
    public ProductImages getImageById(Long id) {
        Optional<ProductImages> optional = repository.findById(id);
        ProductImages images = null;
        if (optional.isPresent()) {
            images = optional.get();
        } else {
            throw new RuntimeException(" Image not found for id :: " + id);
        }
        return images;
    }

    @Override
    public List<ProductImages> countImageByProductId(Long id) {
        return repository.findImageUrlByProductId(id);
    }

    @Override
    public List<ProductImages> findByProductId(Long id) {
        return repository.findByProductsId(id);
    }

    @Override
    public void deleteImage(ProductImages productImages) {
        this.repository.delete(productImages);
    }


}
