package ass1;

import java.util.ArrayList;

/**
 * Created by Niek on 9/15/2015.
 */
public class Neuron {

    private ArrayList<Edge> inEdges;
    private ArrayList<Edge> outEdges;
    private double value;
    private double errorgradient;

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    private double threshold;



    public Neuron() {
        this.inEdges = new  ArrayList<Edge>();
        this.outEdges = new  ArrayList<Edge>();
        threshold=1;
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


    public void updateValue(){

        double sum=0;

        for (Edge input: inEdges){
            sum+= input.getFrom().getValue()*input.getWeight();
        }

        sum-= threshold;

        //sigmoid
        sum= 1/(1+Math.exp(-sum));

        value=sum;


    }
    public double getValue(){ return value;}

    public void setValue(double value) {
        this.value = value;
    }

    public void setErrorgradient(double errorgradient) {
        this.errorgradient = errorgradient;
    }

    public double getErrorgradient() {
        return errorgradient;
    }
}
