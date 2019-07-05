package com.esame.progetto;

import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

@RestController
public class Controller {
	
	@RequestMapping(path="/data", method = RequestMethod.GET, headers = "Accept=application/json; charset=utf-8")
	public ArrayList<StatoMembro> GetData() throws FileNotFoundException, IOException {
		GeneratoreLista lista = new GeneratoreLista();
		return lista.getLista();
	}
	
	@RequestMapping(path = "/metadata", method = RequestMethod.GET, headers = "Accept=application/json")
	public JSONArray GetMetadata() throws FileNotFoundException, IOException, ClassNotFoundException {
		GeneratoreMetadati metadati = new GeneratoreMetadati();
		return metadati.getMetadati();
	}

	@RequestMapping(path = "/data/stats", method = RequestMethod.GET, headers = "Accept=application/json; charset=utf-8")
	public JSONObject elemento(@RequestParam(value="Field", required = false) String campo) throws FileNotFoundException, ClassNotFoundException, IOException {
		Statistiche elementiUnici = new Statistiche(campo);
		return elementiUnici.getElementiConOcc();
	}
}
