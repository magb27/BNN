package bnn.process;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import bnn.elements.Dictionary;
import bnn.elements.Neurotransmitter;
import bnn.elements.Pulse;
import bnn.io.SocketIO;
import graph.GraphViz;
import utils.Params;
import utils.Serializer;
import utils.Utilities;


public class ProcessManager{


	static Logger logger = (Logger) LogManager.getLogger(ProcessManager.class);
	
	public static GraphViz graphics;
	public static Dictionary dictionary;
	public static ArrayList<Neurotransmitter> ntrs_trigger;
	public NeuralRegion neuralRegion;
	public static Pulse pulse;
	

	public ProcessManager() {
		neuralRegion = new NeuralRegion();
		
	}
	
	// Initialyzing process.
	public void init(Pulse pulseIn) throws FileNotFoundException { 
		
		
		//neuralRegion.ResetNeuralRegion();
		
		dictionary = new Dictionary();
		dictionary.loadDictionary();
		
		neuralRegion.training = true;
		
		int s = 0;
		while(neuralRegion.training) {
			
			neuralRegion.ResetNeuralRegion();
			
			s++;
			logger.info("Stage["+s+"]");
			graphics = new GraphViz();
			graphics.Initialize();		
			graphics.StartRegion(neuralRegion.getRegionName());	
			
			neuralRegion.init(pulseIn);
			
			graphics.EndRegion();
			graphics.Finalize(neuralRegion);
		}
		
		logger.info(neuralRegion.sinapticSpace.neurotransmitterPopulation.toString());
		
		
		neuralRegion.pulsesResults.clear();
		setNeuralRegion(neuralRegion);
	}
	
	public static void listenForPulseIn() throws IOException {
		
		SocketIO sio = new SocketIO();
		pulse = (Pulse) sio.readIn();
		ntrs_trigger = new ArrayList<Neurotransmitter>();
		logger.info( "Concept Name = " + pulse.getConceptName());
		ntrs_trigger = pulse.getTriggers();
		logger.trace(""+pulse.getTriggers());
		for (int i = 0; i < ntrs_trigger.size(); i++) {
			logger.trace(ntrs_trigger.get(i).toString());
		}
	}
	
	// Receibe a pulse with data input
	public void setPulseIn(Pulse pulseIn) throws IOException {
		
		pulse = pulseIn;

		this.neuralRegion = getNeuralRegion(pulse);
		
		logger.debug(Utilities.loadRegionsNames().toString());
		
		init(pulse);
		

	}
	

	private static NeuralRegion getNeuralRegion(Pulse pulseIn) {
		NeuralRegion nrLoaded = new NeuralRegion();
		logger.trace(Params.NR_PATH+"\\"+pulseIn.getPulseType());
		if(Serializer.CheckFile(Params.NR_PATH+"\\"+pulseIn.getPulseType())) {
			nrLoaded = Serializer.loadNeuralRegion(pulseIn.getPulseType());
			logger.trace("neuralRegion "+nrLoaded.getRegionName());
			logger.trace("neuralPopulation "+nrLoaded.sinapticSpace.neuralPopulation.size());
			logger.trace("neurotransmitterPopulation "+nrLoaded.sinapticSpace.neurotransmitterPopulation.size());
		}else {
			logger.error("No Neural Region "+pulseIn.getPulseType()+" found at "+Params.NR_PATH+"\\"+pulseIn.getPulseType());
			logger.info("Generating new one...");
			nrLoaded.setRegionName(pulseIn.getPulseType());
			nrLoaded.SetUpRegion();
			setNeuralRegion(nrLoaded);
		}
		return nrLoaded;
	}

	private static void setNeuralRegion(NeuralRegion nrIn) {
		Serializer.saveNeuralRegion(nrIn);
	}
	
	
	public static GraphViz getGraphics() {
		return graphics;
	}
	public static Dictionary getDictionary() {
		return dictionary;
	}	
}
