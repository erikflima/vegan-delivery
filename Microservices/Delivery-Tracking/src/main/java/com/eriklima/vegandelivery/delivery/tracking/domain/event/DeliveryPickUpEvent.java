package com.eriklima.vegandelivery.delivery.tracking.domain.event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString
//Evento - Significa que a entrega foi pega por um entegador, e passou para o estado "em transito"
public class DeliveryPickUpEvent {

    private final OffsetDateTime occurredAt;

    private final UUID deliveryId;
}