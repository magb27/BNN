package bnn.elements;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class Dictionary  implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	static Logger logger = (Logger) LogManager.getLogger(Dictionary.class);
	
	ArrayList<Concept> rootConcepts;
	StringBuilder rootConceptsStr;
	Concept concept;
	
	public Dictionary() {
		
	}
	
	public void loadDictionary() {
		try {
			rootConcepts = new ArrayList<Concept>();
			File fXmlFile = new File("Dictionary.xml");
			//TODO Dictionary name by params.
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			logger.trace("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("concept");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				logger.trace("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					concept = new Concept();
					concept.conceptId = Integer.parseInt(eElement.getAttribute("conceptId"));
					concept.conceptName = eElement.getAttribute("conceptName");
					concept.parentId = Integer.parseInt(eElement.getAttribute("parentId"));
					
					if(concept.parentId > 0) {
						Concept parenConcept = findConceptById(concept.parentId);
						parenConcept.sub_concepts.add(concept);
					}
					rootConcepts.add(concept);
				}
				
				
			}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		logger.trace(this.toString());
	}
	
	
	private Concept findConceptById(int conceptIdIn) {
	    for(Concept cpt : rootConcepts) {
	        if(cpt.conceptId == conceptIdIn) {
	            return cpt;
	        }
	    }
	    return null;
	}
	
	public String toString() {
		rootConceptsStr = new StringBuilder();
		for (int i = 0; i < rootConcepts.size(); i++) {
			rootConceptsStr.append(rootConcepts.get(i).toString());
		}
		return rootConceptsStr.toString();
	}
}
