package com.study.base.boot.aggregations.v1.item.application.dto.req;


import com.study.base.boot.aggregations.v1.item.presentation.dto.req.CreateItemDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateItem {

    private String itemName;
    private int price;
    private CreateItemStock stock;
}
