package dataset;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;

import operazioni.Filter;
import operazioni.FilterUtils;
import dataset.StatoMembro;

public class GeneratoreLista implements Filter {
	private FilterUtils<StatoMembro> utils;
	ArrayList<StatoMembro> lista = new ArrayList<>();
	
	public GeneratoreLista() throws FileNotFoundException, IOException {
		super();
		String line;
		BufferedReader br = new BufferedReader(new FileReader("dataset.csv"));
		br.readLine();
		while((line = br.readLine()) != null) {
			String[] str = line.split(",", 7);
			StatoMembro stato = new StatoMembro(str[0], str[1], str[2], Double.parseDouble(str[3]), Double.parseDouble(str[4]), Double.parseDouble(str[5]), Double.parseDouble(str[6]));
			lista.add(stato);
		}
		br.close();
	}

	public ArrayList<StatoMembro> getLista() {
		return lista;
	}
	
	public ArrayList<StatoMembro> filterField(String fieldName, String operator, Object value) {
		return (ArrayList<StatoMembro>) utils.select(this.getLista(), fieldName, operator, value);
	}
}
	