package com.esame.progetto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author Fabio Raffaeli
 *
 */

@SpringBootApplication
public class ProgettoEsameApplication {
	
	/**Chiama il costruttore della classe che si occupa del download del dataset e avvia il server web locale sulla porta 8080 ricorrendo al framework
	 * Spring
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Download(); //esegue il costruttore della classe Download
		SpringApplication.run(ProgettoEsameApplication.class, args); //avvia il server locale
	}
}