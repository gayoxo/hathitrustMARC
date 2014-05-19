import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 */

/**
 * @author Joaquin Gayoso-Cabada
 *
 */
public class MainProcess {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		FileReader fr = null;
		String Folder=Long.toString(System.nanoTime());
		try {
			File A=new File("idents.txt");
			fr = new FileReader (A);
			BufferedReader br = new BufferedReader(fr);
			String linea;
			while  ((linea = br.readLine())!=null)
			{
				
				try {
					URL url = new URL("http://catalog.hathitrust.org/Record/"+linea+".xml");
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
//					System.out.println("Output from Server .... \n");
//					while ((output = br2.readLine()) != null) {
//						System.out.println(output);
//					}
			 
					FileWriter fichero = null;
			        PrintWriter pw = null;
			        try
			        {
			        	File D=new File("Result/"+Folder+"/");
			        	D.mkdirs();
			            fichero = new FileWriter("Result/"+Folder+"/"+linea+".xml");
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
					System.err.println("Error en Elemento " + linea );
					e3.printStackTrace();
				}
				
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally
		{
			try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
		}
		
	}

}
