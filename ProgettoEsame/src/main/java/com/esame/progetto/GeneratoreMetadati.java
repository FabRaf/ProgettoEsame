package com.esame.progetto;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class GeneratoreMetadati {
	private JSONArray metadati = new JSONArray();
	public GeneratoreMetadati() throws IOException, ClassNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader("dataset.csv"));
		Class c = Class.forName("com.esame.progetto.StatoMembro");
		Constructor costruttori[] = c.getConstructors();
		Field attributi[] = c.getDeclaredFields();
		Class tipiAttributi[] = costruttori[0].getParameterTypes();
		String line = br.readLine();
		String valori[] = line.split(";", 7);
		for(int i=0; i<attributi.length; i++) {
			JSONObject obj = new JSONObject();
			obj.put("alias", attributi[i].getName());
			obj.put("sourceField", valori[i]);
			obj.put("type", tipiAttributi[i].getName());
			String tipo = (String) obj.get("type");
			if(tipo.equals("java.lang.String")) obj.put("type", "String");
			metadati.add(obj);
		}
	}
	
	public JSONArray getMetadati() {
		return metadati;
	}	
}
