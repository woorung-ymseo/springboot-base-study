package com.study.base.boot.aggregations.v1.order.infrastructure.repository.dto.req;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderCondition {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int price;
    private Pageable pageable;
}
