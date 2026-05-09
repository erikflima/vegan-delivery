package com.eriklima.vegandelivery.delivery.tracking.domain.event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.time.OffsetDateTime;
import java.util.UUID;


@Getter
@AllArgsConstructor
@ToString
//Evento - Que diz que entrega foi concluida e marcada com o status "DELIVERED".
public class DeliveryFulfilledEvent {

    private final OffsetDateTime occurredAt;

    private final UUID deliveryId;
}