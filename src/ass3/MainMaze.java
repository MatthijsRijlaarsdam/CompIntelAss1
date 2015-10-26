package ass3;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Niek on 10/21/2015.
 */
public class MainMaze {

    public final static int MAX_NUMBER_OF_ITERATIONS = 100;
    public final static int NUMBER_OF_ANTS = 1;
    public final static double PHEROMONE_DROPPED = 50;
    public final static double EVAPORATION_PARAMETERS = 0.05;
    public final static double CONVERGION_CRITERION = .05;
    public final static String mapFile = "medium maze.txt";
    public final static String coordsFile = "medium coordinates.txt";

    protected Map tMap;
    protected ArrayList<Ant> tAnts;
    protected ArrayList<ArrayList<Integer>> actions;
    protected ArrayList<Integer> routeLengths;
    protected ArrayList<Integer> bestActions;
    protected int bestRoute;
    protected int prevBest=Integer.MAX_VALUE;
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
        for (Ant ant : tAnts) {
            while (!ant.hasReachedGoal()) {
                action = ant.selectTile();
                ant.incrementRouteLength();
                ant.addAction(action);
                ant.getTile().moveAnt(ant, action);
                ant.addVisited(ant.getTile());
                checkFinished(ant);

            }
        }
        
    }

    public void checkFinished(Ant ant) {
        if (ant.getTile().equals(tMap.getEnd())) {
            if (!ant.hasReachedGoal()) {

                antsReachedGoal++;
                routeLengths.add(ant.getRouteLength());
                actions.add(ant.gettActions());
                ant.reachedGoal();
                getBestAnt(ant);

            }
        }

    }


    public void getBestAnt(Ant ant) {
        if (ant.getRouteLength() < bestRoute) {
            bestActions = ant.gettActions();
            prevBest=bestRoute;
            bestRoute = ant.getRouteLength();
            System.out.println(bestRoute + ";");
            System.out.println(tMap.getStart().gettX() + ", " + tMap.getStart().gettY() + ";");
            for (int i : bestActions) {
                System.out.print(i + ";");
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
        boolean finished=false;
        int i=0;
        while(i < MAX_NUMBER_OF_ITERATIONS&&!finished) {
            if(i>0)
                noRouteYet=true;

            if(main.prevBest/main.bestRoute-1<CONVERGION_CRITERION)
                finished=true;

            main.resetAnts();
            main.evaporate();
            main.generateSolotions();
            main.updatePheromone();
            i++;
        }
    }
}
