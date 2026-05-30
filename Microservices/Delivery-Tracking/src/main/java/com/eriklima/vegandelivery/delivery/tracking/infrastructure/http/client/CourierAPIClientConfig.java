package com.eriklima.vegandelivery.delivery.tracking.infrastructure.http.client;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class CourierAPIClientConfig { //Classe responsável por configurar automaticamente um cliente HTTP para comunicacao com a API do projeto "Courier Management"


    @Bean
    @LoadBalanced //Habilita balanceamento de carga e descoberta automatica via Eureka do Spring Clound. Ou seja, esse "@LoadBalanced" so funciona pq tenha a dependencia do Spring cloud eureka no projeto.
    public RestClient.Builder loadBalacendRestClientBuilder() { //Metodo responsável por criar um RestClient.Builder com Load Balancer.

        //Retorna um builder de cliente HTTP que podera usar nomes de serviço ao inves de IP.
        return RestClient.builder();

    }


    @Bean // Metodo responsavel por criar automaticamente um client HTTP do Courier
    public CourierAPIClient courierAPIClient( RestClient.Builder builder ) {

        //Forma antiga: chamava diretamente um endereço fixo (IP/porta)
        //RestClient restClient = builder.baseUrl("http://localhost:8081")
        //        .requestFactory( generateClientRequestFactory() )
        //        .build();

        //Nova forma: usa o nome do microserviço registrado no Eureka do Spring cloud. O LoadBalancer + Eureka resolvem automaticamente qual instancia chamar.
        RestClient restClient = builder.baseUrl( "http://courier-management" )
                .requestFactory( generateClientRequestFactory() )
                .build();

        RestClientAdapter adapter = RestClientAdapter.create( restClient );

        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();

        //Gera automaticamente a implementacao da interface "CourierAPIClient" permitindo chamar endpoints HTTP como se fossem metodos Java.
        return proxyFactory.createClient( CourierAPIClient.class );
    }



    //Metodo responsavel por configurar os tempos limite (timeout) do RestClient, implementando o "Timeout Pattern" para evitar que a aplicacao fique esperando indefinidamente respostas lentas ou servicos indisponiveis.
    private ClientHttpRequestFactory generateClientRequestFactory() {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory(); // Cria a fábrica responsável por configurar requisições HTTP do RestClient

        factory.setConnectTimeout( Duration.ofMillis(10) ); //Define o tempo maximo (10ms) para conseguir estabelecer conexao com outro servico. Se nao conectar nesse tempo, a requisicao falha por timeout
        factory.setReadTimeout( Duration.ofMillis(200) );   //Define o tempo máximo (200ms) para esperar a resposta apos conectar. Se o servico demorar mais que isso para responder, ocorre timeout.

        return factory;
    }

}