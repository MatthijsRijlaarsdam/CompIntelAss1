package ass3;

import java.util.ArrayList;

/**
 * Created by Niek on 10/21/2015.
 */
public class Ant {

    protected Tile tTile;
    protected int routeLength;
    protected ArrayList<Tile> tVisited;
    protected ArrayList<Integer> tActions;
    protected boolean tGoalReached;

    public Ant(Tile tile) {
        this.tTile = tile;
        tVisited = new ArrayList<Tile>();
        tActions = new ArrayList<Integer>();
        tGoalReached = false;
    }

    public Tile getTile() {
        return tTile;
    }

    public void setTile(Tile tile) {
        tTile = tile;
    }

    public int getRouteLength() {
        return routeLength;
    }

    public void incrementRouteLength() {
        this.routeLength++;
    }

    public ArrayList<Tile> gettVisited() {
        return tVisited;
    }

    public void addVisited(Tile tile) {
        this.tVisited.add(tile);
    }

    public void addAction(int action) {
        tActions.add(action);
    }

    public ArrayList<Integer> gettActions() {
        return tActions;
    }

    public boolean hasReachedGoal() {
        return tGoalReached;
    }

    public void reachedGoal() {
        tGoalReached = true;
    }

    public double calculateChance(double pheromone, double total) {
        return (pheromone / total);
    }

    public int selectTile() {

        double eastPheromone = 0, northPheromone = 0, westPheromone = 0, southPheromone = 0;
        double eastChance =0, northChance=0, westChance=0, southChance=0;

        if (tTile.hasEastTile() && tTile.getEastTile().isAccessable()) {
            eastPheromone = tTile.getEastTile().getPheromone();
        }
        if (tTile.hasNorthTile() && tTile.getNorthTile().isAccessable()) {
            northPheromone = tTile.getNorthTile().getPheromone();
        }

        if (tTile.hasWestTile() && tTile.getWestTile().isAccessable()) {
            westPheromone = tTile.getWestTile().getPheromone();
        }

        if (tTile.hasSouthTile() && tTile.getSouthTile().isAccessable()) {
            southPheromone = tTile.getSouthTile().getPheromone();
        }

        ArrayList<Tile> tiles = tTile.getAccessibleTiles();
        double total = tiles.size();
            if (tTile.hasEastTile()) {
                if (MainMaze.start) {
                    if (tiles.contains(tTile.getEastTile())) {
                        eastChance = 1 / total;
                    }
                } else {
                    eastPheromone = tTile.getEastTile().getPheromone();
                }
            }
            if (tTile.hasNorthTile()) {
                if (MainMaze.start) {
                    if (tiles.contains(tTile.getNorthTile())) {
                        northChance = 1 / total;
                    }
                } else {
                    northPheromone = tTile.getNorthTile().getPheromone();
                }

            }
            if (tTile.hasWestTile()) {
                if (MainMaze.start) {
                    if (tiles.contains(tTile.getWestTile())) {
                        westChance = 1 / total;
                    }
                }else {
                    westPheromone = tTile.getWestTile().getPheromone();
                }
            }
            if (tTile.hasSouthTile()) {
                if (MainMaze.start) {
                    if (tiles.contains(tTile.getSouthTile())) {
                        southChance = 1 / total;
                    }
                } else {
                    southPheromone = tTile.getSouthTile().getPheromone();
                }
            }

            if (!MainMaze.start) {
                total = eastPheromone + northPheromone + westPheromone + southPheromone;
                eastChance = eastPheromone / total;
                northChance = northPheromone / total;
                westChance = westPheromone / total;
                southChance = southPheromone / total;
            }
        double random = Math.random();
        if (random < eastChance) {
            //go east
            return 0;
        } else if (random < eastChance + northChance) {
            //go north
            return 1;
        } else if (random < eastChance + northChance + westChance) {
            //go west
            return 2;
        } else {
            //go south
            return 3;
        }
    }
}
