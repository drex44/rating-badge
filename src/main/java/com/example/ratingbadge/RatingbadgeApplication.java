package com.example.ratingbadge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.ratingbadge.repository")
public class RatingbadgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatingbadgeApplication.class, args);
	}

}
