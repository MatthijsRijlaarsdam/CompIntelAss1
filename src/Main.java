import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Niek on 9/15/2015.
 */
public class Main {

    private static DataList data = new DataList();
    private static Network network = new Network();
    private static final int InputLayerSize = 5;
    private static final int HiddenLayerSize = 5;
    private static final int OutputLayerSize = 3;
    private static final int size = InputLayerSize + HiddenLayerSize + OutputLayerSize;

    public static void main(String[] args) throws FileNotFoundException{

        //Scan our data
        //readFiles();

        //Create neurons and add them to our Network
        while(network.getNeurons().size() < size) {
            Neuron neuron = new Neuron();
            ArrayList<Double> weights = new ArrayList<Double>();
            while (weights.size() < 10) {
                weights.add(Math.random());
            }
            neuron.setWeights(weights);
            network.addNeuron(neuron);
        }

        //Add all edges to our network
        for (int i = 0; i < InputLayerSize; i++) {
            for (int j = InputLayerSize; j < HiddenLayerSize + InputLayerSize; j++) {
                    Neuron from = network.getNeurons().get(i);
                    Neuron to = network.getNeurons().get(j);
                    from.addEdge(to);
                    if (i == 0) {
                        for (int k = InputLayerSize + HiddenLayerSize; k < size; k++) {
                            from = network.getNeurons().get(j);
                            to = network.getNeurons().get(k);
                            from.addEdge(to);
                        }
                    }
            }
        }

        //Verify we have the correct sizes
        System.out.println("EDGES SIZE:" + network.getEdges().size());
        System.out.println("NEURON SIZE: " + network.getNeurons().size());
    }

    public static void readFiles () throws FileNotFoundException{
        try {
            data.setFeatures(data.readFeatures("features.txt"));
            data.setUnknown(data.readFeatures("unknown.txt"));
            data.setTargets(data.readTargets("targets.txt"));
            } catch (FileNotFoundException e) {
            System.out.println("Document not found");
        }
    }
}
