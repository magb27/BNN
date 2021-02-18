package bnn.elements;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import utils.Params;
import utils.Utilities;

public class Neuron implements java.io.Serializable{
	static Logger logger = (Logger) LogManager.getLogger(Neuron.class);
	
	private static final long serialVersionUID = 1L;
	
	public StringBuilder neuron_str;
	public long nid;
	public boolean state;
	public ArrayList<Neurotransmitter> dendrites;
	public String polarity;
	public int reinforcement;
	public int roundsToGrowUp;
	public int pointsPositives;
	public int pointsNegatives;
	public String connection;
	
	public Neuron() {
		this.nid = Utilities.getUniqueNeuronId();
	}

	public Neuron Init() {

		Neurotransmitter ntr;
		dendrites = new ArrayList<Neurotransmitter>();
		//terminals = new ArrayList<Neurotransmitter>();
		connection = "NONE";
		// Setting Neuron to true, so it will be online
		this.state = true;
		polarity = "NEUTRAL";
		reinforcement = 0;
		pointsPositives = Params.POINTS_OF_POLARITY;
		pointsNegatives = Params.POINTS_OF_POLARITY;
		roundsToGrowUp = Params.ROUNDSTOGROWUP;
		for (int i = 0; i < Params.NEURAL_DENDRITES; i++) {
			ntr = new Neurotransmitter();
			ntr.Generate(this.nid, Params.NEURONAL_TANSMITTER_TYPE);
			this.dendrites.add(ntr);
		}
		logger.trace(this.toString(false));
		return this;
	}
	
	
	
	public String toString(boolean detail) {
		neuron_str = new StringBuilder();
		neuron_str.append("NEURON NID["+this.nid+"] STATE["+this.state+"] POLARITY["+this.polarity+"] ");
		neuron_str.append("REINFORCEMENT["+reinforcement+"] NO_DENDITRES["+this.dendrites.size()+"] ");
		neuron_str.append("PTS+["+pointsPositives+"] PTS-["+pointsNegatives+"] ");
		neuron_str.append("ROUNDTOGROWUP["+roundsToGrowUp+"] CONNECTION_WITH["+connection+"] ");
		if(detail) {
			neuron_str.append("\n\tDENDRITES\n");
			for (int i = 0; i < this.dendrites.size(); i++) {
				neuron_str.append("\t\t"+this.dendrites.get(i).toString()+"\n");
			}
			//meuron_str.append("\tTERMINALS\n");
			//for (int i = 0; i < this.terminals.size(); i++) {
				//meuron_str.append("\t\t"+this.terminals.get(i).toString()+"\n");
			//}	
		}
		return neuron_str.toString();
	}
}
