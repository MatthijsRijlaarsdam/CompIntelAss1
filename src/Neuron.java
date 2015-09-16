import com.sun.javafx.geom.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Niek on 9/15/2015.
 */
public class Neuron {

    private ArrayList<Double> weights;
    private ArrayList<Edge> inEdges;
    private ArrayList<Edge> outEdges;


    public Neuron() {
        this.weights = new ArrayList<Double>();
        this.inEdges = new  ArrayList<Edge>();
        this.outEdges = new  ArrayList<Edge>();
    }


    public Neuron(ArrayList<Double> weights, ArrayList<Edge> inEdges, ArrayList<Edge> outEdges) {
        this.weights = weights;
        this.inEdges = inEdges;
        this.outEdges = outEdges;
    }


    /**
     * Adds an edge starting FROM this and TO the parameter
     * @param neuron
     * @return
     */
    public Neuron addEdge(Neuron neuron) {
        Edge e = new Edge(this, neuron);
        outEdges.add(e);
        neuron.inEdges.add(e);
        return this;
    }

    public ArrayList<Double> getWeights() {
        return weights;
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

    /**
     * Add an entire list of weights given in the parameter
     * @param weights
     */
    public void setWeights(ArrayList<Double> weights) {
        this.weights = weights;
    }

    /**
     * Change a single weight, with:
     * @param position as position in the list, and
     * @param value for the value to be placed
     */
    public void setWeight(int position, double value) {
        weights.set(position, value);
    }

    public String toString() {
        return weights.toString();
    }
}
