package com.esame.progetto;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

/**
 * 
 * @author Fabio Raffaeli
 *
 */

public class Download {
	private File dataset = new File("dataset.csv"); //file che conterrà il dataset
	private String str = "http://data.europa.eu/euodp/data/api/3/action/package_show?id=funds-absorption-rate"; //indirizzo del JSON
	
	/**
	 * Effettua il download del dataset tramite l'URL ottenuto dopo opportuna decodifica del JSON reperibile all'indirizzo assegnato
	 */
	public Download() {
		try {
			String data = "";
			URL url = new URL(str); //crea un oggetto URL sulla base dell'indirizzo contenuto in "str"
			URLConnection openConnection = url.openConnection(); //esegue la connessione a "url"
			openConnection.addRequestProperty("User-Agent", "Chrome");
			InputStream in = openConnection.getInputStream(); //crea un flusso di input a partire dalla connessione
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(in));	//esegue la lettura bufferizzata del flusso
				String line = "";
				while((line = br.readLine()) != null) { //legge riga per riga finché non arriva la fine del file
					data+=line; //memorizza le righe lette in una stringa
				}
			}			
			finally {
				in.close();	//chiude il flusso di input, anche se si verificano eccezioni
			}
			JSONObject text = (JSONObject) JSONValue.parseWithException(data); //genera un oggetto JSON sulla base della stringa "data"
			JSONObject result = (JSONObject) text.get("result"); //genera un oggetto JSON con il contenuto del campo "result" di "text"
			JSONArray resources = (JSONArray) result.get("resources"); // genera un array JSON corrispondente al campo "resources" di "result"
			
			for(Object x : resources) { // scorre l'array
				if(x instanceof JSONObject) { //verifica che l'oggetto x sia convertibile nel tipo JSONObject
					JSONObject o = (JSONObject) x; //opera un cast di tipo di x a JSONObject
					URL url1 = new URL((String) o.get("url")); //estrae il campo "url" di "o"
					String formato = (String) o.get("format"); //estrae il campo "format" di "o"
					if(formato.equals("http://publications.europa.eu/resource/authority/file-type/CSV")) { //verifica che il formato sia quello corretto
						FileUtils.copyURLToFile(url1, dataset); // scarica il file in "dataset"
					}
				}
			}			
		}
		catch(IOException | ParseException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}