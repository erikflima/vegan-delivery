package com.eriklima.vegandelivery.courier.management.domain.repository;
import com.eriklima.vegandelivery.courier.management.domain.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;


public interface CourierRepository extends JpaRepository<Courier, UUID> {

    //Busca um entregador que fez a entrega por ultimo.
    Optional<Courier> findTop1ByOrderByLastFulfilledDeliveryAtAsc();

    Optional<Courier> findByPendingDeliveries_id( UUID deliveryId );

}