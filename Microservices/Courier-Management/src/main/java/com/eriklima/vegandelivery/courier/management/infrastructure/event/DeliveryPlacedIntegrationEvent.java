package com.eriklima.vegandelivery.courier.management.infrastructure.event;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
// Classe que representa o evento de integração de entrega criada/solicitada
public class DeliveryPlacedIntegrationEvent {

    private OffsetDateTime occurredAt;

    private UUID deliveryId;
}