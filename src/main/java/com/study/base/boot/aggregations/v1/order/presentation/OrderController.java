package com.study.base.boot.aggregations.v1.order.presentation;

import com.study.base.boot.aggregations.v1.order.application.OrderService;
import com.study.base.boot.aggregations.v1.order.application.dto.req.GetOrder;
import com.study.base.boot.aggregations.v1.order.domain.OrderAggregate;
import com.study.base.boot.aggregations.v1.order.domain.enumerations.OrderStatusEnum;
import com.study.base.boot.aggregations.v1.order.presentation.dto.req.CreateOrdersDto;
import com.study.base.boot.aggregations.v1.order.presentation.dto.req.GetOrderDto;
import com.study.base.boot.aggregations.v1.order.presentation.dto.res.OrderDto;
import com.study.base.boot.aggregations.v1.order.presentation.mapper.OrderEDMapper;
import com.study.base.boot.config.annotations.Get;
import com.study.base.boot.config.annotations.Patch;
import com.study.base.boot.config.annotations.Post;
import com.study.base.boot.config.annotations.RestApi;
import com.study.base.boot.config.controller.SupportController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@RestApi("/v1/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController extends SupportController {

    private final OrderService orderService;

    private final OrderEDMapper orderEDMapper;

    @Get
    public Page<OrderDto> getOrders(
            GetOrderDto request,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        final GetOrder getOrder = request.toGetOrder(pageable);
        final Page<OrderAggregate> pageOrders = orderService.list(getOrder);

        return response(orderEDMapper, pageOrders, pageable);
    }

    @Get("/{id}")
    public OrderDto getOrder(@PathVariable long id) {
        OrderAggregate orderAggregate = orderService.get(id);

        OrderDto orderDto = orderEDMapper.toDto(orderAggregate);

        return orderDto;
    }

    @Get("/status/{status}")
    public Page<OrderDto> getOrders(
            @PathVariable OrderStatusEnum status,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<OrderAggregate> pageOrders = orderService.listByStatus(status, pageable);
        List<OrderAggregate> orders = pageOrders.getContent();

        List<OrderDto> orderDtos = orders.stream()
                .map(order -> orderEDMapper.toDto(order))
                .collect(Collectors.toList());

        return new PageImpl<>(orderDtos, pageable, pageOrders.getTotalElements());
    }

    @Post
    public List<Long> createOrders(@RequestBody @Valid CreateOrdersDto request) {
        final var create = request.toCreateOrders();
        final var ids = orderService.creates(create);

        return ids;
    }

    @Patch("/{id}/status/{status}")
    public void changeOrderStatus(
            @PathVariable long id,
            @PathVariable OrderStatusEnum status
    ) {
        orderService.changeStatus(id, status);
    }
}

