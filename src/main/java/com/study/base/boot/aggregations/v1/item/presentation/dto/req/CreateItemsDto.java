package com.study.base.boot.aggregations.v1.item.presentation.dto.req;

import com.study.base.boot.aggregations.v1.item.application.dto.req.CreateItem;
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
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemsDto {

    @NotNull
    @Size(min = 1)
    @Valid
    private List<CreateItemDto> items;

    public List<CreateItem> toCreateItems() {
        return this.items.stream()
                .map(CreateItemDto::toCreate)
                .collect(Collectors.toList());
    }
}
