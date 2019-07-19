package dataset;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;

import dataset.StatoMembro;

/**
 * 
 * @author Fabio Raffaeli
 *
 */

public class GeneratoreLista  {
	private ArrayList<StatoMembro> lista = new ArrayList<>(); //creazione di un ArrayList di oggetti di tipo StatoMembro
	
	/**
	 * Legge il contenuto del dataset e genera la lista dei dati sulla base di esso
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public GeneratoreLista() throws FileNotFoundException, IOException {
		String line;
		BufferedReader br = new BufferedReader(new FileReader("dataset.csv")); //esegue la lettura bufferizzata del dataset
		br.readLine(); // legge a vuoto la prima riga poiché contiene le intestazioni delle colonne
		while((line = br.readLine()) != null) { //legge le successive righe fino alla fine del file
			String[] str = line.split(",", 7); //separa ogni riga sulla base del carattere "," e inserisce le sottostringhe ottenute in un array
			StatoMembro stato = new StatoMembro(str[0], str[1], str[2], Double.parseDouble(str[3]), Double.parseDouble(str[4]), Double.parseDouble(str[5]), Double.parseDouble(str[6])); //crea un oggetto StatoMembro con i campi contenuti nell'array 
			lista.add(stato); //aggiunge l'oggetto appena creato nella lista "lista"
		}
		br.close(); //chiude il buffer di lettura
	}
	
	/**
	 * 
	 * @return la lista dei dati sotto forma di ArrayList
	 */
	public ArrayList<StatoMembro> getLista() {
		return lista;
	}
}