package com.southsystem.challengebackvote.application.controller;

import com.southsystem.challengebackvote.domain.service.MessagingService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/v1/messaging_result")
@Api(value = "agenda")
@AllArgsConstructor
public class MessagingResultController {

    @Autowired
    private MessagingService messagingService;

    @GetMapping("/{section_id}")
    public ResponseEntity<?> getMessagingResult(@PathVariable("section_id") String sectionId){
        var result = this.messagingService.getMessagingResult(sectionId, false);
        return ok(result);
    }
}
