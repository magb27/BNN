package utils;

import java.util.Comparator;

import bnn.elements.Neuron;

public class NeuronIdSorter implements Comparator<Neuron> 
{
    @Override
    public int compare(Neuron o1, Neuron o2) {
    	Integer a = Integer.parseInt(""+o1.nid);
    	Integer b = Integer.parseInt(""+o2.nid);
        return a.compareTo(b);
    }
}
