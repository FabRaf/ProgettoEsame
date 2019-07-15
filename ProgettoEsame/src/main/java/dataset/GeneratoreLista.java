package dataset;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;

//import operazioni.Filter;
//import operazioni.FilterUtils;
import dataset.StatoMembro;

public class GeneratoreLista /*implements Filter*/ {
	//private FilterUtils<StatoMembro> utils;
	ArrayList<StatoMembro> lista = new ArrayList<>(); //creazione di un ArrayList di oggetti di tipo StatoMembro
	
	public GeneratoreLista() throws FileNotFoundException, IOException {
		//super();
		String line;
		BufferedReader br = new BufferedReader(new FileReader("dataset.csv")); //esegue la lettura bufferizzata del dataset
		br.readLine(); // legge a vuoto la prima riga poiché contiene le intestazioni delle colone
		while((line = br.readLine()) != null) { //legge le successive righe fino alla fine del file
			String[] str = line.split(",", 7); //separa ogni riga sulla base del carattere ',' e inserisce le sottostringhe ottenute in un array
			StatoMembro stato = new StatoMembro(str[0], str[1], str[2], Double.parseDouble(str[3]), Double.parseDouble(str[4]), Double.parseDouble(str[5]), Double.parseDouble(str[6])); //crea un oggetto StatoMembro con i campi contenuti nell'array 
			lista.add(stato); //aggiunge l'oggetto appena creato nella lista 'lista'
		}
		br.close(); //chiude il buffer di lettura
	}

	public ArrayList<StatoMembro> getLista() {
		return lista;
	}
	
	/*public ArrayList<StatoMembro> filterField(String fieldName, String operator, Object value) {
		return (ArrayList<StatoMembro>) utils.select(this.getLista(), fieldName, operator, value);
	}*/
}
	