package com.example.stock.facade;

import com.example.stock.service.StockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedissonLockStockFacade {

    private final RedissonClient redissonClient;

    private final StockService stockService;

    public RedissonLockStockFacade(RedissonClient redissonClient, @Qualifier("stockServiceImpl") StockService stockService) {
        this.redissonClient = redissonClient;
        this.stockService = stockService;
    }

    public void decreaseStock(Long key, Long quantity){
        RLock lock = redissonClient.getLock(key.toString());

        try{
            boolean available = lock.tryLock(100, 1, TimeUnit.SECONDS);

            if(!available){
                System.out.println("lock 획득 실패");
                return;
            }

            stockService.decreaseStock(key, quantity);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }
}
