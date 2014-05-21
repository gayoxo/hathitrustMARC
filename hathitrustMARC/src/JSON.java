import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 
 */

/**
 * @author Joaquin Gayoso-Cabada
 *
 */
public class JSON {

	public static void processLines(ArrayList<String> listaFiles, String folder, String args) throws FileNotFoundException {
		ArrayList<String> Resultado=new ArrayList<String>();
		
		int counter=1;
		for (String linea2 : listaFiles) {	
			System.out.println("Record "+counter+"..."+listaFiles.size());
			counter++;
			try {
				URL url = new URL("http://catalog.hathitrust.org/api/volumes/full/htid/"+linea2+".json");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
		 
				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ conn.getResponseCode());
				}
		 
				BufferedReader br2 = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
		 
				StringBuffer output=new StringBuffer();

				String LineaT=null;
				
				while ((LineaT = br2.readLine()) != null) {
					System.out.println(LineaT);
					output.append(LineaT);
				}
				
				
				String Result=output.toString();
			 	JsonParser parser = new JsonParser();
		        JsonElement datos = parser.parse(new StringReader(Result));
		        dumpJSONElement(datos,Resultado);
				
			
				
				conn.disconnect();
			} catch (Exception e3) {
				System.err.println("Error en Elemento " + linea2 );
				e3.printStackTrace();
			}
			
		}
		
		if (args.equals("S"))
			Sueltos.processLines(Resultado,folder);
		else if (args.equals("C"))
			Unidos.processLines(Resultado,folder);
		else if (args.equals("T"))
			Todo.processLines(Resultado,folder);
		
	}

	private static void dumpJSONElement(JsonElement elemento, ArrayList<String> resultado) {
		if (elemento.isJsonObject()) {
	        JsonObject obj = elemento.getAsJsonObject();
	        java.util.Set<java.util.Map.Entry<String,JsonElement>> entradas = obj.entrySet();
	        java.util.Iterator<java.util.Map.Entry<String,JsonElement>> iter = entradas.iterator();
	        java.util.Map.Entry<String,JsonElement> entrada = iter.next();
	        dumpJSONElement2(entrada.getValue(),resultado);
	 
	    } 
		
	}

	private static void dumpJSONElement2(JsonElement elemento,
			ArrayList<String> resultado) {
		if (elemento.isJsonObject()) {
	        JsonObject obj = elemento.getAsJsonObject();
	        java.util.Set<java.util.Map.Entry<String,JsonElement>> entradas = obj.entrySet();
	        java.util.Iterator<java.util.Map.Entry<String,JsonElement>> iter = entradas.iterator();
	            java.util.Map.Entry<String,JsonElement> entrada = iter.next();
	            if (!resultado.contains(entrada.getKey()))
	            	{resultado.add(entrada.getKey());
	            System.out.println(entrada.getKey());
	            	}
	            else 
	            	System.out.println("repetido: " + entrada.getKey());
	 
	    }
		
	}

}
