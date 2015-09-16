import com.sun.corba.se.impl.orbutil.graph.Graph;
import com.sun.corba.se.impl.orbutil.graph.GraphImpl;
import com.sun.javafx.geom.*;

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
        noOfHidden=_noOfHidden;
        neuronsLayers = new ArrayList<ArrayList<Neuron>>();

        //create input layer
        ArrayList<Neuron> inputLayer= new ArrayList<Neuron>();
        for (int i=0; i<input; i++){
            Neuron neuron= new Neuron();
            ArrayList<Double> weights= new ArrayList<Double>();
            neuron.setWeight(0,1);
            inputLayer.add(neuron);
        }
        neuronsLayers.add(inputLayer);

        //create hidden layers
        for(int i=0; i<noOfHidden; i++){
            ArrayList<Neuron> hiddenLayer= new ArrayList<Neuron>();
            for (int j=0; j<hidden; j++){
                Neuron neuron= new Neuron();
                hiddenLayer.add(neuron);

            }
            neuronsLayers.add(hiddenLayer);
        }

        //create output layers
        ArrayList<Neuron> outputLayer= new ArrayList<Neuron>();
        for (int i=0; i<output; i++){
            Neuron neuron= new Neuron();

            outputLayer.add(neuron);
        }
        neuronsLayers.add(outputLayer);


        cycles = 0;
    }

    public void connectNeurons(){
        if (!neuronsLayers.isEmpty()){





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

    public int getCycles() {
        return cycles;
    }
}
