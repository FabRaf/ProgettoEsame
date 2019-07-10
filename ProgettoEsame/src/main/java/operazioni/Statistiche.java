package operazioni;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import java.util.Locale;

import java.util.ArrayList;

import dataset.GeneratoreLista;
import dataset.StatoMembro;

public class Statistiche {
	JSONArray statistiche = new JSONArray();
	JSONObject stat = new JSONObject();
	
	public Statistiche(String nomeCampo) throws FileNotFoundException, IOException, ClassNotFoundException {
		super(); //perché?
		boolean flag = false;
		GeneratoreMetadati gm = new GeneratoreMetadati();
		JSONArray metadati = gm.getMetadati();
		String campo="", tipo="";
				
		for(int i=0; i<metadati.size(); i++) {
			JSONObject jsonobj = (JSONObject) metadati.get(i);
			campo = (String) jsonobj.get("alias");
			tipo = (String) jsonobj.get("type");
			if(campo.equals(nomeCampo)) {
				flag = true;
				break;
			}
		}
		
		if(flag == true) {
			stat.put("field", nomeCampo);
			GeneratoreLista gl = new GeneratoreLista();
			ArrayList<StatoMembro> lista = gl.getLista();
			if (tipo.equals("String")) {
				ArrayList<String> elementiUnici = new ArrayList<String>();
				//elementiUnici.add("");
				for (StatoMembro sm : lista) {
					flag = false;
					try {
						Method m = sm.getClass().getMethod(
								"get" + nomeCampo.substring(0, 1).toUpperCase() + nomeCampo.substring(1), null);
						try {
							String valoreCampo = (String) m.invoke(sm);
							for (String str : elementiUnici) {
								if (str.equals(valoreCampo))
									flag = true;
							}
							if (flag == false)
								elementiUnici.add(valoreCampo);
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				for (String str : elementiUnici) {
					int cont = 0;
					for (StatoMembro sm : lista) {
						try {
							Method m = sm.getClass().getMethod(
									"get" + nomeCampo.substring(0, 1).toUpperCase() + nomeCampo.substring(1), null);
							try {
								String valoreCampo = (String) m.invoke(sm);
								if (str.equals(valoreCampo))
									cont++;
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					stat.put(str, cont);
				}
			}
			else if(tipo.equals("double")) {
			
				//media, min, max
				double somma = 0.0;
				double max = 0.0, min = 999999999999.0; //da perfezionare
				for(StatoMembro sm : lista) {
					try {
						Method m = sm.getClass().getMethod(
								"get" + nomeCampo.substring(0, 1).toUpperCase() + nomeCampo.substring(1), null);
						try {
							double valoreCampo = (double) m.invoke(sm);
							somma += valoreCampo;
							
							if(valoreCampo > max) max = valoreCampo;
							else if(valoreCampo < min) min = valoreCampo;
							
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				double media = somma/lista.size();
				

				//deviazione standard
				double dev = 0.0;
				for(StatoMembro sm : lista) {
					try {
						Method m = sm.getClass().getMethod(
								"get" + nomeCampo.substring(0, 1).toUpperCase() + nomeCampo.substring(1), null);
						try {
							double valoreCampo = (double) m.invoke(sm);
							double scarto = Math.pow(valoreCampo - media, 2);
							dev += scarto;
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				dev = Math.sqrt(dev / lista.size());
				
				//NumberFormat nf = new DecimalFormat("0.00");
				
				DecimalFormat nf = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
				nf.setMaximumFractionDigits(2); // 340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
				
				stat.put("avg", media);				
				stat.put("min", min);
				stat.put("max", max);
				stat.put("dev std", dev);
				//stat.put("sum", Double.parseDouble(nf.format(somma)));
				stat.put("sum", somma);
				//stat.put("sumn", Math.round(somma * 100) / 100.0);
				stat.put("count", lista.size());
			}
		}
		else {
			stat.put("result", "attributo inesistente");
		}
		statistiche.add(stat);
	}

	public JSONArray getStatistiche() {
		return statistiche;
	}
}

