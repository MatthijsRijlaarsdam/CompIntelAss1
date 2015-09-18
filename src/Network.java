import com.sun.corba.se.impl.orbutil.graph.Graph;
import com.sun.corba.se.impl.orbutil.graph.GraphImpl;
import com.sun.javafx.geom.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

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


        System.out.println("LAYERS: " + neuronsLayers.size());
        System.out.println("INPUT: " + inputLayer.size());
        System.out.println("HIDDEN: " + neuronsLayers.get(1).size());
        System.out.println("OUTPUT: " + outputLayer.size());

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
}
