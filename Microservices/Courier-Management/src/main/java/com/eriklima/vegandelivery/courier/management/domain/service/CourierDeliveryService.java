package com.eriklima.vegandelivery.courier.management.domain.service;
import com.eriklima.vegandelivery.courier.management.domain.model.Courier;
import com.eriklima.vegandelivery.courier.management.domain.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CourierDeliveryService {

    private final CourierRepository courierRepository;


    public void assign( UUID deliveryId ) {

        //Busca um entregador que fez a entrega por ultimo, pois teoricamente ele esta livre para proxima entrega. Exemplo de regra de neogcio.
        Courier courier = courierRepository.findTop1ByOrderByLastFulfilledDeliveryAtAsc().orElseThrow();

        //Artibui a entrega para o entregador.
        courier.assign( deliveryId );

        courierRepository.saveAndFlush( courier );

        log.info("\n---------------------------------------------");
        log.info("Courier assigned to delivery! | Entrega atribuída ao entregador!");
        log.info("Courier ID: {}", courier.getId());
        log.info("Delivery ID: {}", deliveryId);
        log.info("---------------------------------------------\n");
    }


    public void fulfill( UUID deliveryId ) {

        //Busca o entregador da entrega que esta pendente.
        Courier courier = courierRepository.findByPendingDeliveries_id( deliveryId ).orElseThrow();

        courier.fulfill( deliveryId );

        courierRepository.saveAndFlush( courier );

        log.info("\n---------------------------------------------");
        log.info("Courier fulfilled the delivery! Entrega concluída pelo entregador!");
        log.info("Courier ID: {}", courier.getId());
        log.info("Delivery ID: {}", deliveryId);
        log.info("---------------------------------------------\n");
    }

}