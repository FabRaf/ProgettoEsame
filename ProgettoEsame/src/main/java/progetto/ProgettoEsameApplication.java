package progetto;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.File;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import org.apache.commons.io.FileUtils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProgettoEsameApplication {

	public static void main(String[] args) {
		download();
		SpringApplication.run(ProgettoEsameApplication.class, args);
	}

	private static void download() {
		File dataset = new File("dataset.csv");
		String str = "http://data.europa.eu/euodp/data/api/3/action/package_show?id=funds-absorption-rate";
		try {
			String data = "";
			URL url = new URL(str);
			URLConnection openConnection = url.openConnection();
			openConnection.addRequestProperty("User-Agent", "Chrome");
			InputStream in = openConnection.getInputStream();
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String line = "";
				while((line = br.readLine()) != null) {
					data+=line;
				}
			}
			finally {
				in.close();
			}
			JSONObject text = (JSONObject) JSONValue.parseWithException(data);
			JSONObject result = (JSONObject) text.get("result");
			JSONArray resources = (JSONArray) result.get("resources");
			
			for(Object x : resources) {
				if(x instanceof JSONObject) {
					JSONObject o = (JSONObject) x;
					URL url1 = new URL((String) o.get("url"));
					String formato = (String) o.get("format");
					if(formato.equals("http://publications.europa.eu/resource/authority/file-type/CSV")) {
						FileUtils.copyURLToFile(url1, dataset);
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