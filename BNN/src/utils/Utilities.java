package utils;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.w3c.dom.Document;
//import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Utilities {
	static Logger logger = (Logger) LogManager.getLogger(Utilities.class);

	
	public static String getPart() {
		char ch = (char) ((int)((Math.random()*Params.CHR_MAX)+Params.CHR_MIN));
		return Character.toString(ch);
	}
	
	
	public static long getUniqueNeurotransmitterId() {
		Params.NTR_UNIQUE_ID = Params.NTR_UNIQUE_ID + 1;
		Params.prop.setProperty("NTR_UNIQUE_ID", ""+Params.NTR_UNIQUE_ID);
		//System.out.println("Params.NTR_UNIQUE_ID "+Params.NTR_UNIQUE_ID);
		return Params.NTR_UNIQUE_ID;
	}
	public static long getUniqueNeuronId() {
		Params.NEURON_UNIQUE_ID = Params.NEURON_UNIQUE_ID + 1;
		//System.out.println("Params.NEURON_UNIQUE_ID "+Params.NEURON_UNIQUE_ID);
		Params.prop.setProperty("NEURON_UNIQUE_ID", ""+Params.NEURON_UNIQUE_ID);
		return Params.NEURON_UNIQUE_ID;
	}
	public static ArrayList<String> loadRegionsNames() {
		ArrayList<String> nrNames = new ArrayList<String>();
		try {
			File fXmlFile = new File(Params.NR_PATH+"\\"+Params.NR_INDEX_FILE_NAME);
		
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			logger.trace("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("region");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				logger.trace("Current Element : " + nNode.getNodeName()+" Value="+nNode.getTextContent());
				nrNames.add(nNode.getTextContent());
			}
	    } catch (Exception e) {
	    	logger.error(e);
	    	e.printStackTrace();
	    	
	    }
		return nrNames;
	}	
	//TODO Write file....
}
