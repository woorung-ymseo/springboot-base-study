package com.study.base.boot.aggregations.v1.item.application;

import com.study.base.boot.aggregations.v1.item.application.dto.req.CreateItem;
import com.study.base.boot.aggregations.v1.item.domain.ItemAggregate;
import com.study.base.boot.aggregations.v1.item.infrastructure.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public List<Long> creates(List<CreateItem> createItems) {
        final var items = ItemAggregate.creates(itemRepository, createItems);

        return items.stream()
                .map(ItemAggregate::getId)
                .collect(Collectors.toList());
    }
}
