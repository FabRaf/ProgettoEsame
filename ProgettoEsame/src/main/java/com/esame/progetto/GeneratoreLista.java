package com.esame.progetto;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;

public class GeneratoreLista {
	ArrayList<StatoMembro> lista = new ArrayList<>();
	
	public GeneratoreLista() throws FileNotFoundException, IOException {
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
	
}