package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class Params {
	
	static Logger logger = (Logger) LogManager.getLogger(Params.class);
	static Properties prop;
	
	// New Params
	public static int REINFORCEMENT_INCREMENT;
	public static int MAX_REINFORCEMENT;
	public static int MIN_REINFORCEMENT;

	public static int MAX_CICLES_TO_ABORT;
	public static int STAGES;
	public static boolean KEEP_TRIGGERS;
	
	public static String NTRS_TRIGGERS_FILENAME;
	public static String NEURONS_SS_FILENAME;
	public static String NTRS_SS_FILENAME;
	public static String NR_PATH;
	public static String NR_INDEX_FILE_NAME;
	
	public static String NEURONAL_TANSMITTER_TYPE;
	public static String NEUROTRANSMITTER_TRIGGERS_TYPE;
	public static String NEUROTRANSMITTER_REGIONAL_TYPE;
	public static String AXON_TRANSMITTER_TYPE;
	public static String NEUROTRANSMITTER_TERMINAL_TYPE;
	public static String PATH_TO_SER;
	
	public static int NEURON_POPULATION_FOR_REGION;
	public static int NEUROTRANSMITTER_POPULATION_FOR_REGION;
	
	public static int NEURAL_DENDRITES;
	public static int NEURAL_MAX_DENDRITES_AVAILABLE;
	public static int EVALUATING_MATCHES_TO_OK;
	public static int ATTEMPS_INCREMENT;
	public static int ROUNDSTOGROWUP;
	
	public static int CHROMOSOME_LENGTH;
	public static int CHR_MAX;	
	public static int CHR_MIN;	

	public static int C1_FACTOR;
	public static int C2_FACTOR;

	public static long NTR_UNIQUE_ID;
	public static long NEURON_UNIQUE_ID;
	
	public static int POINTS_OF_POLARITY;
	
	
	
	public static void init() throws FileNotFoundException, IOException{
		prop = ReadConfig();
		logger.info("Properties file loaded.");
		


		
		/* PARAMS FOR NEURONAL REGION DIMENSION*/
		REINFORCEMENT_INCREMENT = Integer.parseInt(prop.getProperty("REINFORCEMENT_INCREMENT"));
		MAX_REINFORCEMENT = Integer.parseInt(prop.getProperty("MAX_REINFORCEMENT"));
		MIN_REINFORCEMENT = Integer.parseInt(prop.getProperty("MIN_REINFORCEMENT"));
		MAX_CICLES_TO_ABORT = Integer.parseInt(prop.getProperty("MAX_CICLES_TO_ABORT"));
		STAGES = Integer.parseInt(prop.getProperty("STAGES"));
		KEEP_TRIGGERS = Boolean.parseBoolean(prop.getProperty("KEEP_TRIGGERS"));
		
		NEURON_POPULATION_FOR_REGION = Integer.parseInt(prop.getProperty("NEURON_POPULATION_FOR_REGION"));
		NEUROTRANSMITTER_POPULATION_FOR_REGION = Integer.parseInt(prop.getProperty("NEUROTRANSMITTER_POPULATION_FOR_REGION"));
		NEURAL_DENDRITES = Integer.parseInt(prop.getProperty("NEURAL_DENDRITES"));
		NEURAL_MAX_DENDRITES_AVAILABLE = Integer.parseInt(prop.getProperty("NEURAL_MAX_DENDRITES_AVAILABLE"));
		EVALUATING_MATCHES_TO_OK = Integer.parseInt(prop.getProperty("EVALUATING_MATCHES_TO_OK"));
		ATTEMPS_INCREMENT = Integer.parseInt(prop.getProperty("ATTEMPS_INCREMENT"));
		ROUNDSTOGROWUP = Integer.parseInt(prop.getProperty("ROUNDSTOGROWUP"));
		
		CHROMOSOME_LENGTH = Integer.parseInt(prop.getProperty("CHROMOSOME_LENGTH"));
		CHR_MAX = Integer.parseInt(prop.getProperty("CHR_MAX"));
		CHR_MIN = Integer.parseInt(prop.getProperty("CHR_MIN"));
		POINTS_OF_POLARITY = Integer.parseInt(prop.getProperty("POINTS_OF_POLARITY"));
		
		
		NEURONAL_TANSMITTER_TYPE = prop.getProperty("NEURONAL_TRANSMITTER_TYPE"); // Default name for Neural Transmitter
		NEUROTRANSMITTER_TRIGGERS_TYPE = prop.getProperty("NEUROTRANSMITTER_TRIGGERS_TYPE");
		NEUROTRANSMITTER_REGIONAL_TYPE = prop.getProperty("NEUROTRANSMITTER_REGIONAL_TYPE");
		AXON_TRANSMITTER_TYPE = prop.getProperty("AXON_TRANSMITTER_TYPE");
		NEUROTRANSMITTER_TERMINAL_TYPE = prop.getProperty("NEUROTRANSMITTER_TERMINAL_TYPE");

		PATH_TO_SER = prop.getProperty("PATH_TO_SER");
		NTRS_TRIGGERS_FILENAME = prop.getProperty("NTRS_TRIGGERS_FILENAME"); //
		NEURONS_SS_FILENAME = prop.getProperty("NEURONS_SS_FILENAME");
		NTRS_SS_FILENAME = prop.getProperty("NTRS_SS_FILENAME");
		NR_PATH = prop.getProperty("NR_PATH");
		NR_INDEX_FILE_NAME = prop.getProperty("NR_INDEX_FILE_NAME");
		
		C1_FACTOR = Integer.parseInt(prop.getProperty("C1_FACTOR"));
		C2_FACTOR = Integer.parseInt(prop.getProperty("C2_FACTOR"));

		NTR_UNIQUE_ID = Long.parseLong(prop.getProperty("NTR_UNIQUE_ID")); // Start Number of sequence for Ntrs & Neurons Id.
		NEURON_UNIQUE_ID = Long.parseLong(prop.getProperty("NEURON_UNIQUE_ID")); // Start Number of sequence for Ntrs & Neurons Id.
	}
	
	
	
	
	
	public static Properties ReadConfig() throws FileNotFoundException, IOException {
		String configPath = "bnn.config";
			prop = new Properties();
			
			try {
				prop.load(new FileInputStream(configPath));
			}catch(IOException e) {
				System.out.println("Params "+e);
			}
		logger.trace(prop.toString());
		return prop;
	}
	public static void saveConfig() throws FileNotFoundException, IOException {
		String configPath = "bnn.config";
		prop.store(new FileOutputStream(configPath),"");
		logger.trace(prop.toString());
	}
	
}
