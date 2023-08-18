package com.study.base.boot.aggregations.v1.order.presentation.dto.req;

import com.study.base.boot.aggregations.v1.order.application.dto.req.CreateOrder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Builder
@Getter
public class CreateOrdersDto {

    @Valid
    private List<CreateOrderDto> createOrders;

    public List<CreateOrder> toCreateOrders() {
        return this.createOrders.stream()
                .map(CreateOrderDto::toCreate)
                .collect(Collectors.toList());
    }
}


