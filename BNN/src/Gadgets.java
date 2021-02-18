import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import bnn.elements.Neuron;
import bnn.elements.Neurotransmitter;
import utils.Params;
import utils.Serializer;


public class Gadgets {
	static Logger logger;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		LoggerContext context = (LoggerContext)LogManager.getContext(false);
		File file = new File("resources\\log4j2.properties");
		
		context.setConfigLocation(file.toURI());
		
		logger = (Logger) LogManager.getLogger(Gadgets.class);
		
		Params.init();

	}
	
	static void GenerateNtrsPopulation() throws FileNotFoundException, IOException {
		main(null);
		logger.debug("Generating TRIGGERS");
		ArrayList<Neurotransmitter> ntrs = new ArrayList<Neurotransmitter>();
		for (int i=0;i<32;i++) {
			Neurotransmitter ntr = new Neurotransmitter();
			ntr.Generate(0, Params.NEUROTRANSMITTER_TRIGGERS_TYPE);
			ntrs.add(ntr);
		}
		Serializer.SaveNtrs(ntrs, Params.NTRS_TRIGGERS_FILENAME);
	}
	
	static void generateNeurons(int no) {
		for (int i=0; i< no ;i++) {
			Neuron nr = new Neuron();
			nr.Init();
			System.out.println(nr.toString(true));
		}
		
	}
}
