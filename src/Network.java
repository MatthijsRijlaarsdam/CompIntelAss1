import com.sun.corba.se.impl.orbutil.graph.Graph;
import com.sun.corba.se.impl.orbutil.graph.GraphImpl;
import com.sun.javafx.geom.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by Niek on 9/15/2015.
 */
public class Network {

    private ArrayList<ArrayList<Neuron>>neuronsLayers;
    private int cycles;
    private int noOfHidden;

    public Network(int input, int output, int hidden, int _noOfHidden) {
        noOfHidden = _noOfHidden;
        neuronsLayers = new ArrayList<ArrayList<Neuron>>();

        //create input layer
        ArrayList<Neuron> inputLayer = new ArrayList<Neuron>();
        for (int i = 0; i < input; i++) {
            Neuron neuron = new Neuron();
            ArrayList<Double> weights = new ArrayList<Double>();
            neuron.setValue(1);
            inputLayer.add(neuron);
        }
        neuronsLayers.add(inputLayer);

        //create hidden layers
        for (int i = 0; i < noOfHidden; i++) {
            ArrayList<Neuron> hiddenLayer = new ArrayList<Neuron>();
            for (int j = 0; j < hidden; j++) {
                Neuron neuron = new Neuron();
                hiddenLayer.add(neuron);
            }
            neuronsLayers.add(hiddenLayer);
        }

        //create output layers
        ArrayList<Neuron> outputLayer = new ArrayList<Neuron>();
        for (int i = 0; i < output; i++) {
            Neuron neuron = new Neuron();
            outputLayer.add(neuron);
        }
        neuronsLayers.add(outputLayer);
        cycles = 0;


      //  System.out.println("LAYERS: " + neuronsLayers.size());
      //  System.out.println("INPUT: " + inputLayer.size());
      //  System.out.println("HIDDEN: " + neuronsLayers.get(1).size());
      //  System.out.println("OUTPUT: " + outputLayer.size());

        //Add edges

        //Add Edges to and from input/output layer
        for (int i = 0; i < inputLayer.size(); i++) {
            ArrayList<Neuron> firstHiddenLayer = neuronsLayers.get(1);
            for (int j = 0; j < firstHiddenLayer.size(); j++) {
                Neuron from = inputLayer.get(i);
                Neuron to = firstHiddenLayer.get(j);
                from.addEdge(to);
                if (i == 0) {
                    ArrayList<Neuron> lastHiddenLayer = neuronsLayers.get(noOfHidden);
                    for (int k = 0; k < outputLayer.size(); k++) {
                        from = lastHiddenLayer.get(j);
                        to = outputLayer.get(k);
                        from.addEdge(to);
                    }
                }
            }
        }
    }

    public void addNeuron(Neuron neuron, int layer) {
        neuronsLayers.get(layer).add(neuron);
    }

    public ArrayList<Neuron> getNeurons() {
        ArrayList<Neuron> neurons = new ArrayList<Neuron>();
        for (ArrayList<Neuron> neuronLayer : neuronsLayers) {
            for (Neuron neuron : neuronLayer) {
                neurons.add(neuron);
            }

        }
        return neurons;

    }


    public ArrayList<Edge> getEdges() {
        ArrayList<Edge> result = new ArrayList<Edge>();
        for (ArrayList<Neuron> neuronLayer: neuronsLayers) {
            for(Neuron neuron: neuronLayer) {
                for (Edge edge : neuron.getOutEdges()) {
                    result.add(edge);
                }
            }
        }
        return result;
    }

    public ArrayList<ArrayList<Neuron>> getNeuronsLayers() {
        return neuronsLayers;
    }

    public ArrayList<Neuron> getOutputLayer() {
        return neuronsLayers.get(noOfHidden + 1);
    }

    public int getCycles() {
        return cycles;
    }

    public ArrayList<Double> runNetwork(ArrayList<Double> features) {
        //Set values for input neurons
        for (int i = 0; i < neuronsLayers.get(0).size(); i++) {
            neuronsLayers.get(0).get(i).setValue(features.get(i));
        }

        //UPDATEVALUE METHOD PER LAYER
        for (int i=1; i<neuronsLayers.size();i++){
            ArrayList<Neuron> column= neuronsLayers.get(i);
            for (Neuron neuron:column){
                neuron.updateValue();
            }

        }


        //Set Output Neuron values into an arraylist of doubles
        ArrayList<Double> outputs = new ArrayList<Double>();
        for (int i = 0; i < getOutputLayer().size(); i++) {
            outputs.add(getOutputLayer().get(i).getValue());
        }
        return outputs;
    }

    public void updateEdgeWeights(double[] errorgradient, double alpha) {

        //update hidden to output weights
        ArrayList<Neuron> outputLayer = neuronsLayers.get(noOfHidden+1);
        for (int i = 0; i < outputLayer.size(); i++) {
            Neuron neuron = outputLayer.get(i);
            ArrayList<Edge> inEdges = neuron.getInEdges();
            for (Edge edge : inEdges) {
                edge.setWeight(edge.getWeight() + alpha * edge.opposite(neuron).getValue() * errorgradient[i]);
            }
        }


        for(int j=0; j<noOfHidden;j++) {
            //update input to hidden weights
            ArrayList<Neuron> hiddenLayer = neuronsLayers.get(noOfHidden-j);
            for (int i = 0; i < hiddenLayer.size(); i++) {
                Neuron neuron = hiddenLayer.get(i);
                double newgradient = 0;
                for (Edge edge : neuron.getOutEdges()) {
                    Neuron opposite = edge.opposite(neuron);
                    newgradient += opposite.getErrorgradient() * edge.getWeight();
                }
                newgradient *= neuron.getValue() * (1 - neuron.getValue());
                for (Edge edge : neuron.getInEdges()) {
                    edge.setWeight((edge.getPrevDelta() * .95) + edge.getWeight() + (alpha * edge.opposite(neuron).getValue() * newgradient));
                    edge.setPrevDelta(alpha * edge.opposite(neuron).getValue() * newgradient);

                }
            }
        }
    }

    public void setWeigthsFromString(String networkConfig) {
       networkConfig= networkConfig.replaceAll("\\s+","");

        Scanner scanner = new Scanner(networkConfig).useDelimiter(",");




        for (int i=1; i<neuronsLayers.size();i++){
            ArrayList<Neuron> column=neuronsLayers.get(i);
            for (Neuron neuron :column){
                for (Edge inEdge : neuron.getInEdges()){
                    inEdge.setWeight(scanner.nextDouble());
                }


            }


        }

    }
    public String toString(){
        String result="";
        for (int i=1;i<neuronsLayers.size();i++){
         ArrayList<Neuron> column=neuronsLayers.get(i);
            for (Neuron neuron :column){
                String neuronString= "";
                for (Edge inEdge : neuron.getInEdges()){
                    neuronString+=inEdge.getWeight()+", ";
                }
                neuronString+="\n";

                result +=neuronString;
            }
            result +="\n\n";


        }
        return result;




    }

}
