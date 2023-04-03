package com.southsystem.challengebackvote.infrastructure.queue;

import org.apache.kafka.clients.consumer.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class VoteResultQueue {

    private final KafkaTemplate kafkaTemplate;
    Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    public VoteResultQueue(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "${generalconfig.result-kafka-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) {
        logger.info(message);
    }

}
