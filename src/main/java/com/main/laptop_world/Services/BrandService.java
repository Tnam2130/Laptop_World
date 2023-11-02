package com.main.laptop_world.Services;


import com.main.laptop_world.Entity.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    List<Brand> findAllBrand();
    public Brand getBrandById(Long id);
    void saveBrand(Brand brand);
    void deleteBrand(Long id);
    Optional<Brand> findByTitle(String title);
    void updateBrand(Brand brand);
}
