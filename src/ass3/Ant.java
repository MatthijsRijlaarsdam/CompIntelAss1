package ass3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Niek on 10/21/2015.
 */
public class Ant {

    protected Tile tTile;
    protected int routeLength;
    protected ArrayList<Tile> tVisited;
    protected ArrayList<Integer> tActions;
    protected boolean tGoalReached;
    protected Tile tPreviousTile;
    protected ArrayList<Tile> recentlyVisited;
    protected int i;

    public Ant(Tile tile) {
        this.tTile = tile;
        tVisited = new ArrayList<Tile>();
        tActions = new ArrayList<Integer>();
        tGoalReached = false;
        recentlyVisited = new ArrayList<Tile>();
        i = 0;
    }

    public void addRecentlyVisited(Tile tile) {
        recentlyVisited.add(tile);
        if (recentlyVisited.size() > 1000) {
            recentlyVisited.remove(i);
            i++;
        }
    }

    public Tile getTile() {
        return tTile;
    }

    public void setTile(Tile tile) {
        tTile = tile;
    }

    public Tile gettPreviousTile() {
        return tPreviousTile;
    }

    public void settPreviousTile(Tile tile) {
        tPreviousTile = tile;
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
                if (MainMaze.noRouteYet) {
                    if (tiles.contains(tTile.getEastTile())) {
                        eastChance = 1 / total;
                    }
                } else {
                    eastPheromone = tTile.getEastTile().getPheromone();
                }
            }
            if (tTile.hasNorthTile()) {
                if (MainMaze.noRouteYet) {
                    if (tiles.contains(tTile.getNorthTile())) {
                        northChance = 1 / total;
                    }
                } else {
                    northPheromone = tTile.getNorthTile().getPheromone();
                }

            }
            if (tTile.hasWestTile()) {
                if (MainMaze.noRouteYet) {
                    if (tiles.contains(tTile.getWestTile())) {
                        westChance = 1 / total;
                    }
                }else {
                    westPheromone = tTile.getWestTile().getPheromone();
                }
            }
            if (tTile.hasSouthTile()) {
                if (MainMaze.noRouteYet) {
                    if (tiles.contains(tTile.getSouthTile())) {
                        southChance = 1 / total;
                    }
                } else {
                    southPheromone = tTile.getSouthTile().getPheromone();
                }
            }

            if (!MainMaze.noRouteYet) {
                total = eastPheromone + northPheromone + westPheromone + southPheromone;
                eastChance = eastPheromone / total;
                northChance = northPheromone / total;
                westChance = westPheromone / total;
                southChance = southPheromone / total;
            }
            double random = Math.random();
        if (random < eastChance) {
            //go east
            if (!tVisited.contains(tTile.getEastTile()))
            return 0;
        } else if (random < eastChance + northChance) {
            //go north
            if (!tVisited.contains(tTile.getNorthTile()))
                return 1;
        } else if (random < eastChance + northChance + westChance) {
            //go west
            if (!tVisited.contains(tTile.getWestTile()))
                return 2;
        } else {
            //go south
            if (!tVisited.contains(tTile.getSouthTile()))
                return 3;
        }
        return 4;
    }
}
