package com.study.base.boot.aggregations.v1.order.presentation.dto.req;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Builder
@Getter
public class CreateOrdersDto {

    private List<CreateOrderDto> createOrders;
}


