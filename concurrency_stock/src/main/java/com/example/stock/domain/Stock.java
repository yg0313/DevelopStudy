package com.example.stock.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Long quantity;

    //optimistic Lock 사용
    @Version
    private Long version;

    public Stock(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public void decrease(Long quantity){
        if(this.quantity - quantity < 0){
            throw new RuntimeException("foo");
        }

        this.quantity = this.quantity - quantity;
    }
}
