package operazioni;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * 
 * @author Fabio Raffaeli
 *
 */

public class GeneratoreMetadati {
	private JSONArray metadati = new JSONArray();
	
	/**
	 * Genera la lista dei metadati relativi al dataset considerato. Per metadati si intendono le informazioni riguardanti: il nome dei campi del dataset,
	 * il nome e il tipo della variabile con cui essi vengono gestiti nell'applicazione
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public GeneratoreMetadati() throws IOException, ClassNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader("dataset.csv")); //crea un buffer di lettura dal file del dataset
		String line = br.readLine(); //legge la prima riga del dataset
		String valori[] = line.split(",", 7); //memorizza le intestazioni, cioè i nomi dei campi, in un array
		Class c = Class.forName("dataset.StatoMembro"); //crea un oggetto Class relativo alla classe StatoMembro
		Field attributi[] = c.getDeclaredFields(); //memorizza le variabili membro della classe
		Constructor costruttori[] = c.getConstructors(); //crea un oggetto Constructor relativo ai costruttori della classe StatoMembro
		Class tipiAttributi[] = costruttori[0].getParameterTypes(); //memorizza i tipi dei parametri del costruttore
		
		for(int i=0; i<attributi.length; i++) {
			JSONObject obj = new JSONObject();
			obj.put("alias", attributi[i].getName()); //inserisce nell'oggetto il nome della variabile membro
			obj.put("sourceField", valori[i]); //inserisce nell'oggetto l'intestazione			
			String tipo = tipiAttributi[i].getName(); //memorizza il tipo della variabile membro
			if(tipo.equals("java.lang.String")) tipo = "String"; //modifica il tipo java.lang.String in String
			obj.put("type", tipo); //inserisce nell'oggetto il tipo della variabile membro			
			metadati.add(obj); //aggiunge l'oggetto all'array 'metadati'
		}
		
		br.close(); //chiude il buffer di lettura
	}
	
	 /**
	  * 
	  * @return la lista di metadati sotto forma di array JSON
	  */
	public JSONArray getMetadati() {
		return metadati;
	}	
}
