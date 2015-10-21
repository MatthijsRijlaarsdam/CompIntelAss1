package ass3;

import java.util.ArrayList;

/**
 * Created by Niek on 10/21/2015.
 */
public class Main {

    public final static int MAX_NUMBER_OF_ITERATIONS = 100;
    public final static int NUMBER_OF_ANTS = 50;
    public final static int PHEROMONE_DROPPED = 1;
    public final static double EVAPORATION_PARAMETERS = 0.1;
    public final static double CONVERGION_CRITERION = 0;

    protected Map map;

    public Main() {
        map = new Map();

    }
}
