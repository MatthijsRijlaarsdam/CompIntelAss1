import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

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
    private static ArrayList<Double> MSElist = new ArrayList<Double>();
    private static final double alpha = 0.1;

    public static void main(String[] args) throws FileNotFoundException{

        //Scan our data
        readFiles();

        //Verify we have the correct sizes
        System.out.println("EDGES SIZE:" + network.getEdges().size());
        System.out.println("NEURON SIZE: " + network.getNeurons().size());


        double stoppingError=1;
        int epochNo=0;
        //TRAIN

        while (stoppingError>0.065&&epochNo<1000) {
            epochNo++;
            stoppingError=0;
            double ETotal = 0;
            // Loop through % of features to train the network
            for (int i = 0; i < 5498; i++) {
                //Keep cycling until error is low enough

                //Input values of one feature array into the network
                //And Receive from network the output values
                ArrayList<Double> outputs = network.runNetwork(data.getFeatures().get(i));
                int target = data.getTargets().get(i);
                double[] targetlist = new double[7];
                targetlist[target - 1] = 1;
                double[] errorlist = new double[7];
                double totalerror = 0;
                for (int j = 0; j < 7; j++) {
                    double outputvalue = outputs.get(j);
                    double error = targetlist[j] - outputvalue;
                    totalerror += error * error;
                    double errorgradient = outputvalue * (1 - outputvalue) * error;
                    errorlist[j] = errorgradient;
                    network.getOutputLayer().get(j).setErrorgradient(errorgradient);
                }

                stoppingError+=(Math.sqrt(totalerror))/7;
                //Update weights of network
                network.updateEdgeWeights(errorlist, alpha);
                ETotal += totalerror / 7;
                MSElist.add(totalerror / 7);
            }
            MSElist.add(ETotal / 5498);

            stoppingError=stoppingError/5498;
          //  System.out.println(stoppingError);
            //(Update Threshholds
        }

        System.out.println("Needs Epochs: " + epochNo);
            //  Loop through % of features to validate network


        String config=(network.toString());
        validate(network);


        //VALIDATE



        //TEST


    }

    public static void validate(Network validationNetwork){
        double ETotal=0;
        String results="";
        for (int i = 5498; i < 6676; i++) {
            //Keep cycling until error is low enough

            //Input values of one feature array into the network
            //And Receive from network the output values
            ArrayList<Double> outputs = validationNetwork.runNetwork(data.getFeatures().get(i));
            //print outputs
            int output= outputs.indexOf(Collections.max(outputs))+1;
            results+= output+", ";

            int target = data.getTargets().get(i);
            double[] targetlist = new double[7];
            targetlist[target - 1] = 1;
            double[] errorlist = new double[7];
            double totalerror = 0;
            for (int j = 0; j < 7; j++) {
                double outputvalue = outputs.get(j);
                double error = targetlist[j] - outputvalue;
                totalerror += error * error;

            }

            //Update weights of network
            ETotal += totalerror / 7;
            MSElist.add(totalerror / 7);
        }
        MSElist.add(ETotal / 1178);

        System.out.println("VALIDATION MSE: " + ETotal / 1178);

        System.out.println(results);

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
