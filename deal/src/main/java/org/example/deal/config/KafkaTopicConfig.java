package org.example.deal.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.example.moduledto.dto.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic finishRegistrationTopic() {
        return TopicBuilder.name("finish-registration").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic createDocumentsTopic() {
        return TopicBuilder.name("create-documents").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic sendDocumentsTopic() {
        return TopicBuilder.name("send-documents").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic sendSesTopic() {
        return TopicBuilder.name("send-ses").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic creditIssuedTopic() {
        return TopicBuilder.name("credit-issued").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic statementDeniedTopic() {
        return TopicBuilder.name("statement-denied").partitions(1).replicas(1).build();
    }

    @Bean
    public KafkaTemplate<String, EmailMessage> kafkaTemplate(ProducerFactory<String, EmailMessage> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
