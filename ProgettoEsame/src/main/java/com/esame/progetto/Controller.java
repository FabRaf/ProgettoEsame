package com.esame.progetto;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.json.simple.JSONArray;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.ArrayList;

import dataset.StatoMembro;
import dataset.GeneratoreLista;
import operazioni.GeneratoreMetadati;
import operazioni.Statistiche;

/**
 * 
 * @author Fabio Raffaeli
 *
 */

@RestController
public class Controller {	
	/**
	 * Restituisce la lista completa dei dati del dataset
	 * 
	 * @return lista dei dati
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping(path = "/data", method = RequestMethod.GET, headers = "Accept=application/json; charset=utf-8") //definisce la struttura della richiesta dei dati
	public ArrayList<StatoMembro> GetData() throws FileNotFoundException, IOException {
		GeneratoreLista lista = new GeneratoreLista();
			return lista.getLista();
	}
	
	/**
	 * Restituisce la lista dei metadati
	 * 
	 * @return lista dei metadati
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@RequestMapping(path = "/metadata", method = RequestMethod.GET, headers = "Accept=application/json") //definisce la struttura della richiesta dei metadati
	public JSONArray GetMetadata() throws FileNotFoundException, IOException, ClassNotFoundException {
		GeneratoreMetadati metadati = new GeneratoreMetadati();
		return metadati.getMetadati();
	}
	
	/**
	 * Restituisce le statistiche sul campo specificato: elementi unici e numero di occorrenze se si tratta di un campo di tipo String,
	 * media, massimo, minimo, deviazione standard, somma, conteggio se si tratta di un campo di tipo numerico
	 * 
	 * @param campo campo del quale si vogliono conoscere le statistiche
	 * @return statistiche del campo specificato
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@RequestMapping(path = "/stats", method = RequestMethod.GET, headers = "Accept=application/json; charset=utf-8") //definisce la struttura della richiesta delle statistiche
	public JSONArray elemento(@RequestParam(value="field", required = false) String campo) throws FileNotFoundException, ClassNotFoundException, IOException {
		Statistiche elementiUnici = new Statistiche(campo);
		return elementiUnici.getStatistiche();
	}
}


