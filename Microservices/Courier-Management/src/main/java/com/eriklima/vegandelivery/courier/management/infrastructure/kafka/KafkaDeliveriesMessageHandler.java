package com.eriklima.vegandelivery.courier.management.infrastructure.kafka;
import com.eriklima.vegandelivery.courier.management.domain.service.CourierDeliveryService;
import com.eriklima.vegandelivery.courier.management.infrastructure.event.DeliveryFulfilledIntegrationEvent;
import com.eriklima.vegandelivery.courier.management.infrastructure.event.DeliveryPlacedIntegrationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener( topics = { "deliveries.v1.events"}, groupId = "courier-management")
@Slf4j
@RequiredArgsConstructor
// Classe listener/consumer do Kafka que fica ouvindo automaticamente o topico "deliveries.v1.events" e manda os eventos para os metodos dessa mesma classe de acordo com o tipo do evento.
public class KafkaDeliveriesMessageHandler {


    private final CourierDeliveryService courierDeliveryService;


    @KafkaHandler( isDefault = true )                      // Handler padrão executado quando o tipo da mensagem não encontra um handler específico
    public void defaultHandler( @Payload Object object ) { // Metodo que recebe qualquer objeto desconhecido do Kafka

        log.info("\n--------------------------------------------- ");
        log.info("Evento recebido!");
        log.info("Tipo de evento: Generico" );
        log.info("Dados do evento recebido: {}", object );
        log.info("--------------------------------------------- \n");
    }


    @KafkaHandler                                                           // Define um handler Kafka específico para DeliveryPlacedIntegrationEvent
    public void handle( @Payload DeliveryPlacedIntegrationEvent event ) {   // Metodo chamado automaticamente quando chega um evento DeliveryPlacedIntegrationEvent

        log.info("\n--------------------------------------------- ");
        log.info("Evento recebido!");
        log.info("Tipo de evento: " + event.getClass().getSimpleName() );
        log.info("Id da delivery: " + event.getDeliveryId() );
        log.info("Momento que o evento ocorreu: " + event.getOccurredAt() );
        log.info("--------------------------------------------- \n");

        // Chama a regra de negócio para atribuir um entregador à entrega
        courierDeliveryService.assign( event.getDeliveryId() );
    }


    @KafkaHandler                                                             // Define um handler Kafka específico para DeliveryFulfilledIntegrationEvent
    public void handle( @Payload DeliveryFulfilledIntegrationEvent event ) {  // Metodo chamado automaticamente quando chega um evento DeliveryFulfilledIntegrationEvent

         log.info("\n--------------------------------------------- ");
        log.info("Evento recebido!");
        log.info("Tipo de evento: " + event.getClass().getSimpleName() );
        log.info("Id da delivery: " + event.getDeliveryId() );
        log.info("Momento que o evento ocorreu: " + event.getOccurredAt() );
        log.info("--------------------------------------------- \n");


        // Chama a regra de negócio para marcar a entrega como concluída
        courierDeliveryService.fulfill( event.getDeliveryId() );
    }

}