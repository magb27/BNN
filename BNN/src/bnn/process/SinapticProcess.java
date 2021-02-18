package bnn.process;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import bnn.elements.Neuron;
import bnn.elements.Neurotransmitter;
import graph.GraphViz;
import utils.IdSorter;
import utils.Params;
import utils.Utilities;


public class SinapticProcess implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	static Logger logger = (Logger) LogManager.getLogger(SinapticProcess.class);
	public static SinapticSpace sSpace;
	public static ArrayList<Neurotransmitter> ntrsBugget;
	public static StringBuilder ntrAxonChromosome;
	private static int globalCounter;
	public static int mutations;
	public static int transformations;
	public static int sinapsisCounter;
	public static int triggerLeftCounter;
	public static GraphViz graphics;
	public static boolean continueProcess;

	
	// Initializing de main process.
	public void InitSinapticProcess(SinapticSpace sSpaceIn) {
		globalCounter = 0;
		mutations = 0;		
		transformations = 0;
		sinapsisCounter = 0;
		triggerLeftCounter = 0;
		continueProcess = true;

		sSpace = new SinapticSpace();
		sSpace = sSpaceIn;
		
		graphics = ProcessManager.getGraphics();
		
		ntrsBugget = new ArrayList<Neurotransmitter>();
		ntrsBugget = sSpace.neurotransmitterPopulation;
		
		sSpace.neuronsWorkers = new ArrayList<Neuron>();
		
		logger.debug("Starting process");
		
		
		
		int totalDendrites = 0;
		for(int i=0;i<sSpace.neuralPopulation.size();i++) {
			logger.trace(" "+sSpace.neuralPopulation.get(i).toString(true));
			totalDendrites = totalDendrites + sSpace.neuralPopulation.get(i).dendrites.size();
		}
		
		logger.info("Processing forcess:");
		logger.info("["+(sSpace.neuralPopulation.size())+"] Neurons");
		logger.info("["+totalDendrites+"] Dendrites");
		
		
		
		while(continueProcess) {
			globalCounter++;
			if(globalCounter > Params.MAX_CICLES_TO_ABORT){
				logger.error("MAX_CICLES_TO_ABORT Exceeded!!!");
				globalCounter = 0;
				sSpace.neuralPopulation.addAll(SinapticManager.GenerateNeurons());
				break;
			}
			sSpace.neuralPopulation.sort(new IdSorter());

			NeuronsManager();
			
		}
		System.out.println("\n");
		
	}
	
	

	private static boolean CheckResults() {
		boolean result = false;
		logger.debug("Triggers available ["+sSpace.ntrsRegionPop.size()+"]");
		triggerLeftCounter = 0;
		for(int r = 0; r < sSpace.ntrsRegionPop.size();r++) {
			// If all trigger had been processed task is finished.
			if(sSpace.ntrsRegionPop.get(r).type.equals("TRIGGERS")) {
				logger.debug(" "+sSpace.ntrsRegionPop.get(r).toString());
				triggerLeftCounter++;
				result = true;
				
			}
		}
		return result;
	}

	// Presinaptic selecting best match between dendrite and neurotransmitters.
	private static void PreSinaptic(Neuron neuronIn, Neurotransmitter dendriteIn) {
		
		Neurotransmitter neurotransmitter = null;
		// To avoid still running when all Triggers had benn processed, each attemp chekresult
		
		//Looking for best pretender
		logger.trace("Processing Dendrite  "+dendriteIn.toString());
		for (int j = 0; j < sSpace.ntrsRegionPop.size();j++) {
			neurotransmitter = (Neurotransmitter) sSpace.ntrsRegionPop.get(j);
			
			System.out.print("\rRound["+globalCounter+"] Triggers left["+triggerLeftCounter+"] Mutations["+mutations+"] Transformations["+transformations+"] Sinapsis performed["+sinapsisCounter+"]");

			
			//When differenet Neurotransmitters are receibed, this Dendrite must be eable to adapting to the new Chromosome chain lenght
			// TODO not at the momment
			logger.trace("Controlling differents chromosome's lenght [Dendrite " + dendriteIn.chromosome.length() + " Vs " + neurotransmitter.chromosome.length() + " Neurotransmitter]");
			if(dendriteIn.chromosome.length() != neurotransmitter.chromosome.length()) {
				//dendriteIn.chromosome = SinapticManager.GenerateNewChromosome(neurotransmitter.chromosome.length());
				//break;
			}
			// Controlling that we ar not processing an axon Neurotransmitter from the same Neuron
			logger.trace("Controlling Parents Ids [Dendrite " + dendriteIn.parentId + " Vs " + neurotransmitter.parentId+" Neurotransmitter]");
			if(neurotransmitter.parentId != dendriteIn.parentId) {
					
				logger.trace("Controlling Dendrite state "+dendriteIn.state);
				// Dentrite state must be true, means this was not used before.
				if (dendriteIn.state) {
					// If the Dendrite's chromosome and the Neurotransmitter's chromosome are equals sinapsis can be done
					if(dendriteIn.chromosome.toString().equals(neurotransmitter.chromosome.toString())) {
						logger.trace("Best Choice "+dendriteIn.toString());
						sinapsisCounter++;
						Sinaptic(dendriteIn.chromosome, sSpace.ntrsRegionPop.get(j).chromosome);
						FinalizeSinapsis(neuronIn, dendriteIn, sSpace.ntrsRegionPop.get(j));
					}else if(dendriteIn.reinforcement < 1){
						dendriteIn.reinforcement = dendriteIn.reinforcement - Params.REINFORCEMENT_INCREMENT;
						 if(dendriteIn.reinforcement < (0-Params.CHROMOSOME_LENGTH)) {dendriteIn = Transformation(dendriteIn, neurotransmitter);}
					}	
				}
			}
		}
	}




	// The inner sinaptic process generate new axon neurotransmitter 
	private static void Sinaptic(StringBuilder dendriteChromosome , StringBuilder ntrChromosome) {
		
		ntrAxonChromosome = new StringBuilder();

		int chromosomeLength = ntrChromosome.length();
		for(int i = chromosomeLength; i > 0; i--) {
			ntrAxonChromosome.append(dendriteChromosome.charAt(i-1));
		}
		logger.trace("New Axon Chromosome = "+ntrAxonChromosome.toString());

		//SinapticManager.setNewDendriteChromosome(newDendriteChromosome);
		SinapticManager.setNtrAxonChromosome(ntrAxonChromosome);
	}
	

	
	private static void FinalizeSinapsis(Neuron neuronIn, Neurotransmitter dendriteIn, Neurotransmitter neurotransmitter) {
		
		sSpace.ntrsRegionPop.remove(neurotransmitter);
		//dendriteIn.state = false;
		
		if(dendriteIn.reinforcement < 0) {dendriteIn.reinforcement = 0;}
		dendriteIn.reinforcement = dendriteIn.reinforcement + Params.REINFORCEMENT_INCREMENT;
		if(dendriteIn.reinforcement >= Params.MAX_REINFORCEMENT) {
			dendriteIn.reinforcement = Params.MAX_REINFORCEMENT;
			
		}
		

		logger.trace("Dendrite Reinforcement = "+dendriteIn.reinforcement);
		
		UpdateNeuronState(neuronIn, SinapticManager.getNtrAxonChromosome());
		graphics.add(neurotransmitter, neuronIn, dendriteIn);		
		if(neuronIn.state) {
			Neurotransmitter axonTermination = new Neurotransmitter();
			axonTermination.Generate(neuronIn.nid, Params.AXON_TRANSMITTER_TYPE);
			axonTermination.chromosome = SinapticManager.getNtrAxonChromosome();
			sSpace.ntrsRegionPop.add(axonTermination);
			graphics.add(neuronIn, axonTermination);	
			neuronIn.roundsToGrowUp = neuronIn.roundsToGrowUp -1;			
		}

		

		
		if((neuronIn.roundsToGrowUp == 0) && neuronIn.dendrites.size() < Params.NEURAL_MAX_DENDRITES_AVAILABLE) {
			Neurotransmitter reinforcementDendrite = new Neurotransmitter();
			reinforcementDendrite.Generate(neuronIn.nid, Params.NEURONAL_TANSMITTER_TYPE);
			reinforcementDendrite.chromosome = dendriteIn.chromosome;
			neuronIn.dendrites.add(reinforcementDendrite);
			neuronIn.roundsToGrowUp = Params.ROUNDSTOGROWUP;
			neuronIn.state = false;
		}
		

		
	}
	
	// If all Dendrites are false (mean they are closed), Neuron must be change to false
	private static void UpdateNeuronState(Neuron neuronIn, StringBuilder newAxonChromosomeIn) {
		
		int dentritesSize = neuronIn.dendrites.size();
		//boolean newState = false;
		int positives = 0;
		int negatives = 0;
		int neuronReinforcement =  0;
		
		logger.trace("Checking Neuron "+neuronIn.toString(false));
		
		for (int h = 0; h < newAxonChromosomeIn.length();h++) {
			if(((int)newAxonChromosomeIn.charAt(h)) % 2 == 0) {
				positives++;
			}else {
				negatives++;
			}
		}
		neuronIn.pointsNegatives = neuronIn.pointsNegatives - positives;
		neuronIn.pointsPositives = neuronIn.pointsPositives - negatives;
	
		if(neuronIn.pointsPositives > neuronIn.pointsNegatives) {
			neuronIn.polarity = "POSITIVE";
		} else if(neuronIn.pointsPositives < neuronIn.pointsNegatives){
			neuronIn.polarity = "NEGATIVE";
		}else if(neuronIn.pointsPositives == neuronIn.pointsNegatives){
			neuronIn.polarity = "NEUTRAL";
		}
		//neuronIn.state = newState;
		neuronIn.reinforcement = neuronReinforcement;
		logger.trace("Setting Neuron polarity["+neuronIn.polarity+"]");
		logger.trace("Final checking neuron ["+dentritesSize+"]"+neuronIn.toString(true));
		
		sSpace.neuronsWorkers.add(neuronIn);

	}

	private static void NeuronsManager() {
		Neuron neuron;

		
		for (int i = 0; i < sSpace.neuralPopulation.size(); i++ ) {
			neuron = sSpace.neuralPopulation.get(i);
		
			if (neuron.state) {
				logger.trace(" "+neuron.toString(true));
				if(CheckResults()) {
					DendritesManager(neuron);
				}else {
					continueProcess = false;
					
				}
			}
		}
	}
	private static void DendritesManager(Neuron neuronIn) {
		Neurotransmitter dendrite;
		for (int h = 0; h < neuronIn.dendrites.size();h++) {
			dendrite = new Neurotransmitter();
			dendrite = neuronIn.dendrites.get(h);
			logger.trace(" "+dendrite.toString());
			ManageNeurotransmitterPop(neuronIn, dendrite);
		}
		
	}
	private static void ManageNeurotransmitterPop(Neuron neuronIn, Neurotransmitter dendriteIn) {
		PreSinaptic(neuronIn, dendriteIn);
	}
	
	// Mutate dendrites.
	private static Neurotransmitter MutateDendrite(Neurotransmitter dendriteToMutate) {
		logger.trace("Dendritre going to be mutated "+dendriteToMutate.toString());
		mutations++;
		StringBuilder mutateChromosome = new StringBuilder();
		for (int c = 0; c < Params.CHROMOSOME_LENGTH; c++) {
			mutateChromosome.append(Utilities.getPart());
		}
		dendriteToMutate.chromosome = mutateChromosome;
		dendriteToMutate.reinforcement = 0;
		//dendriteToMutate.state = true;
		logger.trace("Dendritre Mutated "+dendriteToMutate.toString());
		return dendriteToMutate;
		
	}


	private static Neurotransmitter Transformation(Neurotransmitter dendriteToTransform, Neurotransmitter neurotransmitterIn) {
		
		if(dendriteToTransform.reinforcement < (0-(Params.CHROMOSOME_LENGTH*2))) {
			dendriteToTransform = MutateDendrite(dendriteToTransform);
		}else {
			logger.trace("Dendritre going to be transformed "+dendriteToTransform.toString());
			transformations++;
			StringBuilder newDendriteChromosome = new StringBuilder();
			for(int d = 0; d < neurotransmitterIn.chromosome.length(); d++) {
				if(neurotransmitterIn.chromosome.charAt(d) == dendriteToTransform.chromosome.charAt(d)) {
					newDendriteChromosome.append(neurotransmitterIn.chromosome.charAt(d));
				}else {
					newDendriteChromosome.append(Utilities.getPart());
				}
				
			}
			dendriteToTransform.chromosome = newDendriteChromosome;
			logger.trace("Dendritre Transformed "+dendriteToTransform.toString());
		}
		return dendriteToTransform;
		
	}		
}
