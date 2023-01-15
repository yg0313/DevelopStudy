package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockServiceImplTest {

    @Autowired
    private StockService stockServiceImpl;

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
    public void stockDecrease(){
        stockServiceImpl.decreaseStock(1L, 1L);

        //100-1 = 99

        Stock stock = stockRepository.findById(1L).orElseThrow();

        assertEquals(99, stock.getQuantity());
    }

    @Test
    @DisplayName("동시에 100개의 요청 테스트")
    public void hundredRequestTest() throws InterruptedException {
        int threadCount = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(32); // 비동기로 실행하는 작업을 단순화하도록 사용.
        CountDownLatch latch = new CountDownLatch(threadCount);

        for(int i = 0; i< threadCount; i++){
            executorService.submit(()->{
                try{
                    stockServiceImpl.decreaseStock(1L, 1L);
                }finally {
                    latch.countDown(); // 100개의 쓰레드가 끝날때까지 카운팅
                }
            });
        }

        latch.await();

        Stock stock = stockRepository.findById(1L).orElseThrow();

        /**
         * 레이스 컨디션
         * 2개 이상의 쓰레드가 공유 데이터에 접근가능하고,
         * 동시에 변경하는 경우 일어나는문제.
         */
        // test 실패
        assertEquals(0L, stock.getQuantity());
    }
}