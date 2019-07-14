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

@RestController
public class Controller {	
	@RequestMapping(path = "/data", method = RequestMethod.GET, headers = "Accept=application/json; charset=utf-8") //definisce la struttura della richiesta dei dati
	public ArrayList<StatoMembro> GetData(@RequestParam(value="filter", required=false) String filter/*, @RequestBody String body*/) throws FileNotFoundException, IOException {
		GeneratoreLista lista = new GeneratoreLista();
	//	if (filter==null) {
			return lista.getLista();
	//	}
	//	return new ArrayList<StatoMembro>();
	}
	
	@RequestMapping(path = "/metadata", method = RequestMethod.GET, headers = "Accept=application/json") //definisce la struttura della richiesta dei metadati
	public JSONArray GetMetadata() throws FileNotFoundException, IOException, ClassNotFoundException {
		GeneratoreMetadati metadati = new GeneratoreMetadati();
		return metadati.getMetadati();
	}

	@RequestMapping(path = "/stats", method = RequestMethod.GET, headers = "Accept=application/json; charset=utf-8") //definisce la struttura della richiesta delle statistiche
	public JSONArray elemento(@RequestParam(value="field", required = false) String campo) throws FileNotFoundException, ClassNotFoundException, IOException {
		Statistiche elementiUnici = new Statistiche(campo);
		return elementiUnici.getStatistiche();
	}
}


