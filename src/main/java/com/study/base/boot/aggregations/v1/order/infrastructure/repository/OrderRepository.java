package com.study.base.boot.aggregations.v1.order.infrastructure.repository;

import com.study.base.boot.aggregations.v1.order.domain.OrderAggregate;
import com.study.base.boot.aggregations.v1.order.domain.enumerations.OrderStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderAggregate, Long> {

    Page<OrderAggregate> findAllByStatus(OrderStatusEnum status, Pageable pageable);

}
