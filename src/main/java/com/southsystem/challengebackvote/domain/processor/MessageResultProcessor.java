package com.southsystem.challengebackvote.domain.processor;

import com.southsystem.challengebackvote.domain.model.internal.MessagingResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageResultProcessor {

    private String kafkaTopic;
    private final KafkaTemplate<String, MessagingResult> kafkaTemplate;

    public MessageResultProcessor(@Value("${generalconfig.result-kafka-topic}") String kafkaTopic,
                                  KafkaTemplate<String, MessagingResult> kafkaTemplate) {
        this.kafkaTopic = kafkaTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(MessagingResult messagingResult){
        kafkaTemplate.send(kafkaTopic, messagingResult).addCallback(
                success -> log.info("Message send: " + success.getProducerRecord().value()),
                failure -> log.error("Message fail: " + failure.getMessage())
        );
    }
}
