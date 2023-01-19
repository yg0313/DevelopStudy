package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptimisticLockStockServiceImpl implements StockService{

    private final StockRepository stockRepository;

    @Transactional
    @Override
    public void decreaseStock(Long id, Long quantity) {
        Stock stock = stockRepository.findByWithOptimisticLock(id);

        stockRepository.saveAndFlush(stock);
    }
}
