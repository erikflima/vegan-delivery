package com.eriklima.vegandelivery.delivery.tracking.domain.service;
import com.eriklima.vegandelivery.delivery.tracking.api.model.ContactPointInput;
import com.eriklima.vegandelivery.delivery.tracking.api.model.DeliveryInput;
import com.eriklima.vegandelivery.delivery.tracking.api.model.ItemInput;
import com.eriklima.vegandelivery.delivery.tracking.domain.exception.DomainException;
import com.eriklima.vegandelivery.delivery.tracking.domain.model.ContactPoint;
import com.eriklima.vegandelivery.delivery.tracking.domain.model.Delivery;
import com.eriklima.vegandelivery.delivery.tracking.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class DeliveryPreparationService {


    private final DeliveryRepository deliveryRepository;

    private final DeliveryTimeEstimationService deliveryTimeEstimationService;

    private final CourierPayoutCalculationService courierPayoutCalculationService;


    @Transactional
    public Delivery draft( DeliveryInput input ) {

        Delivery delivery = Delivery.draft();
        handlePreparation(input, delivery);

        return deliveryRepository.saveAndFlush(delivery);
    }


    @Transactional
    public Delivery edit(UUID deliveryId, DeliveryInput input) {

        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> new DomainException() );
        delivery.removeItems();
        handlePreparation( input, delivery );

        return deliveryRepository.saveAndFlush(delivery);
    }


    private void handlePreparation(DeliveryInput input, Delivery delivery) {

        ContactPointInput senderInput = input.getSender();
        ContactPointInput recipientInput = input.getRecipient();

        ContactPoint sender = ContactPoint.builder()
                                            .phone(senderInput.getPhone())
                                            .name(senderInput.getName())
                                            .complement(senderInput.getComplement())
                                            .number(senderInput.getNumber())
                                            .zipCode(senderInput.getZipCode())
                                            .street(senderInput.getStreet())
                                            .build();


        ContactPoint recipient = ContactPoint.builder()
                                            .phone(recipientInput.getPhone())
                                            .name(recipientInput.getName())
                                            .complement(recipientInput.getComplement())
                                            .number(recipientInput.getNumber())
                                            .zipCode(recipientInput.getZipCode())
                                            .street(recipientInput.getStreet())
                                            .build();


        DeliveryEstimate estimate = deliveryTimeEstimationService.estimate( sender, recipient); // Calculando o tempo e distancia da entrega.

        BigDecimal calculatedPayout = courierPayoutCalculationService.calculatePayout( estimate.getDistanceInKm() ); //Calculado do valor a ser pago para o entregador.

        BigDecimal distanceFee = calculateFee( estimate.getDistanceInKm() ); //Calculo da taxa de distancia da entrega.


        var preparationDetails = Delivery.PreparationDetails.builder()
                                            .recipient(recipient)
                                            .sender(sender)
                                            .expectedDeliveryTime(estimate.getEstimatedTime())
                                            .courierPayout(calculatedPayout)
                                            .distanceFee(distanceFee)
                                            .build();

        delivery.editPreparationDetails( preparationDetails );

        for ( ItemInput itemInput : input.getItems() ) {

            delivery.addItem( itemInput.getName(), itemInput.getQuantity() );
        }
    }


    private BigDecimal calculateFee( Double distanceInKm ) {

        BigDecimal baseValue = new BigDecimal("3");
        BigDecimal distance = new BigDecimal( distanceInKm );
        BigDecimal result = baseValue.multiply( distance );

        return result.setScale( 2, RoundingMode.HALF_EVEN );
    }

}