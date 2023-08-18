package com.study.base.boot.aggregations.v1.order.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.base.boot.aggregations.v1.order.application.OrderService;
import com.study.base.boot.aggregations.v1.order.application.dto.req.CreateOrder;
import com.study.base.boot.aggregations.v1.order.presentation.dto.req.CreateOrderDto;
import com.study.base.boot.aggregations.v1.order.presentation.dto.req.CreateOrdersDto;
import com.study.base.boot.config.annotations.Get;
import com.study.base.boot.config.annotations.Post;
import com.study.base.boot.config.annotations.RestApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestApi("/v1/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Get
    public List<String> getOrders() {
        return List.of("A", "B", "C");
    }

    @Post
    public List<Long> createOrders(@RequestBody @Valid CreateOrdersDto request) {
        final var create = request.toCreateOrders();
        final var ids = orderService.creates(create);

        return ids;
    }
}

