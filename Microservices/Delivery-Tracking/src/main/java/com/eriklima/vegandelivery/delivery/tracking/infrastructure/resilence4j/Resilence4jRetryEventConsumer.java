package com.eriklima.vegandelivery.delivery.tracking.infrastructure.resilence4j;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import io.github.resilience4j.retry.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Resilence4jRetryEventConsumer implements RegistryEventConsumer<Retry> {


    @Override
    // Metodo executado automaticamente quando uma configuração Retry é adicionada no Registry do Resilience4j.
    public void onEntryAddedEvent( EntryAddedEvent<Retry> entryAddedEvent ) {

        entryAddedEvent.getAddedEntry().getEventPublisher()

                // Escuta eventos do Retry e exibe logs detalhados quando acontecerem
                .onEvent( event -> {

                    log.info("\n------------------------------------------------------------");

                    log.info("Metodo onEntryAddedEvent executado!");
                    log.info("Este metodo escuta automaticamente eventos do Retry");
                    log.info("e registra no log quando houver nova tentativa,");
                    log.info("falha, sucesso ou mudança no comportamento do Retry.");

                    log.info("");

                    log.info("Evento Retry recebido!");
                    log.info("Nome do Retry: {}", entryAddedEvent.getAddedEntry().getName() );
                    log.info("Tipo do evento: {}", event.getEventType() );
                    log.info("Detalhes do evento: {}", event.toString() );

                    log.info("------------------------------------------------------------\n");

                });

    }


    @Override //Metodo executado automaticamente quando uma configuracao Retry é removida do Registry do Resilience4j.
    public void onEntryRemovedEvent( EntryRemovedEvent<Retry> entryRemoveEvent ) {

    }


    @Override //Metodo executado automaticamente quando uma configuracao Retry existente é substituída no Registry do Resilience4j.
    public void onEntryReplacedEvent( EntryReplacedEvent<Retry> entryReplacedEvent ) {

    }

}