package com.study.base.boot.aggregations.v1.order.presentation.dto.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateOrderItemDto {

    private long itemId;

    @NotNull
    @Size
    @Min(0)
    @Max(0)
    @Pattern(regexp = "^.*$")
    private String itemName;
    private int price;
}



