package com.study.base.boot.aggregations.v1.order.infrastructure.repository.dto.res;

import com.study.base.boot.aggregations.v1.order.domain.entity.OrderItemEntity;
import com.study.base.boot.aggregations.v1.order.domain.enumerations.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderInfoProjection {

    private String orderNumber;
    private OrderStatusEnum status;
    private int price;
    private LocalDateTime createdDate;
}
