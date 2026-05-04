package com.eriklima.vegandelivery.delivery.tracking.infrastructure.fake;
import com.eriklima.vegandelivery.delivery.tracking.domain.model.ContactPoint;
import com.eriklima.vegandelivery.delivery.tracking.domain.service.DeliveryEstimate;
import com.eriklima.vegandelivery.delivery.tracking.domain.service.DeliveryTimeEstimationService;
import org.springframework.stereotype.Service;
import java.time.Duration;


@Service
public class DeliveryTimeEstimationServiceFakeImpl implements DeliveryTimeEstimationService {


    @Override
    public DeliveryEstimate estimate( ContactPoint sender, ContactPoint receiver ) {

        //Por enquanto retornando um valor fixo, ficticio para teste.
        return new DeliveryEstimate( Duration.ofHours(3), 3.1 );

    }

}