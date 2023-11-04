package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.Stock;
import com.main.laptop_world.Repository.StockRepository;
import com.main.laptop_world.Services.StockService;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {
    private StockRepository stockRepository;
    public StockServiceImpl(StockRepository stockRepository){
        this.stockRepository=stockRepository;
    }
    @Override
    public Stock findProductById(Long productId) {
        return stockRepository.findByProducts_Id(productId);
    }

    @Override
    public Stock save(Stock stock) {
        return stockRepository.save(stock);
    }
}
