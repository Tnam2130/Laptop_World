package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.Stock;

public interface StockService {
   Stock findProductById(Long productId);
   Stock save(Stock stock);
}
