package com.southsystem.challengebackvote.domain.processor;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoteResultProcessor {

    @Value("${generalconfig.result-kafka-topic}")
    private String kafkaTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public VoteResultProcessor(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String message) {
        kafkaTemplate.send(new ProducerRecord<>(kafkaTopic, UUID.randomUUID().toString(), message));
    }
}
