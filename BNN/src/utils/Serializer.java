package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import bnn.elements.Neuron;
import bnn.elements.Neurotransmitter;
import bnn.process.NeuralRegion;


public class Serializer {
	static Logger logger = (Logger) LogManager.getLogger(Serializer.class);
	
	static String filetosave = ""; 
	static String filetoload = "";
	
	public static boolean CheckFile(String pathfilename) {
		String fileToCheck = pathfilename;
		File tempFile = new File(fileToCheck);
		boolean exists = tempFile.exists();
		return exists;
	}
	
	public static void saveNeuralRegion(NeuralRegion nRegionOut) {
		String filename = nRegionOut.getRegionName();
		//TODO filename by params.
		filetosave = Params.NR_PATH+"\\"+filename;
		// Serialization 
		try { 
			//Saving of object in a file 
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filetosave));
			// Method for serialization of object 
			out.writeObject(nRegionOut); 
			out.close(); 
			logger.debug("Object has been serialized to: "+filetosave);
		} catch(IOException ex) { 
			logger.error(ex);
			ex.printStackTrace();
		} 		
	}
	
	public static NeuralRegion loadNeuralRegion(String neuralRegionNameStr) {
		String filename = neuralRegionNameStr;
		//TODO filename by params.		
		filetoload = Params.NR_PATH+"\\"+filename;
		NeuralRegion  neuralRegionIn = null;
		try { 
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(filetoload)); 
			// Method for deserialization of object 
			neuralRegionIn = (NeuralRegion) in.readObject(); 
			in.close(); 
			logger.debug("Object has been deserialized from: "+filetoload);
		} catch(IOException ioe) { 
			logger.error(ioe);
			ioe.printStackTrace();
		} catch(ClassNotFoundException ce) { 
			logger.error(ce);
			ce.printStackTrace(); 
		}
		return neuralRegionIn;
	} 	
	
	public static void SaveNtrs(ArrayList<Neurotransmitter> NTRS_OUT, String filename) {
		filetosave = Params.PATH_TO_SER+"\\"+filename;
		// Serialization 
		try { 
			//Saving of object in a file 
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filetosave));
			// Method for serialization of object 
			out.writeObject(NTRS_OUT); 
			out.close(); 
			logger.debug("Object has been serialized to: "+filetosave);
		} catch(IOException ex) { 
			ex.printStackTrace();
		} 
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Neurotransmitter> LoadNtrs(String filename) {
		// Deserialization 
		
		filetoload = Params.PATH_TO_SER+"\\"+filename;
		ArrayList<Neurotransmitter>  NTRS_IN = null;
		try { 
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(filetoload)); 
			// Method for deserialization of object 

			NTRS_IN = (ArrayList<Neurotransmitter>) in.readObject(); 
			in.close(); 

			logger.debug("Object has been deserialized from: "+filetoload);
		} catch(IOException ioe) { 
			ioe.printStackTrace();
		} catch(ClassNotFoundException ce) { 
			ce.printStackTrace();
		}
		return NTRS_IN;
	} 
	
	public static void SaveNeurons(ArrayList<Neuron> NRS_OUT, String filename) {
		filetosave = Params.PATH_TO_SER+"\\"+filename;
		// Serialization 
		try { 
			//Saving of object in a file 
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filetosave));
			// Method for serialization of object 
			out.writeObject(NRS_OUT); 
			out.close(); 
			logger.debug("Object has been serialized to: "+filetosave);
		} catch(IOException ex) { 
			ex.printStackTrace();
		} 
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Neuron> LoadNeurons(String filename) {
		// Deserialization 
		
		filetoload = Params.PATH_TO_SER+"\\"+filename;
		ArrayList<Neuron>  NRS_IN = null;
		try { 
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(filetoload)); 
			// Method for deserialization of object 

			NRS_IN = (ArrayList<Neuron>) in.readObject(); 
			in.close(); 

			logger.debug("Object has been deserialized from: "+filetoload);
		} catch(IOException ioe) { 
			ioe.printStackTrace();
		} catch(ClassNotFoundException ce) { 
			ce.printStackTrace(); 
		}
		return NRS_IN;
	} 	
}
