package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService{

    private final StockRepository stockRepository;

    /**
     * synchronized : 각 프로세스 안에서만 보장되기때문에,
     * 여러 스레드에서 동시에 데이터 접근이 가능해지기때문에 레이스 컨디션 발생.
     */
    @Override
    @Transactional //stockService 새로 만들어서 해당 메소드 실행.
    public void decreaseStock(Long productId, Long quantity) {

        Stock stock = stockRepository.findById(productId).orElseThrow();

        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }
}
