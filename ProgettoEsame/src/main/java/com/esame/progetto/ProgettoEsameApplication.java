package com.esame.progetto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProgettoEsameApplication {

	public static void main(String[] args) {
		new Download();
		SpringApplication.run(ProgettoEsameApplication.class, args);
	}
}
