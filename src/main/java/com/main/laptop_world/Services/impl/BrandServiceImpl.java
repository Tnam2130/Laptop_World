package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.Brand;
import com.main.laptop_world.Repository.BrandRepository;
import com.main.laptop_world.Services.BrandService;
import com.main.laptop_world.Services.ProductService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BrandServiceImpl implements BrandService {
    private BrandRepository brandRepository;
    private ProductService productService;

    public BrandServiceImpl(BrandRepository brandRepository, ProductService productService) {
        this.brandRepository=brandRepository;
        this.productService= productService;
    }

    @Override
    public List<Brand> findAllBrand() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrandById(Long id) {
        Optional<Brand> optional = brandRepository.findById(id);
        Brand brand = null;
        if (optional.isPresent()){
            brand = optional.get();
        }else {
            throw new RuntimeException("Brand not found for id :: " + id);
        }
        return brand;
    }

    @Override
    public void saveBrand(Brand brand) {
        if (brand != null){
            this.brandRepository.save(brand);
        }else {
            System.out.println("Brand is null!!!");
        }
    }

    @Override
    public void deleteBrand(Long id) {
        this.brandRepository.deleteById(id);
    }

    @Override
    public Optional<Brand> findByTitle(String title) {
        return brandRepository.findByTitle(title);
    }

    @Override
    public void updateBrand(Brand brand) {
        brand.setTitle(brand.getTitle());
        brandRepository.save(brand);

    }
}
