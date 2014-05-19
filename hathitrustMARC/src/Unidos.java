import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;


public class Unidos {

	public static void processLines(ArrayList<String> listaFiles, String folder) {

		String[] XMLS=new String[listaFiles.size()];
		
		int iterador = 0;
		for (String linea2 : listaFiles) {	
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
		 
				StringBuffer output=new StringBuffer();

				String LineaT=null;
				while ((LineaT = br2.readLine()) != null) {
					System.out.println(LineaT);
					output.append(LineaT);
				}
				

				XMLS[iterador]=output.toString();
				

		      iterador++;
				
				conn.disconnect();
			} catch (Exception e3) {
				System.err.println("Error en Elemento " + linea2 );
				e3.printStackTrace();
			}
			
		}
    	
		 try {
			mainMerge(XMLS, folder);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}
	
	private static void mainMerge(String[] files,String folder) throws Exception {
		    // proper error/exception handling omitted for brevity
		    Document doc = merge("/collection", files);
		    print(doc,folder);
		  }
	
	 private static Document merge(String expression,
			 String... files) throws Exception {
		    XPathFactory xPathFactory = XPathFactory.newInstance();
		    XPath xpath = xPathFactory.newXPath();
		    XPathExpression compiledExpression = xpath
		        .compile(expression);
		    return merge(compiledExpression, files);
		  }

		  private static Document merge(XPathExpression expression,
				  String... files) throws Exception {
		    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
		        .newInstance();
		    docBuilderFactory
		        .setIgnoringElementContentWhitespace(true);
		    DocumentBuilder docBuilder = docBuilderFactory
		        .newDocumentBuilder();
		    Document base = docBuilder.parse(new ByteArrayInputStream(files[0].toString().getBytes(StandardCharsets.UTF_8)));

		    Node results = (Node) expression.evaluate(base,
		        XPathConstants.NODE);
		    if (results == null) {
		      throw new IOException(files[0]
		          + ": expression does not evaluate to node");
		    }

		    for (int i = 1; i < files.length; i++) {
		      Document merge = docBuilder.parse(new ByteArrayInputStream(files[i].toString().getBytes(StandardCharsets.UTF_8)));
		      Node nextResults = (Node) expression.evaluate(merge,
		          XPathConstants.NODE);
		      while (nextResults.hasChildNodes()) {
		        Node kid = nextResults.getFirstChild();
		        nextResults.removeChild(kid);
		        kid = base.importNode(kid, true);
		        results.appendChild(kid);
		      }
		    }

		    return base;
		  }

		  private static void print(Document doc,String folder) throws Exception {
//		    TransformerFactory transformerFactory = TransformerFactory
//		        .newInstance();
//		    Transformer transformer = transformerFactory
//		        .newTransformer();
//		    DOMSource source = new DOMSource(doc);
//		    Result result = new StreamResult(System.out);
//		    transformer.transform(source, result);
		    
		    
	        try
	        {
	        	Source source = new DOMSource(doc);
	        	Result result = new StreamResult(new java.io.File("Result/"+folder+".xml"));
	        	Transformer transformer = TransformerFactory.newInstance().newTransformer();
	        	transformer.transform(source, result);
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
		  }


}
