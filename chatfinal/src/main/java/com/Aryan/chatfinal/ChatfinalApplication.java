package com.Aryan.chatfinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;


@SpringBootApplication
@EnableCaching
@EnableKafka
@ComponentScan(basePackages = "com.Aryan.chatfinal")

@EntityScan(basePackages = "com.Aryan.chatfinal.model")

public class ChatfinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatfinalApplication.class, args);
	}

}
