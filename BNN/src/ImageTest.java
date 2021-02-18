import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import bnn.elements.Neurotransmitter;
import bnn.elements.Pulse;
import bnn.interfaces.ImageReader;
import bnn.process.ProcessManager;
import utils.Params;

public class ImageTest {
	static Logger logger;

	public static void main(String[] args) throws IOException {
		LoggerContext context = (LoggerContext)LogManager.getContext(false);
		File file = new File("resources\\log4j2.properties");
		context.setConfigLocation(file.toURI());
		logger = (Logger) LogManager.getLogger(test.class);
		Instant start = Instant.now();
		logger.info("initialyzing params...");
		Params.init();
		
		if (args.length>0) {
		
			String pulseName = args[0];
			String imageUrl = args[1];

			Pulse pulse = new Pulse();
			pulse.setConceptName(pulseName);
			pulse.setPulseType("ImageProcessing");
			
			SendPulse(pulse, imageUrl);
			
		}else {
			System.out.println("ERROR::: Mising Params!!! Must be [Name] [File Url]");
		}
		
		
		Params.saveConfig();
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();
		logger.info("Duration: "+timeElapsed+" milscs");		
	}

	private static void SendPulse(Pulse pulse, String imageUrl) throws IOException {
		ImageReader ir = new ImageReader();
		ArrayList<String> chromosomes = ir.read(imageUrl);
		
		for (int i=0; i < chromosomes.size(); i++) {
			Neurotransmitter ntr = new Neurotransmitter();
			ntr.Generate(0, Params.NEUROTRANSMITTER_TRIGGERS_TYPE);
			StringBuilder newChromosome = new StringBuilder();
			newChromosome.append(chromosomes.get(i).toString());
			ntr.chromosome = newChromosome;
			pulse.ntrsTriggers.add(ntr);
		}
		System.out.println(pulse.toString());
		
		ProcessManager pm = new ProcessManager();
		pm.setPulseIn(pulse);
	}
}
