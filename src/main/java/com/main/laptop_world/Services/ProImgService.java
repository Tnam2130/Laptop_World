package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.ProductImages;
import com.main.laptop_world.Repository.ProductImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProImgService {
    @Autowired
    ProductImagesRepository repository;
    public List<ProductImages> countImageByProductId(Long id){
        return repository.findImageUrlByProductId(id);
    }
    public List<ProductImages> findByProductId(Long id){
        return repository.findByProductsId(id);
    }
}
