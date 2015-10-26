package ass3;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Niek on 10/21/2015.
 */
public class MainMaze {

    public final static int MAX_NUMBER_OF_ITERATIONS = 100;
    public final static int NUMBER_OF_ANTS = 10;
    public final static double PHEROMONE_DROPPED = 200;
    public final static double EVAPORATION_PARAMETERS = 0.30;
    public final static double CONVERGION_CRITERION = 0.10;
    public final static String mapFile = "INSANE";
    public final static String coordsFile = "INSANE start-finish.txt";

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
        noRouteYet = true;
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
                try {
                    Writer writer = new FileWriter(new File("route"));
                    writer.write(ant.getRouteLength()+ ";\n");
                    writer.write(tMap.getStart().gettX() + ", " + tMap.getStart().gettY() + ";\n");
                    for (int i : ant.gettActions()) {
                        writer.write(i + ";");
                    }
                    writer.flush();
                    writer.close();
                }
                catch(IOException exc){
                }



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
            System.out.println("iteration no:" + i);
            if(i>0)
                noRouteYet=true;

            //if(main.prevBest/main.bestRoute-1<CONVERGION_CRITERION)

            //Calculate total route length
            main.resetAnts();
            main.evaporate();
            main.generateSolotions();
            main.updatePheromone();

            int totalroute = 0;
            for (int routelength : main.routeLengths) {
                totalroute += routelength;
            }
            double averageroutelength = totalroute / main.routeLengths.size();
            System.out.println("average: " + averageroutelength);
            System.out.println("best so far: " + main.bestRoute);
            if ((averageroutelength - main.bestRoute) /  averageroutelength < CONVERGION_CRITERION) {
                finished=true;

            }

            //set new previous best route
            i++;
        }
    }
}
