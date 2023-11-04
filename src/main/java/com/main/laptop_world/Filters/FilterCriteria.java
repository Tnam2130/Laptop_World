package com.main.laptop_world.Filters;

import lombok.Data;

@Data
public class FilterCriteria {
    private Long categoryId;
    private Long brandId;
    private String priceSort;

    public void clear() {
        this.categoryId=null;
        this.brandId=null;
        this.priceSort=null;
    }
}
