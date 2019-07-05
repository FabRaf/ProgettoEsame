package com.esame.progetto;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.ArrayList;


public class Statistiche {
	JSONObject elementiConOcc = new JSONObject();
	
	public Statistiche(String nomeCampo) throws FileNotFoundException, IOException, ClassNotFoundException {
		super(); //perché?
		boolean flag = false;
		GeneratoreMetadati gm = new GeneratoreMetadati();
		JSONArray metadati = gm.getMetadati();
				
		for(int i=0; i<metadati.size(); i++) {
			JSONObject jsonobj = (JSONObject) metadati.get(i);
			String campo = (String) jsonobj.get("alias");
			String tipo = (String) jsonobj.get("type");
			if(tipo.equals("String") && campo.equals(nomeCampo)) {
				flag = true;
				break;
			}
		}
		
		if(flag == true) {
			GeneratoreLista gl = new GeneratoreLista();
			ArrayList<StatoMembro> lista = gl.getLista();
			
			ArrayList<String> elementiUnici = new ArrayList<String>();
			//elementiUnici.add("");
			
			for(StatoMembro sm : lista) {
				flag=false;
				try {
					Method m = sm.getClass().getMethod("get"+nomeCampo.substring(0, 1).toUpperCase()+nomeCampo.substring(1), null);
					try {
						String valoreCampo = (String) m.invoke(sm);
						for(String str : elementiUnici) {
							if(str.equals(valoreCampo)) flag = true;
						}
						if(flag == false) elementiUnici.add(valoreCampo);
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
			
			for(String str : elementiUnici) {
				int cont = 0;
				for(StatoMembro sm : lista) {
					try {
						Method m = sm.getClass().getMethod("get"+nomeCampo.substring(0, 1).toUpperCase()+nomeCampo.substring(1), null);
						try {
							String valoreCampo = (String) m.invoke(sm);
							if(str.equals(valoreCampo)) cont++;
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
				elementiConOcc.put(str, cont);
			}
		}
		else {
			elementiConOcc.put("result", "refused - parametro inesistente o di tipo numerico");
		}
	}

	public JSONObject getElementiConOcc() {
		return elementiConOcc;
	}
}
