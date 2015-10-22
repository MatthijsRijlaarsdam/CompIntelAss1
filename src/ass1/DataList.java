package ass1;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Niek on 9/15/2015.
 */
public class DataList {

    private ArrayList<ArrayList<Double>> features;
    private ArrayList<ArrayList<Double>> unknown;
    private ArrayList<Integer> targets;

    public DataList() {
        features = new ArrayList<ArrayList<Double>>();
        unknown = new ArrayList<ArrayList<Double>>();
        targets = new ArrayList<Integer>();
    }

    public ArrayList<ArrayList<Double>> readFeatures(String s) throws FileNotFoundException{
        Scanner scanner = new Scanner(new File(s));
        ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
        while (scanner.hasNextLine()){
            String str = scanner.nextLine();
            ArrayList<Double> numbers = new ArrayList<Double>();
            String[] splitLine= str.split(",");
            for (String ans :splitLine){
                numbers.add(Double.parseDouble(ans));
            }
                result.add(numbers);
        }
        scanner.close();
        return result;
    }

    public ArrayList<Integer> readTargets(String s) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(s));
        ArrayList<Integer> result = new ArrayList<Integer>();
        while (scanner.hasNext()){
            result.add(scanner.nextInt());
        }
        scanner.close();
        return result;
    }

    public ArrayList<ArrayList<Double>> getFeatures() {
        return features;
    }

    public ArrayList<ArrayList<Double>> getUnknown() {
        return unknown;
    }

    public ArrayList<Integer> getTargets() {
        return targets;
    }

    public void setFeatures(ArrayList<ArrayList<Double>> list) {
        features = list;
    }

    public void setUnknown(ArrayList<ArrayList<Double>> list) {
        unknown = list;
    }

    public void setTargets(ArrayList<Integer> list) {
        targets = list;
    }
}
