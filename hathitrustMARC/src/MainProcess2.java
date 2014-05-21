import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class MainProcess2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileReader fr = null;
		String Folder=Long.toString(System.nanoTime());
		try {
			File A=new File("json.txt");
			fr = new FileReader (A);
			BufferedReader br = new BufferedReader(fr);
			String linea;
			ArrayList<String> ListaFiles=new ArrayList<String>();;
			while  ((linea = br.readLine())!=null)
			{
				ListaFiles.add(linea);
			}
			
			String Args="T";
			if (args.length==0)
				Args="T";
			else if (args[0].equals("S"))
				Args="S";
			else if (args[0].equals("C"))
				Args="C";
			else if (args[0].equals("T"))
				Args="T";
			
			
			JSON.processLines(ListaFiles,Folder,Args);

			
			br.close();
			System.out.println("FIN");
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
