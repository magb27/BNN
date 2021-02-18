package bnn.process;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import bnn.elements.Neuron;
import bnn.elements.Neurotransmitter;
import utils.Params;
//import utils.Serializer;
import utils.Utilities;


public class SinapticManager {
	static Logger logger = (Logger) LogManager.getLogger(SinapticManager.class);
	private static StringBuilder ntrAxonChromosome;
	private static StringBuilder newDendriteChromosome;
	private static int fMaxValue;
	
	public SinapticManager() {
		
	}
	

	
	
	// Method to find similarities between dendrites & neurotransmitters
	// It gives a value acording to parametrized factors.
	public static int Similarity(StringBuilder dendriteChromosome , StringBuilder ntrChromosome) {
		
		int coincidence_t1 = 0;
		int coincidence_t2 = 0;
	
		logger.trace("dendriteChromosome " + dendriteChromosome + " & " + ntrChromosome  + " ntrChromosome");
		
		coincidence_t1 = 0;
		
		// Parametrized factors and evaluations
		int fValue1 = (Params.C1_FACTOR / dendriteChromosome.length());
		int fValue2 = (fValue1 / Params.C2_FACTOR);
		int finalValue = 0;
			
		
		// Maximun value that could be obtained
		fMaxValue = (fValue1*dendriteChromosome.length()) + fValue2*dendriteChromosome.length();
		setfMaxValue(fMaxValue);
		
		StringBuilder sb = new StringBuilder();
		
		// Selecting only characters from dendrite's chromosome that appears withing neurtransmitter's chromosome 
		dendriteChromosome.chars().distinct().forEach(c -> sb.append((char) c));
		logger.trace("Non duplicates selected from dendriteChromosome = "+sb.toString());
		
		for (int l = 0; l < dendriteChromosome.length(); l++) {
			
			// Calcultaing coincidences.
			Character charDentrite = dendriteChromosome.charAt(l);
			Character charNtr = ntrChromosome.charAt(l);
			if(charDentrite.equals(charNtr)) {
				coincidence_t1++;
			}
			
			// Evaluating coincidences
			coincidence_t2 = 0;
			for (int ll=0;ll<sb.length();ll++) {
				coincidence_t2 = coincidence_t2 +  StringUtils.countMatches(ntrChromosome, sb.charAt(ll));
			}
			
			// Final value of how good is the similarity between dendrite and neurotransmitter
			finalValue = ((fValue1 * coincidence_t1)+(fValue2 * coincidence_t2));

		}
		logger.trace("coincidence_t1 = "+coincidence_t1+" coincidence_t2 = "+coincidence_t2+ " FinalValue = "+finalValue+" of "+fMaxValue);
		return finalValue;
	}
	
	
	
	public static StringBuilder getNtrAxonChromosome() {
		return ntrAxonChromosome;
	}

	public static void setNtrAxonChromosome(StringBuilder ntrAxonIn) {
		ntrAxonChromosome = ntrAxonIn;
	}
	
	public static void setNewDendriteChromosome(StringBuilder newDendriteIn) {
		newDendriteChromosome = newDendriteIn;
	}
	
	public static StringBuilder getNewDendriteChromosome() {
		return newDendriteChromosome;
	}
	
	public static StringBuilder GenerateNewChromosome(int chromosomeLength) {
		StringBuilder newChromosome = new StringBuilder();
		for (int i=0; i<chromosomeLength; i++) {
			newChromosome.append(Utilities.getPart());
		}
		return newChromosome;
	}
	
	static ArrayList<Neuron> GenerateNeurons() {
		ArrayList<Neuron> population = new ArrayList<Neuron>();
		for (int i = 0; i < Params.NEURON_POPULATION_FOR_REGION; i++) {
			Neuron nr = new Neuron();
			nr.Init();
			population.add(nr);
			//logger.trace(nr.toString(true));
		}
		return population;
	}
	
	static ArrayList<Neurotransmitter> GenerateNeurotransmitters(){
		ArrayList<Neurotransmitter> ntrs = new ArrayList<Neurotransmitter>();
		Neurotransmitter ntr;
		for (int i = 0; i < Params.NEUROTRANSMITTER_POPULATION_FOR_REGION; i++) {
			ntr = new Neurotransmitter();
			ntr.Generate(0, Params.NEUROTRANSMITTER_REGIONAL_TYPE);
			ntrs.add(ntr);
		}
		return ntrs;
	}


	public static int getfMaxValue() {
		return fMaxValue;
	}

	public static void setfMaxValue(int fMaxValue) {
		SinapticManager.fMaxValue = fMaxValue;
	}
}
