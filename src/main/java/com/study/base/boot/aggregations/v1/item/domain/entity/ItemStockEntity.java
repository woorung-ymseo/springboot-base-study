package com.study.base.boot.aggregations.v1.item.domain.entity;

import com.study.base.boot.aggregations.v1.item.application.dto.req.CreateItemStock;
import com.study.base.boot.aggregations.v1.item.domain.ItemAggregate;
import com.study.base.boot.config.entity.item.AbstractItemStock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

@Table(catalog = "base", name ="item_stock")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@SuperBuilder
@Getter
public class ItemStockEntity extends AbstractItemStock {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private ItemAggregate item;

    public ItemStockEntity patch(CreateItemStock createItemStock) {
        this.stockQty = createItemStock.getStockQty();

        return this;
    }

    public ItemStockEntity putItem(ItemAggregate item) {
        this.item = item;

        return this;
    }
}
