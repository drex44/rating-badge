package com.example.ratingbadge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.ratingbadge.repository")
@EnableWebSocket
public class RatingbadgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatingbadgeApplication.class, args);
	}

}
