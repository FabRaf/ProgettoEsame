package operazioni;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.ArrayList;

import dataset.GeneratoreLista;
import dataset.StatoMembro;

/**
 * 
 * @author Fabio Raffaeli
 *
 */

public class Statistiche {
	private JSONArray statistiche = new JSONArray();
	private JSONObject stat = new JSONObject();
	
	/**
	 * Genera le statistiche relative al campo che riceve come parametro: elementi unici e numero di occorrenze se si tratta di un campo di tipo String,
	 * media, massimo, minimo, deviazione standard, somma, conteggio se si tratta di un campo di tipo numerico. Memorizza tali statistiche in un 
	 * oggetto JSON, che viene poi inserito in un array JSON
	 * 
	 * @param nomeCampo nome del campo del quale vengono richieste le statistiche
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Statistiche(String nomeCampo) throws FileNotFoundException, IOException, ClassNotFoundException {
		boolean flag = false;
		GeneratoreMetadati gm = new GeneratoreMetadati();
		JSONArray metadati = gm.getMetadati();
		String campo="", tipo="";
		
		if (nomeCampo!=null) {
			for (int i = 0; i < metadati.size(); i++) { //scorre il JSONArray metadati
				JSONObject jsonobj = (JSONObject) metadati.get(i); //esamina l'ogetto di indice i
				campo = (String) jsonobj.get("alias"); //memorizza il campo "alias"
				tipo = (String) jsonobj.get("type"); //memorizza il campo "type"
				if (campo.equals(nomeCampo)) { //controlla che alias sia uguale al nome del campo desiderato
					flag = true; //imposta a true il flag
					break; //esce
				}
			}
			if (flag == true) {
				stat.put("field", nomeCampo); //inserisce nell'oggetto "stat" una coppia chiave-valore indicante il nome del campo
				GeneratoreLista gl = new GeneratoreLista();
				ArrayList<StatoMembro> lista = gl.getLista(); //ottiene la lista completa dei dati

				if (tipo.equals("String")) { //se il campo è di tipo String
					ArrayList<String> elementiUnici = new ArrayList<String>();
					for (StatoMembro sm : lista) { //scorre la lista di dati
						flag = false; //imposta a false il flag
						try {
							Method m = sm.getClass().getMethod(
									"get" + nomeCampo.substring(0, 1).toUpperCase() + nomeCampo.substring(1), null); //recupera il metodo getter corrispondente al campo desiderato
							try {
								String valoreCampo = (String) m.invoke(sm); //invoca tale metodo sull'oggetto "sm"
								for (String str : elementiUnici) { //scorre la lista degli elementi unici
									if (str.equals(valoreCampo)) //se nella lista è presente il campo
										flag = true; //imposta a true il flag
								}
								if (flag == false) //se flag ha valore false
									elementiUnici.add(valoreCampo); //aggiunge alla lista degli elementi unici il campo 
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						}
					}
					for (String str : elementiUnici) { //scorre la lista degli elementi unici, a questo punto completa
						int cont = 0;
						for (StatoMembro sm : lista) { //scorre la lista dei dati
							try {
								Method m = sm.getClass().getMethod(
										"get" + nomeCampo.substring(0, 1).toUpperCase() + nomeCampo.substring(1), null); //recupera il metodo getter corrispondente al campo desiderato
								try {
									String valoreCampo = (String) m.invoke(sm); //invoca tale metodo sull'oggetto sm
									if (str.equals(valoreCampo)) //se nella lista è presente il campo
										cont++; //incrementa di 1 il contatore
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
							} catch (SecurityException e) {
								e.printStackTrace();
							}
						}
						stat.put(str, cont); //inserisce nell'oggetto stat una coppia chiave-valore indicante le occorenze degli elementi unici
					}
				}

				else if (tipo.equals("double")) { //se il campo è di tipo double
					double somma = 0.0; //inizializza la somma
					double max = 0.0, min = 9999999999.0; //inizializza il massimo e il minimo
					for (StatoMembro sm : lista) { //scorre la lista di dati
						try {
							Method m = sm.getClass().getMethod(
									"get" + nomeCampo.substring(0, 1).toUpperCase() + nomeCampo.substring(1), null); //recupera il metodo getter corrispondente al campo desiderato
							try {
								double valoreCampo = (double) m.invoke(sm); //invoca tale metodo sull'oggetto "sm"
								somma += valoreCampo; //incrementa la somma di una quantità pari al valore assunto dal campo

								if (valoreCampo > max) //se il valore del campo è superiore al massimo
									max = valoreCampo; //aggiorna il massimo
								else if (valoreCampo < min) //se il valore del campo è inferiore al minimo
									min = valoreCampo; //aggiorna il minimo

							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						}
					}
					double media = somma / lista.size(); //calcola la media

					double dev = 0.0; //inizializza la deviazione standard
					for (StatoMembro sm : lista) {
						try {
							Method m = sm.getClass().getMethod(
									"get" + nomeCampo.substring(0, 1).toUpperCase() + nomeCampo.substring(1), null); //recupera il metodo getter corrispondente al campo desiderato
							try {
								double valoreCampo = (double) m.invoke(sm); //invoca tale metodo sull'oggetto sm
								double scarto = Math.pow(valoreCampo - media, 2); //calcola lo scarto quadratico
								dev += scarto; //somma gli scarti
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						}
					}
					dev = Math.sqrt(dev / lista.size()); //calcola la deviazione standard

					stat.put("avg", Math.round(media * 100) / 100.0); //inserisce nell'oggetto "stat" una coppia chiave-valore indicante la media (arrotondata alla seconda cifra decimale)
					stat.put("min", min); //inserisce nell'oggetto "stat" una coppia chiave-valore indicante il minimo
					stat.put("max", max); //inserisce nell'oggetto "stat" una coppia chiave-valore indicante il massimo
					stat.put("dev std", Math.round(dev * 100) / 100.0); //inserisce nell'oggetto "stat" una coppia chiave-valore indicante la deviazione standard (arrotondata alla seconda cifra decimale)
					stat.put("sum", Math.round(somma * 100) / 100.0); //inserisce nell'oggetto "stat" una coppia chiave-valore indicante la somma (arrotondata alla seconda cifra decimale)
					stat.put("count", lista.size()); //inserisce nell'oggetto "stat" una coppia chiave-valore indicante il conteggio, cioè il numero di elementi della lista
				}
			} else {
				stat.put("errore", "campo inesistente"); //inserisce nell'oggetto un messaggio di errore nel caso in cui il campo specificato sia errato
			}
		}
		else stat.put("errore", "specificare un parametro di tipo 'field'"); //inserisce nell'oggetto un messaggio di errore nel caso in cui il parametro sia errato
		statistiche.add(stat); //inserisce l'oggetto "stat" nell'array JSON "statistiche"
	}
	
	/**
	 * 
	 * @return le statistiche sotto forma di array JSON
	 */
	public JSONArray getStatistiche() {
		return statistiche;
	}
}

