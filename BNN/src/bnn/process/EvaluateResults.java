package bnn.process;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import bnn.elements.Neuron;
import utils.IdSorter;


public class EvaluateResults {
	static Logger logger = (Logger) LogManager.getLogger(EvaluateResults.class);
	public ArrayList<Neuron> activatedNeurons;
	
	public EvaluateResults() {
		activatedNeurons = new ArrayList<Neuron>();
		
	}
	
	public String Evaluate(SinapticSpace sSpce) {
		activatedNeurons = sSpce.neuronsWorkers;
		activatedNeurons.sort(new IdSorter());
		StringBuilder resultsChain = new StringBuilder();
		//logger.info("Activated Neurons...");
		for (int i=0;i<activatedNeurons.size();i++){
			logger.debug(""+activatedNeurons.get(i).toString(false));
			if(activatedNeurons.get(i).polarity.equals("NEGATIVE")) {
				//resultsChain.append("N"+activatedNeurons.get(i).nid);
				resultsChain.append("0");
			}else if(activatedNeurons.get(i).polarity.equals("POSITIVE")){
				//resultsChain.append("P"+activatedNeurons.get(i).nid);
				resultsChain.append("1");
			}else if(activatedNeurons.get(i).polarity.equals("NEUTRAL")){
				//resultsChain.append("U"+activatedNeurons.get(i).nid);
				resultsChain.append("U");
			}
			
		}
		//logger.info(" "+resultsChain);
		return resultsChain.toString();
	}
}
