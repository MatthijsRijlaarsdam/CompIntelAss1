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
    private static final int InputLayerSize = 10;
    private static final int HiddenLayerSize = 10;
    private static final int OutputLayerSize = 7;
    private static final int _noOfHidden = 1;
    private static Network network = new Network(InputLayerSize, OutputLayerSize, HiddenLayerSize, _noOfHidden);
    private static final int size = InputLayerSize + HiddenLayerSize + OutputLayerSize;
    private final int featuresize = 5498;

    public static void main(String[] args) throws FileNotFoundException{

        //Scan our data
        readFiles();

        //Verify we have the correct sizes
        System.out.println("EDGES SIZE:" + network.getEdges().size());
        System.out.println("NEURON SIZE: " + network.getNeurons().size());

        // Loop through % of features to train the network
        for (int i = 0; i < 5498; i++) {

        }

            //Input values of one feature array into the network
            //Receive from network the output values
            //Update weights of network
            //(Update Threshholds

        //  Loop through % of features to validate network



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
