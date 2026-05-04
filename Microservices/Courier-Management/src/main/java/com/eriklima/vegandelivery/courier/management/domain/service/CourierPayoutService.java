package com.eriklima.vegandelivery.courier.management.domain.service;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CourierPayoutService {

    public BigDecimal calculate(Double distanceInKm) {

        BigDecimal baseValue = new BigDecimal( "10" );

        BigDecimal distance = new BigDecimal( distanceInKm );

        BigDecimal result = baseValue.multiply( distance );

        return result.setScale(2, RoundingMode.HALF_EVEN);

    }
}