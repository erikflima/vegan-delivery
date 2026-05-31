package com.eriklima.vegandelivery.delivery.tracking.infrastructure.resilence4j;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import io.github.resilience4j.retry.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Resilence4jCircuitBreakerEventConsumer implements RegistryEventConsumer<CircuitBreaker> {


    @Override
    // Metodo executado automaticamente quando uma configuração CircuitBreaker é adicionada no Registry do Resilience4j.
    public void onEntryAddedEvent( EntryAddedEvent<CircuitBreaker> entryAddedEvent ) {

        entryAddedEvent.getAddedEntry().getEventPublisher()

                // Escuta eventos do CircuitBreaker e exibe logs detalhados quando acontecerem
                .onEvent( event -> {

                    log.info("\n------------------------------------------------------------");

                    log.info("Metodo onEntryAddedEvent executado!");
                    log.info("Este metodo escuta automaticamente eventos do CircuitBreaker");
                    log.info("e registra no log quando houver nova tentativa,");
                    log.info("falha, sucesso ou mudança no comportamento do CircuitBreaker.");

                    log.info("");

                    log.info("Evento CircuitBreaker recebido!");
                    log.info("Nome do CircuitBreaker: {}", entryAddedEvent.getAddedEntry().getName() );
                    log.info("Tipo do evento: {}", event.getEventType() );
                    log.info("Detalhes do evento: {}", event.toString() );

                    log.info("------------------------------------------------------------\n");

                });

    }


    @Override //Metodo executado automaticamente quando uma configuracao CircuitBreaker é removida do Registry do Resilience4j.
    public void onEntryRemovedEvent( EntryRemovedEvent<CircuitBreaker> entryRemoveEvent ) {

    }


    @Override //Metodo executado automaticamente quando uma configuracao CircuitBreaker existente é substituída no Registry do Resilience4j.
    public void onEntryReplacedEvent( EntryReplacedEvent<CircuitBreaker> entryReplacedEvent ) {

    }

}