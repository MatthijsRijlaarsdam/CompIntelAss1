package ass3;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Niek on 10/21/2015.
 */
public class MainMaze {

    public final static int MAX_NUMBER_OF_ITERATIONS = 100;
    public final static int NUMBER_OF_ANTS = 1000;
    public final static double PHEROMONE_DROPPED = 100;
    public final static double EVAPORATION_PARAMETERS = 0.1;
    public final static int CONVERGION_CRITERION = 1500;
    public final static String mapFile = "easy maze.txt";
    public final static String coordsFile = "easy coordinates.txt";

    protected Map tMap;
    protected ArrayList<Ant> tAnts;
    protected ArrayList<ArrayList<Integer>> actions;
    protected ArrayList<Integer> routeLengths;
    protected ArrayList<Integer> bestActions;
    protected int bestRoute;
    protected int antsReachedGoal;
    public static boolean noRouteYet;

    public MainMaze(Map map) {
        tMap = map;
        resetAnts();
        actions = new ArrayList<ArrayList<Integer>>();
        routeLengths = new ArrayList<Integer>();
        bestActions = new ArrayList<Integer>();
        bestRoute = Integer.MAX_VALUE;
        noRouteYet = true;
    }

    public void resetAnts() {
        tAnts = new ArrayList<Ant>();
        for (int i = 0; i < NUMBER_OF_ANTS; i++) {
            tAnts.add(new Ant(tMap.getStart()));
        }
        antsReachedGoal = 0;
    }

    public void settMap(Map map) {
        tMap = map;
    }

    public Map gettMap() {
        return tMap;
    }

    public void generateSolotions() {
        int action;
        for (int steps = 0; steps < CONVERGION_CRITERION; steps++) {
            for (Ant ant : tAnts) {
                ant.addVisited(ant.getTile());
                if (!ant.hasReachedGoal()) {
                    ant.settPreviousTile(ant.getTile());
                    action = ant.selectTile();
                    if (action != 4) {
                        ant.incrementRouteLength();
                        ant.addAction(action);
                    }
                    ant.getTile().moveAnt(ant, action);
                    if (!ant.gettVisited().contains(ant.getTile())) {
                        ant.addVisited(ant.getTile());
                    }
                    if (ant.getTile().equals(tMap.getEnd())) {
                        if (!ant.hasReachedGoal()) {
                            if (noRouteYet) {
                                noRouteYet = false;
                            }
                            antsReachedGoal++;
                            routeLengths.add(ant.getRouteLength());
                            actions.add(ant.gettActions());
                            ant.reachedGoal();
                            if (ant.getRouteLength() < bestRoute) {
                                bestActions = ant.gettActions();
                                bestRoute = ant.getRouteLength();
                                System.out.println("new best route: " + bestRoute);
                            }
                        }
                    }
                }
            }
        }
    }

    public void updatePheromone() {
        for (Ant ant : tAnts) {
                for (Tile tile : ant.gettVisited()) {
                    if (ant.hasReachedGoal()) {
                        tile.addPheromone((PHEROMONE_DROPPED / ant.getRouteLength()));
                    }
                }
        }
    }

    public void evaporate() {
        for (ArrayList<Tile> tiles : tMap.getTileList()) {
            for (Tile tile : tiles) {
                tile.evaporatePheromone(EVAPORATION_PARAMETERS);
            }
        }
    }

    public static void main(String[] args) {
        MapParser parser = new MapParser(mapFile, coordsFile);
        parser.parseMap();
        MainMaze main = new MainMaze(parser.getMap());
        double averageRouteLength = 0;
        for (int i = 0; i < MAX_NUMBER_OF_ITERATIONS; i++) {
            main.resetAnts();
            main.evaporate();
            main.generateSolotions();
            main.updatePheromone();
        }
    }
}
