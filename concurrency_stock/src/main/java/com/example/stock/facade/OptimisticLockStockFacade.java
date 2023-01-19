package com.example.stock.facade;

import com.example.stock.service.StockService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * optimistic lock 실패했을때 재시도 설정.
 */
@Service
public class OptimisticLockStockFacade{

    private StockService stockService;

    public OptimisticLockStockFacade(@Qualifier("optimisticLockStockServiceImpl") StockService stockService) {
        this.stockService = stockService;
    }

    public void decreaseStock(Long id, Long quantity) throws InterruptedException {
        while (true){
            try {
                stockService.decreaseStock(id, quantity);
                break;
            }catch (Exception e){
                Thread.sleep(50);
            }
        }
    }
}
