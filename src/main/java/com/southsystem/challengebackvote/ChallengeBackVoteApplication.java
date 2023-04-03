package com.southsystem.challengebackvote;

import com.google.gson.Gson;
import feign.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class ChallengeBackVoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeBackVoteApplication.class, args);
	}

	@Bean
	public Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

	@Bean
	public Gson gson() {
		return new Gson();
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
