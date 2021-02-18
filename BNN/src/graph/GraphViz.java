package graph;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import bnn.elements.Neuron;
import bnn.elements.Neurotransmitter;
import bnn.process.NeuralRegion;
import utils.Params;

public class GraphViz {
	static Logger logger = (Logger) LogManager.getLogger(GraphViz.class);
	public static StringBuilder graphStr;
	public static ArrayList<StringBuilder> graph;
	
	public void Initialize() {
		graphStr = new StringBuilder();
		graph = new ArrayList<StringBuilder>();
		
		graphStr.append("\ndigraph bnn{\n"); 
		graphStr.append("\trankdir=LR;\n\tfontname=\"Maven Pro\";\n");
		graphStr.append("\tdir=forward;\n"); 
		graphStr.append("\tarrowType=crow\n"); 
		graphStr.append("\tnode [fontsize=6 fixedsize=false style=filled width=0.1 height=0.1 fontcolor=black fontname=\"Maven Pro\" shape=egg];\n");

		
		commit();
	}
	
	public void StartRegion(String regionName) {
		graphStr.append("\tsubgraph cluster_"+regionName+" {\n\t\tfontname=\"Maven Pro\"\n\t\tlabel=Region_"+regionName+";\n\t\tfontsize=8;\n");
		graphStr.append("\t\tstyle=rounded;\n\t\tcolorscheme=bupu9;\n\t\tcolor=3;\n\t\tbgcolor=3;\n");
		commit();
	}
	public void EndRegion() {
		graphStr.append("\t}");
		commit();
	}
	
	public void Finalize(NeuralRegion nrIn) throws FileNotFoundException {
		graphStr.append("\n\tlabel=\"Diagram Params:\n");
		graphStr.append("\t"+Params.STAGES+" Stages.\n");
		graphStr.append("\t"+Params.NEURON_POPULATION_FOR_REGION+" Neurons.\n");
		graphStr.append("\t"+Params.NEURAL_DENDRITES+" Dendrites for each Neuron.\n");
		for(int i=0;i<nrIn.sinapticSpace.ntrsTriggers.size();i++) {
			//graphStr.append("\tTRIGGER "+nrIn.sinapticSpace.ntrsTriggers.get(i).chromosome.toString()+"\n");
		}		
		for(int i=0;i<nrIn.sinapticSpace.ntrsRegionPop.size();i++) {
			graphStr.append("\tAXON "+nrIn.sinapticSpace.ntrsRegionPop.get(i).chromosome.toString()+"\n");
		}	
		int contFinalStateTrue = 0;
		int contFinalStateFalse = 0;
		int contFinalStateNeutral = 0;
		
		for(int i=0;i<nrIn.sinapticSpace.neuronsWorkers.size();i++) {
			if(nrIn.sinapticSpace.neuronsWorkers.get(i).polarity.equals("POSITIVE")) {
				contFinalStateTrue++;
				logger.trace("Neuron Activity ON ["+nrIn.sinapticSpace.neuronsWorkers.get(i).nid+"]");
				//graphStr.append("\tNeuron Activity ON ["+SinapticSpace.neuralPopulation.get(i).nid+"].\n");
			}else if(nrIn.sinapticSpace.neuronsWorkers.get(i).polarity.equals("NEGATIVE")){
				contFinalStateFalse++;
				logger.trace("Neuron Activity OFF ["+nrIn.sinapticSpace.neuronsWorkers.get(i).nid+"]");
				//graphStr.append("\tNeuron Activity OFF ["+SinapticSpace.neuralPopulation.get(i).nid+"].\n");
			}else if(nrIn.sinapticSpace.neuronsWorkers.get(i).polarity.equals("NEUTRAL")){
				contFinalStateNeutral++;
				logger.trace("Neuron Activity NEU ["+nrIn.sinapticSpace.neuronsWorkers.get(i).nid+"]");
			}
		}
		graphStr.append("\t"+contFinalStateTrue+" Total Neurons Activity ON.\n");
		graphStr.append("\t"+contFinalStateFalse+" Total Neurons Activity OFF.\n");
		graphStr.append("\t"+contFinalStateNeutral+" Total Neurons Activity NEU.\n");
		graphStr.append("\t\"");
		graphStr.append("\n}");
		commit();
		toDot();
	}
	
	public void commit() {
		graph.add(graphStr);
		graphStr = new StringBuilder();

	}
	public void add(String new_entry) {
		graphStr.append(new_entry);
	}
	
	public void add(Neurotransmitter ntr, Neuron nr, Neurotransmitter den) {
		String ntr_chromosome = ntr.chromosome.toString();
		logger.trace("ntr_chromosome = "+ ntr_chromosome);
		String dendrite_chromosome = den.chromosome.toString();
		logger.trace("dendrite_chromosome = "+ dendrite_chromosome);
		int color = 40 - (den.reinforcement * 2);
		
		add("\t\tNTR"+ntr.ntrId+"_W"+ntr.reinforcement+" -> NTR"+den.ntrId+"_W"+den.reinforcement+"[label="+ntr_chromosome+" fontsize=6 fontname=\"Maven Pro\" arrowsize=0.5];\n");
		add("\t\tNTR"+den.ntrId+"_W"+den.reinforcement+" -> NRA"+nr.nid+"[label="+dendrite_chromosome+" fontsize=6 fontname=\"Maven Pro\" arrowsize=0.5];\n");
		add("\t\tNTR"+den.ntrId+"_W"+den.reinforcement+"[color=grey"+color+" fontcolor=white ];\n");
		
		if(ntr.type.equals("TRIGGERS")) {
			add("\t\tNTR"+ntr.ntrId+"_W"+ntr.reinforcement+"[color=dodgerblue3 fontcolor=white];\n");
		}
		if(ntr.type.equals("REGIONAL")){
			add("\t\tNTR"+ntr.ntrId+"_W"+ntr.reinforcement+"[color=darkorange fontcolor=white];\n");
		}
		if(ntr.type.equals("NEURONAL")){
			add("\t\tNTR"+ntr.ntrId+"_W"+ntr.reinforcement+"[color=mediumorchid fontcolor=white];\n");
		}
		if(nr.polarity.equals("POSITIVE")) {
			add("\t\tNRA"+nr.nid+"[color=darkgreen fontcolor=white];\n");
		}else if(nr.polarity.equals("NEGATIVE")){
			add("\t\tNRA"+nr.nid+"[color=firebrick3 fontcolor=white];\n");
		}else if(nr.polarity.equals("NEUTRAL")){
			add("\t\tNRA"+nr.nid+"[color=chocolate1 fontcolor=white];\n");
		}		
		commit();
		
	}
	
	public void add(Neuron nr, Neurotransmitter axon) {
		add("\t\tNRA"+nr.nid+" -> "+"NTR"+axon.ntrId+"_W"+axon.reinforcement+"[label="+axon.chromosome+" fontsize=6 fontname=\"Maven Pro\" arrowsize=0.5];\n");
		//add("\t\tNRA"+nr.nid+"[color=indigo fontcolor=white];\n");			
		add("\t\tNTR"+axon.ntrId+"_W"+axon.reinforcement+"[color=mediumorchid fontcolor=white fontsize=6];\n");		
		commit();
	}
	
	public void toDot() throws FileNotFoundException {
		
		StringBuilder str_out = new StringBuilder();
		for (int i=0;i < graph.size(); i++) {
			str_out.append(graph.get(i));
		}
		logger.trace(str_out);
		String filename = Params.PATH_TO_SER+"\\DotDiagram.dt";
		try {
			
		    BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		    writer.write(str_out.toString());
		    writer.close();			
			
		} catch(IOException ex) { 
			System.out.println("IOException is caught\n"+ex); 
		} 
	}	
}
