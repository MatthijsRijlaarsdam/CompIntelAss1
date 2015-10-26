package ass3;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Niek on 10/21/2015.
 */
public class MainMaze {

    public final static int MAX_NUMBER_OF_ITERATIONS = 100;
    public final static int NUMBER_OF_ANTS = 100;
    public final static double PHEROMONE_DROPPED = 100;
    public final static double EVAPORATION_PARAMETERS = 0.20;
    public final static double CONVERGION_CRITERION = 15;
    public final static String mapFile = "hard maze.txt";
    public final static String coordsFile = "hard coordinates.txt";
    public final static String StartingPoint = "Start";
    public final static String EndPoint = "Product 1";

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
        bestActions = new ArrayList<Integer>();
        bestRoute = Integer.MAX_VALUE;
    }

    public void resetAnts() {
        tAnts = new ArrayList<Ant>();
        for (int i = 0; i < NUMBER_OF_ANTS; i++) {
            tAnts.add(new Ant(tMap.getStart()));
        }
        antsReachedGoal = 0;
        actions = new ArrayList<ArrayList<Integer>>();
        routeLengths = new ArrayList<Integer>();
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
                ant.getTile().moveAnt(ant, action);
                ant.addVisited(ant.getTile());
                if (action != 4) {
                    ant.incrementRouteLength();
                    ant.addAction(action);
                }
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
        int noImprovement = 0;
        while(i < MAX_NUMBER_OF_ITERATIONS&&!finished) {
            System.out.println("iteration no:" + i);

            main.resetAnts();
            main.evaporate();
            main.generateSolotions();
            main.updatePheromone();

            //check if the best route is improving
            if (main.prevBest == main.bestRoute) {
                noImprovement++;
            } else {
                noImprovement = 0;
            }
            if (noImprovement > CONVERGION_CRITERION) {
                finished=true;
            } else {
                //set new previous best route
                main.prevBest = main.bestRoute;
            }
            i++;
        }

        try {
            Writer writer = new FileWriter(new File("route"));
            writer.write(main.bestRoute + ";\n");
            writer.write(main.tMap.getStart().gettX() + ", " + main.tMap.getStart().gettY() + ";\n");
            for (int action : main.bestActions) {
                writer.write(action + ";");
            }
            writer.flush();
            writer.close();
        }
        catch(IOException exc){
        }
    }
}
