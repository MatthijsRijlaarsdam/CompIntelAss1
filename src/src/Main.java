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
    private static int HiddenLayerSize = 25;
    private static final int OutputLayerSize = 7;
    private static final int _noOfHidden = 1;
    private static double prevMSE;
    private static Network network;
    private static ArrayList<Double> MSElist = new ArrayList<Double>();
    private static final double alpha = 0.1;


    public static void main(String[] args) throws FileNotFoundException{


        //Scan our data
        readFiles();
        //Create a network
        network = new Network(InputLayerSize, OutputLayerSize, HiddenLayerSize, _noOfHidden);

            double stoppingError = 1;
            prevMSE = 2;
            int epochNo = 0;

            //Train the network until our MSE does not decrease anymore by a sizeable amount
            while ((prevMSE - stoppingError > 0.000005) && epochNo < 1000) {
                epochNo++;
                double ETotal = 0;
                // Loop through % of features to train the network
                for (int i = 0; i < 5498; i++) {
                    //Keep cycling until error is low enough

                    //Input values of one feature array into the network
                    //And Receive from network the output values
                    ArrayList<Double> outputs = network.runNetwork(data.getFeatures().get(i));

                    //Translate outputs into a useable format
                    int target = data.getTargets().get(i);
                    double[] targetlist = new double[7];
                    targetlist[target - 1] = 1;
                    double[] errorlist = new double[7];
                    double totalerror = 0;
                    //Calculate total error over the generated outputs when
                    //compared to the desired outputs
                    for (int j = 0; j < 7; j++) {
                        double outputvalue = outputs.get(j);
                        double error = targetlist[j] - outputvalue;
                        totalerror += error * error;
                        double errorgradient = outputvalue * (1 - outputvalue) * error;
                        errorlist[j] = errorgradient;
                        network.getOutputLayer().get(j).setErrorgradient(errorgradient);
                    }

                    //Update weights of network
                    network.updateEdgeWeights(errorlist, alpha);
                    ETotal += totalerror / 7;
                }
                //keep track of the MSE in this epoch
                MSElist.add(ETotal / 5498);

                //Validate trained network
                prevMSE = stoppingError;
                stoppingError = validate(network, 5498, 6676, false);

                System.out.println(epochNo+","+stoppingError);

            }

            //TEST trained network
            double testMSE = validate(network, 6676, data.getFeatures().size(), true);

            //Method to provide us the solution to the unknown list of values.
            printUnknown(network);
    }

    /**
     * prints the unknown results
     * @param validationNetwork
     */

    public static void printUnknown(Network validationNetwork){
        String results="";
        for (int i = 0; i < data.getUnknown().size(); i++) {

            ArrayList<Double> outputs = network.runNetwork(data.getUnknown().get(i));
            int output= outputs.indexOf(Collections.max(outputs))+1;
            results+= output+", ";

        }

        System.out.println(results);



    }

    /**
     * validate validates our network, meaning it calculates our error marge.
     * @param validationNetwork
     * @param start
     * @param end
     * @param printResult
     * @return
     */
    public static double validate(Network validationNetwork, int start, int end, boolean printResult){
        double ETotal=0;
        String results="";
        for (int i = start; i < end; i++) {
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
            //MSElist.add(totalerror / 7);
        }
        if (printResult)
            System.out.println(results);

        return ETotal / (end-start);



    }


    /**
     * method to read all files and put them into arraylists
     * @throws FileNotFoundException
     */
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
