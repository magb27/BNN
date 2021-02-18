package bnn.elements;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import utils.Params;
import utils.Utilities;

public class Neurotransmitter implements java.io.Serializable{
	static Logger logger = (Logger) LogManager.getLogger(Neurotransmitter.class);
	
	private static final long serialVersionUID = 1L;
	
	public StringBuilder chromosome;
	public long ntrId;
	public long parentId;
	public String type;
	public int reinforcement;
	public boolean state;
	
	public Neurotransmitter() {
		this.ntrId = Utilities.getUniqueNeurotransmitterId();
	}
	
	public void Generate(long parentId, String type){
		chromosome = new StringBuilder();
		for (int i = 0; i < Params.CHROMOSOME_LENGTH; i++) {
			chromosome.append(Utilities.getPart());
		}
		this.type = type;
		// State true = dentrite can receibe Neurotransmitters
		this.state = true;
		this.parentId = parentId;
		this.reinforcement = 0;
		logger.trace("Neurotransmitter " + this.toString());
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("NTR_TYPE["+this.type+"] NTR_ID["+this.ntrId+"] PARENT_ID["+this.parentId+"] ");
		str.append("CHROMOSOME["+this.chromosome+"] REINFORCEMENT["+reinforcement+"] ");
		str.append("STATE["+state+"]");
		return str.toString();
	}	
	
}
