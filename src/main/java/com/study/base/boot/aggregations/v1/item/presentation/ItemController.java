package com.study.base.boot.aggregations.v1.item.presentation;

import com.study.base.boot.aggregations.v1.item.application.ItemService;
import com.study.base.boot.aggregations.v1.item.application.dto.req.CreateItem;
import com.study.base.boot.aggregations.v1.item.presentation.dto.req.CreateItemsDto;
import com.study.base.boot.config.annotations.Post;
import com.study.base.boot.config.annotations.RestApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestApi("/v1/items")
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @Post
    public List<Long> createItems(@RequestBody @Valid CreateItemsDto request) {
        final var createItems = request.toCreateItems();
        final var ids = itemService.creates(createItems);

        return ids;
    }
}
