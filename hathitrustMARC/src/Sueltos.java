import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Sueltos {

	public static void processLines(ArrayList<String> listaFiles, String folder) {
		int counter=1;
		for (String linea2 : listaFiles) {	
			System.out.println("Record "+counter+"..."+listaFiles.size());
			counter++;
			try {
				URL url = new URL("http://catalog.hathitrust.org/Record/"+linea2+".xml");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
		 
				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ conn.getResponseCode());
				}
		 
				BufferedReader br2 = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
		 
				String output;
//				System.out.println("Output from Server .... \n");
//				while ((output = br2.readLine()) != null) {
//					System.out.println(output);
//				}
		 
				FileWriter fichero = null;
		        PrintWriter pw = null;
		        try
		        {
		        	File D=new File("Result/"+folder+"/");
		        	D.mkdirs();
		            fichero = new FileWriter("Result/"+folder+"/"+linea2+".xml");
		            pw = new PrintWriter(fichero);
		            
		            while ((output = br2.readLine()) != null) {
		            	pw.println(output);
		            	System.out.println(output);
					}
		 
		        } catch (Exception e) {
		            e.printStackTrace();
		        } finally {
		           try {
		           if (null != fichero)
		              fichero.close();
		           } catch (Exception e2) {
		              e2.printStackTrace();
		           }
		        }
				
				conn.disconnect();
			} catch (Exception e3) {
				System.err.println("Error en Elemento " + linea2 );
				e3.printStackTrace();
			}
			
		}
		
	}

}
