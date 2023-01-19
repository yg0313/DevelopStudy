package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PessimisticLockStockServiceImplTest {

    @Qualifier("pessimisticLockStockServiceImpl")
    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    public void before(){
        Stock stock = new Stock(1L, 100L);

        stockRepository.saveAndFlush(stock);
    }

    @AfterEach
    public void after(){
        stockRepository.deleteAll();
    }

    @Test
    @DisplayName("pessimistic Lock, 동시에 100개의 요청 테스트")
    public void hundredRequestTest() throws InterruptedException {
        int threadCount = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(32); // 비동기로 실행하는 작업을 단순화하도록 사용.
        CountDownLatch latch = new CountDownLatch(threadCount);

        for(int i = 0; i< threadCount; i++){
            executorService.submit(()->{
                try{
                    stockService.decreaseStock(1L, 1L);
                }finally {
                    latch.countDown(); // 100개의 쓰레드가 끝날때까지 카운팅
                }
            });
        }

        latch.await();

        Stock stock = stockRepository.findById(1L).orElseThrow();

        assertEquals(0L, stock.getQuantity());
    }
}