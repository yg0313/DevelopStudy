package com.example.stock.facade;

import com.example.stock.repository.RedisRepository;
import com.example.stock.service.StockService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class LettuceLockStockFacade {

    private final RedisRepository redisRepository;

    private final StockService stockService;

    public LettuceLockStockFacade(RedisRepository redisRepository, @Qualifier("stockServiceImpl") StockService stockService) {
        this.redisRepository = redisRepository;
        this.stockService = stockService;
    }

    public void decreaseStock(Long key, Long quantity) throws InterruptedException{
        while (!redisRepository.lock(key)) {
            Thread.sleep(100);
        }

        try{
            stockService.decreaseStock(key, quantity);
        }finally {
            redisRepository.unlock(key);
        }
    }
}
