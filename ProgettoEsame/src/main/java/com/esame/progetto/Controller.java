package com.esame.progetto;

import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import org.json.simple.JSONArray;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

@RestController
public class Controller {
	
	@RequestMapping(path="/data", method = RequestMethod.GET, headers = "Accept=application/json; charset = utf-8")
	public ArrayList<StatoMembro> GetData() throws FileNotFoundException, IOException {
		GeneratoreLista lista = new GeneratoreLista();
		return lista.getLista();
	}
	
	@RequestMapping(path = "/metadata", method = RequestMethod.GET, headers = "Accept=application/json")
		public JSONArray GetMetadata() throws FileNotFoundException, IOException, ClassNotFoundException {
		GeneratoreMetadati metadati = new GeneratoreMetadati();
		return metadati.getMetadati();
	}

}
