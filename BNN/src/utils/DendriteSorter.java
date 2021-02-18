package utils;

import java.util.Comparator;

import bnn.elements.Neurotransmitter;

public class DendriteSorter implements Comparator<Neurotransmitter> 
{
    @Override
    public int compare(Neurotransmitter o1, Neurotransmitter o2) {
    	Integer a = Integer.parseInt(""+o1.reinforcement);
    	Integer b = Integer.parseInt(""+o2.reinforcement);
        return a.compareTo(b);
    }
}
