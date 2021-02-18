package utils;

import java.util.Comparator;

import bnn.elements.Neuron;

public class ReinforcementSorter implements Comparator<Neuron> 
{
    @Override
    public int compare(Neuron o1, Neuron o2) {
    	Integer a = Integer.parseInt(""+o1.reinforcement);
    	Integer b = Integer.parseInt(""+o2.reinforcement);
        return a.compareTo(b);
    }
}
