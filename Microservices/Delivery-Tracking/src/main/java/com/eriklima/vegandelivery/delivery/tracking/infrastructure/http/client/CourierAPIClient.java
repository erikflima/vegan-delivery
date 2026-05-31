package com.eriklima.vegandelivery.delivery.tracking.infrastructure.http.client;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/v1/couriers")
public interface CourierAPIClient {

    @CircuitBreaker( name = "CircuitBreaker_CourierAPIClient_payoutCalculation" ) // Aplica automaticamente o padrão Circuit Breaker neste metodo, interrompendo temporariamente chamadas para a API do Courier quando muitas falhas consecutivas forem detectadas, usando a configuração chamada "CircuitBreaker_CourierAPIClient_payoutCalculation".
    @Retry( name = "Retry_CourierAPIClient_payoutCalculation" ) //Aplica automaticamente a estrategia de "Retry" neste metodo, tentando novamente a chamada da API do Courier caso ocorra falha temporaria, usando a configuracao chamada "Retry_CourierAPIClient_payoutCalculation".
    @PostExchange("/payout-calculation")
    CourierPayoutResultModel payoutCalculation( @RequestBody CourierPayoutCalculationInput input );
}
