package ass3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Matthijs on 10/27/15.
 */
public class MainFinalRouter {


    public final static int MAX_NUMBER_OF_ITERATIONS = 100;
    public final static int NUMBER_OF_ANTS = 50;
    public final static double PHEROMONE_DROPPED = 100;
    public final static double EVAPORATION_PARAMETERS = 0.20;
    public final static double CONVERGION_CRITERION = 5;
    public final static String mapFile = "hard maze.txt";
    public final static String coordsFile = "INSANE start-finish.txt";


    protected Map tMap;
    protected ArrayList<Ant> tAnts;
    protected ArrayList<ArrayList<Integer>> actions;
    protected ArrayList<Integer> routeLengths;
    protected ArrayList<Integer> bestActions;
    protected int bestRoute;
    protected int prevBest = Integer.MAX_VALUE;
    protected int antsReachedGoal;

    public MainFinalRouter(Map map) {
        tMap = map;
        resetAnts();
        bestActions = new ArrayList<Integer>();
        bestRoute = Integer.MAX_VALUE;
        prevBest = Integer.MAX_VALUE;

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


        int[][] locationArray = {{0, 0}, {0, 19}, {11, 13}, {8, 39}, {11, 30}, {31, 25}, {60, 0}, {79, 0}, {62, 21}, {0, 50}, {15, 59}, {42, 36}, {47, 47}, {37, 50}, {4, 66}, {34, 78}, {63, 65}, {75, 72}, {78, 60}, {58, 55}
        };
        int[] productArray = {1, 2, 7, 5, 14, 16, 4, 9, 18, 8, 10, 15, 12, 13, 6, 11, 3, 17};
        ArrayList<ArrayList<Integer>> actionArray = new ArrayList<ArrayList<Integer>>();

        int startFinish = 0;
        for (int index = 0; index < locationArray.length - 1; index++) {

            int[] productStart = locationArray[index];
            int[] productFinish = locationArray[index + 1];
            int xStart = productStart[0];
            int yStart = productStart[1];
            int xEnd = productFinish[0];
            int yEnd = productFinish[1];

            MapParser parser = new MapParser(mapFile, coordsFile);
            parser.parseMap();


            MainFinalRouter main = new MainFinalRouter(parser.getMap());
            main.tMap.setStart(main.tMap.getTileAt(xStart, yStart));
            main.tMap.setEnd(main.tMap.getTileAt(xEnd, yEnd));
            boolean finished = false;
            int i = 0;
            int noImprovement = 0;
            while (i < MAX_NUMBER_OF_ITERATIONS && !finished) {
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
                    finished = true;
                } else {
                    //set new previous best route
                    main.prevBest = main.bestRoute;
                }
                i++;
            }

            actionArray.add(main.bestActions);

        }

        try {
            //route
            Writer writer = new FileWriter(new File("finalRoute.txt"));

            for (ArrayList<Integer> route : actionArray) {
                for (Integer i : route) {
                    writer.write(String.valueOf(i) + ";\n");
                }
                if(actionArray.indexOf(route)!=actionArray.size()-1)
                    writer.write("take product #" + productArray[actionArray.indexOf(route)] + ";\n");


            }


            writer.flush();
            writer.close();


        } catch (IOException exc) {
            exc.printStackTrace();
        }


    }

    public static int[][] transpose(int[][] original) {

        // transpose
        if (original.length > 0) {
            for (int i = 0; i < original[0].length; i++) {
                for (int j = 0; j < original.length; j++) {
                    original[i][j] = original[j][i];
                }
            }
        }
        return original;
    }


}
