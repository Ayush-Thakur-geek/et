package com.EaseTravels.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EtApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtApplication.class, args);
	}

}
