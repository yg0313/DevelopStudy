package com.example.orderservice.repository;

import com.example.orderservice.entity.OrderEntity;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

    OrderEntity findByOrderId(String orderId);
    Iterable<OrderEntity> findByUserId(String userId);
}
