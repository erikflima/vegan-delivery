package com.eriklima.vegandelivery.delivery.tracking.infrastructure.http.client;
import com.eriklima.vegandelivery.delivery.tracking.domain.service.CourierPayoutCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceHttpImpl implements CourierPayoutCalculationService {


    private final CourierAPIClient courierAPIClient;


    @Override
    public BigDecimal calculatePayout( Double distanceInKm ) {

        try {
            //Aqui eh feito uma requisicao para o endpoint do projeto/api "Courier-Management". Ou seja, vai ser chamado um endpoint de outro projeto.
            var courierPayoutResultModel = courierAPIClient.payoutCalculation( new CourierPayoutCalculationInput( distanceInKm ) );

            return courierPayoutResultModel.getPayoutFee();
        }

        catch ( ResourceAccessException e ) {

            throw new GatewayTimeoutException( e );

        } catch ( HttpServerErrorException | IllegalArgumentException e ) {

            throw new BadGatewayException( e );
        }

    }

}