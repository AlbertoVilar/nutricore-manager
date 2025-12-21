package com.nutricore.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NutricoreManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NutricoreManagerApplication.class, args);
	}

}
