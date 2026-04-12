package com.eriklima.vegandelivery.delivery.tracking.domain.model;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter(AccessLevel.PRIVATE)
@Getter
public class Item {

    @EqualsAndHashCode.Include
    private UUID id;

    private String name;

    private Integer quantity;


    static Item brandNew(String name, Integer quantity) {

        Item item = new Item();
        item.setId(UUID.randomUUID());
        item.setName(name);
        item.setQuantity(quantity);

        return item;
    }
}