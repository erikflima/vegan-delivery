package com.eriklima.vegandelivery.courier.management;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // Habilita este microserviço como um cliente de descoberta de serviços, permitindo que ele se registre no Eureka e descubra outros microserviços automaticamente.
public class CourierManagementApplication {

	public static void main(String[] args) {

		SpringApplication.run(CourierManagementApplication.class, args);
	}

}