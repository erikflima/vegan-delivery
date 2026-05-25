package com.eriklima.vegandelivery.serviceregistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer// Habilita esta aplicação como um servidor Eureka (Service Registry), responsável por registrar e permitir que microserviços se descubram automaticamente na arquitetura.
public class ServiceRegistryApplication {

	public static void main(String[] args) {

		SpringApplication.run(ServiceRegistryApplication.class, args);

	}

}
