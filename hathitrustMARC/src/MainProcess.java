import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
			ArrayList<String> ListaFiles=new ArrayList<String>();;
			while  ((linea = br.readLine())!=null)
			{
				ListaFiles.add(linea);
			}
			
			if (args.length==0)
				Todo.processLines(ListaFiles, Folder);
			else if (args[0].equals("S"))
				Sueltos.processLines(ListaFiles,Folder);
			else if (args[0].equals("C"))
				Unidos.processLines(ListaFiles,Folder);
			else if (args[0].equals("T"))
				Todo.processLines(ListaFiles,Folder);

			
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
