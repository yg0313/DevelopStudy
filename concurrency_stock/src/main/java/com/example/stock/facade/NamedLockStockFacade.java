package com.example.stock.facade;

import com.example.stock.repository.LockRepository;
import com.example.stock.service.StockService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NamedLockStockFacade {

    private final LockRepository lockRepository;

    private final StockService stockService;

    public NamedLockStockFacade(LockRepository lockRepository, @Qualifier("namedLockStockServiceImpl") StockService stockService) {
        this.lockRepository = lockRepository;
        this.stockService = stockService;
    }

    @Transactional
    public void decreaseStock(Long id, Long quantity){
        try{
            lockRepository.getLock(id.toString());
            stockService.decreaseStock(id, quantity);
        }finally {
            lockRepository.releaseLock(id.toString());
        }
    }
}
