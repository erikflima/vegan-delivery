package com.eriklima.vegandelivery.delivery.tracking.infrastructure.event;
import com.eriklima.vegandelivery.delivery.tracking.domain.event.DeliveryFulfilledEvent;
import com.eriklima.vegandelivery.delivery.tracking.domain.event.DeliveryPickUpEvent;
import com.eriklima.vegandelivery.delivery.tracking.domain.event.DeliveryPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j // Lombok cria automaticamente um objeto "log".
@RequiredArgsConstructor
// Classe responsável por escutar e reagir aos eventos de domínio(classe) Delivery.
public class DeliveryDomainEventHandler {


    @EventListener // Diz ao Spring "quando um evento  do tipo "DeliveryPlacedEvent" acontecer, execute este método automaticamente".
    public void handle( DeliveryPlacedEvent event ) {

        log.info("\n");
        log.info("--------------------------------------------- ");
        log.info("Evento ocorrido!");
        log.info("Tipo de evento: " + event.getClass().getSimpleName() );
        log.info("Id da delivery: " + event.getDeliveryId() );
        log.info("Momento que o evento ocorreu: " + event.getOccurredAt() );
        log.info("--------------------------------------------- \n");
    }


    @EventListener // Diz ao Spring "quando um evento  do tipo "DeliveryPickUpEvent" acontecer, execute este método automaticamente".
    public void handle( DeliveryPickUpEvent event ) {

        log.info("\n");
        log.info("--------------------------------------------- ");
        log.info("Evento ocorrido!");
        log.info("Tipo de evento: " + event.getClass().getSimpleName() );
        log.info("Id da delivery: " + event.getDeliveryId() );
        log.info("Momento que o evento ocorreu: " + event.getOccurredAt() );
        log.info("--------------------------------------------- \n");
    }


    @EventListener // Diz ao Spring "quando um evento  do tipo "DeliveryFulfilledEvent" acontecer, execute este método automaticamente".
    public void handle( DeliveryFulfilledEvent event ) {

        log.info("\n");
        log.info("--------------------------------------------- ");
        log.info("Evento ocorrido!");
        log.info("Tipo de evento: " + event.getClass().getSimpleName() );
        log.info("Id da delivery: " + event.getDeliveryId() );
        log.info("Momento que o evento ocorreu: " + event.getOccurredAt() );
        log.info("--------------------------------------------- \n");
    }

}