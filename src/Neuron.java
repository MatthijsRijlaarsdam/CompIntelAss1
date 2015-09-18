import com.sun.javafx.geom.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

/**
 * Created by Niek on 9/15/2015.
 */
public class Neuron {

    private ArrayList<Edge> inEdges;
    private ArrayList<Edge> outEdges;
    private double value;


    public Neuron() {
        this.inEdges = new  ArrayList<Edge>();
        this.outEdges = new  ArrayList<Edge>();
        value = 0;
    }


    public Neuron(ArrayList<Double> weights, ArrayList<Edge> inEdges, ArrayList<Edge> outEdges) {
        this.inEdges = inEdges;
        this.outEdges = outEdges;
    }


    /**
     * Adds an edge starting FROM this and TO the parameter
     * @param neuron
     * @return
     */
    public Edge addEdge(Neuron neuron) {
        Edge e = new Edge(this, neuron);
        outEdges.add(e);
        neuron.inEdges.add(e);
        return e;
    }

    public ArrayList<Edge> getInEdges() {
        return inEdges;
    }

    public ArrayList<Edge> getOutEdges() {
        return outEdges;
    }

    public ArrayList<Edge> getAllEdges() {
        ArrayList<Edge> result = new ArrayList<Edge>();
        for (Edge edge: inEdges) {
            result.add(edge);
        }
        for (Edge edge: outEdges) {
            result.add(edge);
        }
        return result;
    }

    public boolean equals(Object other) {
        Neuron neuron = (Neuron) other;
        return inEdges.equals(neuron.getInEdges()) && outEdges.equals(neuron.getOutEdges());
    }


    public void setValue(double value) {
        this.value = value;
    }
}
