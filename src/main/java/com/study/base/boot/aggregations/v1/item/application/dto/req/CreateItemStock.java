package com.study.base.boot.aggregations.v1.item.application.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateItemStock {
    private int stockQty;
}
