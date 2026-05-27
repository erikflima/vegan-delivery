package com.eriklima.vegandelivery.delivery.tracking.infrastructure.http.client;
import com.eriklima.vegandelivery.delivery.tracking.domain.service.CourierPayoutCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceHttpImpl implements CourierPayoutCalculationService {


    private final CourierAPIClient courierAPIClient;


    @Override
    public BigDecimal calculatePayout( Double distanceInKm ) {

        //Aqui eh feito uma requisicao para o endpoint do projeto/api "Courier-Management". Ou seja, vai ser chamado um endpoint de outro projeto.
        var courierPayoutResultModel = courierAPIClient.payoutCalculation( new CourierPayoutCalculationInput( distanceInKm ) );

        return courierPayoutResultModel.getPayoutFee();
    }
}