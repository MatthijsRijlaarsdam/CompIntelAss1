package ass3;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Niek on 10/21/2015.
 */
public class MainMaze {

    public final static int MAX_NUMBER_OF_ITERATIONS = 100;
    public final static int NUMBER_OF_ANTS = 50;
    public final static int PHEROMONE_DROPPED = 1;
    public final static double EVAPORATION_PARAMETERS = 0.1;
    public final static double CONVERGION_CRITERION = 0;


    public static void main(String[] args) {
        MapParser parser = new MapParser("easy maze.txt");
        parser.parseMap();

    }
}
