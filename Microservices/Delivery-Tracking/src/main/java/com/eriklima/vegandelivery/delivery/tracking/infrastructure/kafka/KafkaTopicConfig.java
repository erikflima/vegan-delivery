package com.eriklima.vegandelivery.delivery.tracking.infrastructure.kafka;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


@Configuration
// Classe responsável por configurar e criar tópicos Kafka automaticamente
public class KafkaTopicConfig {


    // Constante com o nome do tópico Kafka de eventos de delivery
    public static final String deliveryEventsTopicName = "deliveries.v1.events";


    @Bean
    // Metodo responsável por configurar/criar o topico Kafka
    public NewTopic deliveryEventsTopic() {

        return TopicBuilder.name(deliveryEventsTopicName)  // Define o nome do tópico Kafka
                           .partitions(3)                  // Define que o tópico terá 3 partições para paralelismo
                           .replicas(1)                    // Define que o tópico terá apenas 1 réplica
                           .build();                       // Finaliza e cria a configuração do tópico Kafka

    }

}