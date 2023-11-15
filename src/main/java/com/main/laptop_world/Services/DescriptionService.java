package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.Description;
import java.util.List;

public interface DescriptionService {

    public Description getById(Long id);
    void updateDesc(Description description);
    List<Description> findAllDesc();
    List<Description> getDescriptionProduct(Long productId);
    void saveDesc(Description description);
    void deleteDesc(Description description);

}
