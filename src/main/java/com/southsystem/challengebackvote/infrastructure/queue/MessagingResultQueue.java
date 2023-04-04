package com.southsystem.challengebackvote.infrastructure.queue;

import com.southsystem.challengebackvote.domain.model.internal.MessagingResult;
import com.southsystem.challengebackvote.domain.model.internal.dto.MessagingResultDto;
import com.southsystem.challengebackvote.domain.service.MessagingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;;

@Slf4j
@Component
@AllArgsConstructor
public class MessagingResultQueue {

    @Autowired
    private MessagingService messagingService;

    @KafkaListener(topics = "${generalconfig.result-kafka-topic}", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "messagingResultListenerContainerFactory")
    public void listenTopicCar(ConsumerRecord<String, MessagingResult> messagingResult){
        this.messagingService.save(MessagingResultDto.builder().voteResult(messagingResult.value().getVoteResult())
                .agenda(messagingResult.value().getAgenda()).build());
    }
}
