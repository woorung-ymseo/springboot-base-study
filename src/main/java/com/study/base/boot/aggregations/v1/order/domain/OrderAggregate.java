package com.study.base.boot.aggregations.v1.order.domain;

import com.study.base.boot.aggregations.v1.order.application.dto.req.CreateOrder;
import com.study.base.boot.aggregations.v1.order.infrastructure.repository.OrderRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Table(catalog = "base", name ="order")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@Getter
public class OrderAggregate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;
    private String orderName;
    private String status;
    private int price;
    private int deliveryFee;
    private String address;
    private long userId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public static final List<OrderAggregate> creates(OrderRepository orderRepository, List<CreateOrder> createOrders) {
        Assert.notEmpty(createOrders, "createOrders is null");

        final var orders = createOrders.stream()
                .map(createOrder ->
                        OrderAggregate.builder()
                                .build()
                                .patch(createOrder)
                ).collect(Collectors.toList());

        orderRepository.saveAll(orders);

        return orders;
    }

    public OrderAggregate create(OrderRepository orderRepository) {
        orderRepository.save(this);

        return this;
    }

    public OrderAggregate patch(CreateOrder createOrder) {
        this.orderNumber = StringUtils.defaultIfEmpty(createOrder.getOrderNumber(), this.orderNumber);
        this.orderName = StringUtils.defaultIfEmpty(createOrder.getOrderNumber(), this.orderName);
        this.price = createOrder.getPrice();
        this.deliveryFee = createOrder.getDeliveryFee();
        this.address = StringUtils.defaultIfEmpty(createOrder.getOrderNumber(), this.address);
        this.userId = createOrder.getUserId();
        this.createdDate = LocalDateTime.now();

        return this;
    }
}
