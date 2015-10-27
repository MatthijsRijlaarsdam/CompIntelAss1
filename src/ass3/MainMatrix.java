package ass3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Matthijs on 10/27/15.
 */
public class MainMatrix {

    public final static int MAX_NUMBER_OF_ITERATIONS = 100;
    public final static int NUMBER_OF_ANTS = 50;
    public final static double PHEROMONE_DROPPED = 100;
    public final static double EVAPORATION_PARAMETERS = 0.20;
    public final static double CONVERGION_CRITERION = 5;
    public final static String mapFile = "world maze.txt";
    public final static String coordsFile = "INSANE start-finish.txt";


    protected Map tMap;
    protected ArrayList<Ant> tAnts;
    protected ArrayList<ArrayList<Integer>> actions;
    protected ArrayList<Integer> routeLengths;
    protected ArrayList<Integer> bestActions;
    protected int bestRoute;
    protected int prevBest = Integer.MAX_VALUE;
    protected int antsReachedGoal;

    public MainMatrix(Map map) {
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


        int[][] locationArray = {{0, 91}, {0, 0}, {6, 36}, {10, 122}, {57, 5}, {89, 4}, {120, 9}, {32, 36}, {114, 56}, {14, 78}, {40, 68}, {62, 70}, {69, 63}, {42, 97}, {100, 94}, {112, 101}, {67, 113}, {124, 83}};

        int[][] distArray = new int[locationArray.length][locationArray.length];

        int startFinish = 0;
        for (int index = 0; index < locationArray.length; index++) {
            startFinish++;
            for (int finishIndex = startFinish; finishIndex < locationArray.length; finishIndex++) {
                int[] productStart = locationArray[index];
                int[] productFinish = locationArray[finishIndex];
                int xStart = productStart[0];
                int yStart = productStart[1];
                int xEnd = productFinish[0];
                int yEnd = productFinish[1];

                MapParser parser = new MapParser(mapFile, coordsFile);
                parser.parseMap();


                MainMatrix main = new MainMatrix(parser.getMap());
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

                distArray[index][finishIndex] = main.bestRoute;


            }


        }

        try {
            //route
            Writer writer = new FileWriter(new File("matrix.txt"));
            int newLineIndex = locationArray.length - 1;
            int i = 0;
            int zeroIndex = 0;
            distArray = MainMatrix.transpose(distArray);
            for (int[] row : distArray) {
                for (int dist : row) {
                    if (i == newLineIndex) {
                        writer.write("\n");
                        newLineIndex--;
                        i = 0;
                        zeroIndex++;
                    }
                    if (i == zeroIndex)
                        writer.write("0 ");
                    writer.write(String.valueOf(dist) + " ");
                    i++;
                }
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




