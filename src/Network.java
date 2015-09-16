import com.sun.corba.se.impl.orbutil.graph.Graph;
import com.sun.corba.se.impl.orbutil.graph.GraphImpl;
import com.sun.javafx.geom.*;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Niek on 9/15/2015.
 */
public class Network {

    private ArrayList<Neuron> neurons;
    private int cycles;

    public Network() {
        neurons = new ArrayList<Neuron>();
        cycles = 0;
    }

    public void addNeuron(Neuron neuron) {
        neurons.add(neuron);
    }

    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }


    public ArrayList<Edge> getEdges() {
        ArrayList<Edge> result = new ArrayList<Edge>();
        for (Neuron neuron: neurons) {
            for (Edge edge : neuron.getOutEdges()) {
                result.add(edge);
            }
        }
        return result;
    }

    public int getCycles() {
        return cycles;
    }
}
