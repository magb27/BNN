package bnn.elements;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class Pulse implements Serializable{

	private static final long serialVersionUID = 1L;

	static Logger logger = (Logger) LogManager.getLogger(Pulse.class);
	
	public ArrayList<Neurotransmitter> ntrsTriggers;
	private String conceptNameStr;
	private String pulseType;
	private StringBuilder pulse_str;
	
	public Pulse() {
		this.ntrsTriggers = new ArrayList<Neurotransmitter>();
	}
	
	public void setTriggers(ArrayList<Neurotransmitter> ntrs_in) {
		this.ntrsTriggers = ntrs_in;
	}
	
	public ArrayList<Neurotransmitter> getTriggers(){
		return this.ntrsTriggers;
	}
	public void setConceptName(String lable) {
		conceptNameStr = lable;
	}
	public String getConceptName() {
		return this.conceptNameStr;
	}

	public String getPulseType() {
		return pulseType;
	}

	public void setPulseType(String pulseType) {
		this.pulseType = pulseType;
	}
	public String toString() {
		pulse_str = new StringBuilder();
		pulse_str.append("PULSE NAME["+conceptNameStr+"] PULSETYPE["+pulseType+"] ");
		pulse_str.append("\n\tTRIGGERS\n");
		for (int i = 0; i < ntrsTriggers.size(); i++) {
			pulse_str.append("\t\t"+ntrsTriggers.get(i).toString()+"\n");
		}
		return pulse_str.toString();
	}
}
