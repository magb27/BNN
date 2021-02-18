package bnn.process;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import bnn.elements.Neuron;
import bnn.elements.Neurotransmitter;
//import graph.GraphViz;


// Here is where we're cooking all !!!
public class SinapticSpace  implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	static Logger logger = (Logger) LogManager.getLogger(SinapticSpace.class);
	
	public ArrayList<Neuron> neuralPopulation;
	public ArrayList<Neurotransmitter> neurotransmitterPopulation;	
	public ArrayList<Neurotransmitter> ntrsTriggers;
	public ArrayList<Neurotransmitter> ntrsRegionPop;
	public ArrayList<Neuron> neuronsWorkers;
	public String pulseConcept;
	public ArrayList<Neuron> neuronsConcepts;

		
	//static GraphViz graphics;
	
	public void init(ArrayList<Neurotransmitter> ntrsTriggersIn) {
		//graphics = ProcessManager.getGraphics();
		ntrsTriggers = new ArrayList<Neurotransmitter>();
		ntrsTriggers = ntrsTriggersIn;
		logger.trace("ntrsTrigger "+ntrsTriggers.size());
		ntrsRegionPop = new ArrayList<Neurotransmitter> ();	
		neuronsConcepts = new ArrayList<Neuron>();
		
		//neuralPopulation = SinapticManager.neuralPopulation;
		logger.trace("neuralPopulation of "+neuralPopulation.size()+" neurons has benn generated.");
		//neurotransmitterPopulation = SinapticManager.neurotransmitterPopulation;

		logger.trace("neurotransmitterPopulation "+neurotransmitterPopulation.size()+" neurons has benn generated.");
		ntrsRegionPop.addAll(neurotransmitterPopulation);
		ntrsRegionPop.addAll(ntrsTriggers);

		logger.trace("ntrsRegionPop "+ntrsRegionPop.size());
		
	}
}
