import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import bnn.elements.Neurotransmitter;
import bnn.elements.Pulse;
//import bnn.io.SocketIO;
import bnn.process.ProcessManager;
import utils.Params;
import utils.Serializer;

public class test {
	static Logger logger;
	
	public static void main(String[] args) throws FileNotFoundException, IOException {

		LoggerContext context = (LoggerContext)LogManager.getContext(false);
		File file = new File("resources\\log4j2.properties");
		
		context.setConfigLocation(file.toURI());
		
		logger = (Logger) LogManager.getLogger(test.class);
		
		Instant start = Instant.now();
		/*
		logger.trace("Trace");
		logger.debug("Debug");
		logger.info("Info");
		logger.warn("Warn");
		logger.error("Error");
		logger.fatal("Fatal");
		*/
		logger.info("initialyzing params...");
		Params.init();
		String argsValue = null;
		if(args.length == 0) {
			argsValue = "NULL"; 
		}else {
			argsValue = args[0];
		}
		init(argsValue);
		Params.saveConfig();
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();
		logger.info("Duration: "+timeElapsed+" milscs");		

		
	}
	public static void init(String argsIn) throws IOException {
		logger.info("Preparing pulse...");
		ArrayList<Neurotransmitter> ntrs = new ArrayList<Neurotransmitter>();
		if(Params.KEEP_TRIGGERS) {
			if(Serializer.CheckFile(Params.PATH_TO_SER+"\\"+Params.NTRS_TRIGGERS_FILENAME)) {
				ntrs = (ArrayList<Neurotransmitter>)Serializer.LoadNtrs(Params.NTRS_TRIGGERS_FILENAME);

			}else {
				logger.error("Ca'nt find file " + Params.PATH_TO_SER + "\\"+ Params.NTRS_TRIGGERS_FILENAME + " regenerating.");
				Gadgets.GenerateNtrsPopulation();
			}
		}else {
			Gadgets.GenerateNtrsPopulation();
			//Serializer.SaveNtrs(ntrs, Params.NTRS_TRIGGERS_FILENAME);
			ntrs = (ArrayList<Neurotransmitter>)Serializer.LoadNtrs(Params.NTRS_TRIGGERS_FILENAME);
		}
		
		logger.trace("Reading Ntrs Triggers");
		Pulse pulse = new Pulse();
		logger.trace(" "+ntrs.toString());
		pulse.setTriggers(ntrs);
		
		pulse.setConceptName(argsIn);
		pulse.setPulseType("TESTING");
		
		if(argsIn != "NULL") {SetTriggers(pulse);}
		
		
		logger.info("Sending Pulse...\n"+pulse.toString());
		
		ProcessManager pm = new ProcessManager();

		

		pm.setPulseIn(pulse);

		

	}
	private static void SetTriggers(Pulse pulseIn) {
		String conceptName = pulseIn.getConceptName();
		int triggerNo = 0;
		for (int i=0;i<conceptName.length();i++) {
			
			if( conceptName.substring(i, i+1).equals("T") ) {
				pulseIn.ntrsTriggers.get(triggerNo).chromosome = new StringBuilder("ABC");
			}
			if( conceptName.substring(i, i+1).equals("E") ) {
				pulseIn.ntrsTriggers.get(triggerNo).chromosome = new StringBuilder("BBC");
			}
			if( conceptName.substring(i, i+1).equals("S") ) {
				pulseIn.ntrsTriggers.get(triggerNo).chromosome = new StringBuilder("CAB");
			}
			if( conceptName.substring(i, i+1).equals("1") ) {
				pulseIn.ntrsTriggers.get(triggerNo).chromosome = new StringBuilder("AAB");
			}
			if( conceptName.substring(i, i+1).equals("2") ) {
				pulseIn.ntrsTriggers.get(triggerNo).chromosome = new StringBuilder("ABA");
			}
			if( conceptName.substring(i, i+1).equals("3") ) {
				pulseIn.ntrsTriggers.get(triggerNo).chromosome = new StringBuilder("BAA");
			}
			
			triggerNo++;
		}
	}

}
