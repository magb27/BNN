package bnn.process;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import bnn.elements.Neuron;
import bnn.elements.Neurotransmitter;
import bnn.elements.Pulse;
import utils.Params;


public class NeuralRegion implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	public static ArrayList<Neuron> neuralPopulation;
	public static ArrayList<Neurotransmitter> neurotransmitterPopulation;
	public ArrayList<String> pulsesResults;
	public ArrayList<Neurotransmitter> ntrs_trigger;
	static Logger logger = (Logger) LogManager.getLogger(NeuralRegion.class);
	
	public HashMap<Pulse, ArrayList<Neuron>> resultsMap;
	
	public String regionName;
	//GraphViz graphics;
	public SinapticSpace sinapticSpace;
	public SinapticProcess sinapticProcess;
	public boolean training;
	
	public NeuralRegion() {
		pulsesResults = new ArrayList<String>();
		resultsMap = new HashMap<Pulse, ArrayList<Neuron>>();
		
	}
	
	// Initialize a new Neural region.
	public void init(Pulse pulseIn) {
		
		ntrs_trigger = new ArrayList<Neurotransmitter>();
		logger.debug( "Concept Name = " + pulseIn.getConceptName());
		sinapticSpace.pulseConcept = pulseIn.getConceptName();
		
		logger.debug( "Pulse Type = " + pulseIn.getPulseType());
		ntrs_trigger = pulseIn.getTriggers();
		logger.trace("Pulse Ntrs Triggers = "+pulseIn.getTriggers());
		for (int i = 0; i < ntrs_trigger.size(); i++) {
			logger.trace(ntrs_trigger.get(i).toString());
		}
		
		logger.info("Setting Up SinapticSpace");
		sinapticSpace = getSinapticSpace();
		
		logger.info("Init SinapticSpace");
		sinapticSpace.init(ntrs_trigger);
		
		logger.info("Init SinapticProcess");
		sinapticProcess = new SinapticProcess();
		sinapticProcess.InitSinapticProcess(sinapticSpace);
		
		
		
		EvaluateResults evaResults = new EvaluateResults();
		logger.info("Evaluating results");
		String lastResultProcess = evaResults.Evaluate(sinapticSpace);
		pulsesResults.add(lastResultProcess);
		training = true;
		int contOk = 0;
		for(int i = pulsesResults.size(); i > Params.EVALUATING_MATCHES_TO_OK-1; i--) {
			
			if(pulsesResults.get(i-1).equals(lastResultProcess)) {
				logger.info(pulsesResults.get(i-1));
				contOk++;	
				if(contOk >= Params.EVALUATING_MATCHES_TO_OK) {
					training = false;
					break;
				}
			}
		}
		//System.out.println("MAGB::"+pulseIn);
		logger.info("Evaluating results "+evaResults.activatedNeurons.size());

		resultsMap.put(pulseIn, evaResults.activatedNeurons);
		
		
	}
	
	// Setting up a new Region...
	public void SetUpRegion() {
		logger.info("Setting Up Region");
		
		sinapticSpace = new SinapticSpace();
		logger.info("Generating Neurons for its Sinaptic Space");

		sinapticSpace.neuralPopulation = SinapticManager.GenerateNeurons();
		logger.info("Generating Neurotransmitters for its Sinaptic Space");
		sinapticSpace.neurotransmitterPopulation = SinapticManager.GenerateNeurotransmitters();
		
		setSinapticSpace(sinapticSpace);
	}

	
	// This method reset all flag at each Neuron to setting up them available for future process
	public void ResetNeuralRegion() {
		
		Neuron neuron;
		Neurotransmitter dendrite;
		
		
		for (int i = 0; i < sinapticSpace.neuralPopulation.size(); i++ ) {
			neuron = sinapticSpace.neuralPopulation.get(i);
			logger.trace("Reseting Neuron Before "+neuron.toString(false));
			for (int h = 0; h < neuron.dendrites.size();h++) {
				dendrite = neuron.dendrites.get(h);
				logger.trace("Reseting Dendrite Before"+dendrite.toString());
				dendrite.state = true;
				//dendrite.reinforcement = 0;
				logger.trace("Reseting Dendrite After"+dendrite.toString());
			}
			neuron.state = true;
			neuron.polarity = "NEUTRAL";
			neuron.roundsToGrowUp = Params.ROUNDSTOGROWUP;
			neuron.pointsNegatives = Params.POINTS_OF_POLARITY;
			neuron.pointsNegatives = Params.POINTS_OF_POLARITY;
			//neuron.reinforcement = 0;
			logger.trace("Reseting Neuron After "+neuron.toString(false));
			
		}
	}	
	public void setSinapticSpace(SinapticSpace sinapticSpaceIn) {
		sinapticSpace = sinapticSpaceIn;
	}
	public SinapticSpace getSinapticSpace() {
		return sinapticSpace;
	}
	
	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
}
