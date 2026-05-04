package com.eriklima.vegandelivery.delivery.tracking.domain.service;
import com.eriklima.vegandelivery.delivery.tracking.domain.model.ContactPoint;


public interface DeliveryTimeEstimationService {

    DeliveryEstimate estimate( ContactPoint sender, ContactPoint receiver );

}