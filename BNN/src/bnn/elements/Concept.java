package bnn.elements;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class Concept {
	static Logger logger = (Logger) LogManager.getLogger(Concept.class);
	public ArrayList<Concept> sub_concepts;
	public String conceptName;
	public int conceptId;
	public int parentId;
	public StringBuilder conceptStr;
	
	public Concept(){
		sub_concepts = new ArrayList<Concept>();
	}
	
	public String toString() {
		conceptStr = new StringBuilder();
		conceptStr.append("\nCONCEPT_ID["+conceptId+"] CONCEPT_NAME["+conceptName+"] PARENT_ID["+parentId+"]");
		if(sub_concepts.size()>0) {
			conceptStr.append("\n\tSUBCONCEPTS\n");
			for(int i=0;i<sub_concepts.size();i++) {
				conceptStr.append("\t\t"+sub_concepts.get(i).toString()+"\n");
			}
		}
		return conceptStr.toString();
	}
}
