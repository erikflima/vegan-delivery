package com.eriklima.vegandelivery.delivery.tracking.domain.model;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class CourierIdInput {

    private UUID courierId;
}