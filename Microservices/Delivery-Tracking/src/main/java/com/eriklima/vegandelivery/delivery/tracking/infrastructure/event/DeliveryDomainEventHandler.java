package com.eriklima.vegandelivery.delivery.tracking.infrastructure.event;
import com.eriklima.vegandelivery.delivery.tracking.domain.event.DeliveryFulfilledEvent;
import com.eriklima.vegandelivery.delivery.tracking.domain.event.DeliveryPickUpEvent;
import com.eriklima.vegandelivery.delivery.tracking.domain.event.DeliveryPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import static com.eriklima.vegandelivery.delivery.tracking.infrastructure.kafka.KafkaTopicConfig.deliveryEventsTopicName;

@Component
@Slf4j // Lombok cria automaticamente um objeto "log".
@RequiredArgsConstructor
// Classe responsável por escutar e reagir aos eventos de domínio(classe) Delivery.
public class DeliveryDomainEventHandler {


    private final IntegrationEventPublisher integrationEventPublisher;


    @EventListener // Diz ao Spring "quando um evento  do tipo "DeliveryPlacedEvent" acontecer, execute este metodo automaticamente".
    public void handle( DeliveryPlacedEvent event ) {

        log.info("\n--------------------------------------------- ");
        log.info("Evento ocorrido!");
        log.info("Tipo de evento: " + event.getClass().getSimpleName() );
        log.info("Id da delivery: " + event.getDeliveryId() );
        log.info("Momento que o evento ocorreu: " + event.getOccurredAt() );
        log.info("--------------------------------------------- \n");

        /* Publica o evento no Kafka, enviando o objeto do evento como payload, o deliveryId como chave da mensagem (key) e o tópico de destino.
           -> event: dados do evento que aconteceu
           -> deliveryId: identificador usado como chave da mensagem (ajuda o Kafka a manter eventos do mesmo delivery organizados)
           -> deliveryEventsTopicName: topico para onde a mensagem sera enviada */
        integrationEventPublisher.publish( event, event.getDeliveryId().toString(), deliveryEventsTopicName );
    }


    @EventListener // Diz ao Spring "quando um evento  do tipo "DeliveryPickUpEvent" acontecer, execute este metodo automaticamente".
    public void handle( DeliveryPickUpEvent event ) {

        log.info("\n--------------------------------------------- ");
        log.info("Evento ocorrido!");
        log.info("Tipo de evento: " + event.getClass().getSimpleName() );
        log.info("Id da delivery: " + event.getDeliveryId() );
        log.info("Momento que o evento ocorreu: " + event.getOccurredAt() );
        log.info("--------------------------------------------- \n");

        /* Publica o evento no Kafka, enviando o objeto do evento como payload, o deliveryId como chave da mensagem (key) e o tópico de destino.
           -> event: dados do evento que aconteceu
           -> deliveryId: identificador usado como chave da mensagem (ajuda o Kafka a manter eventos do mesmo delivery organizados)
           -> deliveryEventsTopicName: topico para onde a mensagem sera enviada */
        integrationEventPublisher.publish( event, event.getDeliveryId().toString(), deliveryEventsTopicName );
    }


    @EventListener // Diz ao Spring "quando um evento  do tipo "DeliveryFulfilledEvent" acontecer, execute este metodo automaticamente".
    public void handle( DeliveryFulfilledEvent event ) {

        log.info("\n--------------------------------------------- ");
        log.info("Evento ocorrido!");
        log.info("Tipo de evento: " + event.getClass().getSimpleName() );
        log.info("Id da delivery: " + event.getDeliveryId() );
        log.info("Momento que o evento ocorreu: " + event.getOccurredAt() );
        log.info("--------------------------------------------- \n");

        /* Publica o evento no Kafka, enviando o objeto do evento como payload, o deliveryId como chave da mensagem (key) e o tópico de destino.
           -> event: dados do evento que aconteceu
           -> deliveryId: identificador usado como chave da mensagem (ajuda o Kafka a manter eventos do mesmo delivery organizados)
           -> deliveryEventsTopicName: topico para onde a mensagem sera enviada */
        integrationEventPublisher.publish( event, event.getDeliveryId().toString(), deliveryEventsTopicName );
    }

}