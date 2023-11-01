package com.main.laptop_world.Filters;

import lombok.Data;

@Data
public class FilterCriteria {
    private Long categoryId;
    private String priceSort;

    public void clear() {
        this.categoryId=null;
        this.priceSort=null;
    }
}
