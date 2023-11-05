package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.Specifications;

import java.util.List;


public interface SpecificationService {
    List<Specifications> findAllSSpec();
    public Specifications getSpecById(Long id);
    void saveSpec( Specifications specifications);
    void deleteSpec(Long id);
    void updateSpec(Specifications specifications);
}
