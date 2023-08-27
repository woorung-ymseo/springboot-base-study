package com.study.base.boot.aggregations.v1.item.domain;

import com.study.base.boot.aggregations.v1.item.application.dto.req.CreateItem;
import com.study.base.boot.aggregations.v1.item.domain.entity.ItemStockEntity;
import com.study.base.boot.aggregations.v1.item.infrastructure.repository.ItemRepository;
import com.study.base.boot.config.entity.item.AbstractItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;
import java.util.stream.Collectors;

@Table(catalog = "base", name ="item")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@SuperBuilder
@Getter
public class ItemAggregate extends AbstractItem {

    @OneToOne(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private ItemStockEntity itemStock;

    public static final List<ItemAggregate> creates(ItemRepository itemRepository, List<CreateItem> createItems) {
        final var items = createItems.stream()
                .map(createItem ->
                        ItemAggregate.builder()
                                .build()
                                .patch(createItem))
                .collect(Collectors.toList());

        itemRepository.saveAll(items);

        return items;
    }

    public ItemAggregate patch(CreateItem createItem) {
        final var itemStock = createItem.getStock();
        this.itemName = StringUtils.defaultIfEmpty(createItem.getItemName(), this.itemName);
        this.price =  createItem.getPrice();

        if (this.itemStock == null) {
            this.itemStock = ItemStockEntity.builder().build();
        }

        this.itemStock
                .patch(itemStock)
                .putItem(this);

        return this;
    }
}
