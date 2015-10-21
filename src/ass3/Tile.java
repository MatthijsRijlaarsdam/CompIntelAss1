package ass3;

import java.util.ArrayList;

/**
 * Created by Niek on 10/21/2015.
 */
public class Tile {

    protected int tX;
    protected int tY;
    protected Tile eastTile, northTile, westTile, southTile;
    protected ArrayList<Ant> antList;

    public Tile(int x, int y) {
        tX = x;
        tY = y;
        antList = new ArrayList<Ant>();
    }

    public void addAnt(Ant ant) {
        antList.add(ant);
    }

    public void removeAnt(Ant ant) {
        antList.remove(ant);
    }

    public int moveAnt(Ant ant, String direction) {
        int action =4;
        switch (direction) {
            case "east":
                if (hasEastTile()) {
                    eastTile.addAnt(ant);
                    this.removeAnt(ant);
                    action = 0;
                }
                break;
            case "north":
                if (hasNorthTile()) {
                    northTile.addAnt(ant);
                    this.removeAnt(ant);
                    action = 1;
                }
                break;
            case "west":
                if (hasWestTile()) {
                    westTile.addAnt(ant);
                    this.removeAnt(ant);
                    action = 2;
                }
                break;
            case "south":
                if (hasWestTile()) {
                    westTile.addAnt(ant);
                    this.removeAnt(ant);
                    action = 2;
                }
                break;
        }

        return action;
    }

    public boolean hasEastTile() {
        return eastTile != null;
    }

    public boolean hasNorthTile() {
        return northTile != null;
    }

    public boolean hasWestTile() {
        return westTile != null;
    }

    public boolean hasSouthTile() {
        return southTile != null;
    }

    public Tile getEastTile() {
        return eastTile;
    }

    public void setEastTile(Tile eastTile) {
        this.eastTile = eastTile;
    }

    public Tile getNorthTile() {
        return northTile;
    }

    public void setNorthTile(Tile northTile) {
        this.northTile = northTile;
    }

    public Tile getWestTile() {
        return westTile;
    }

    public void setWestTile(Tile westTile) {
        this.westTile = westTile;
    }

    public Tile getSouthTile() {
        return southTile;
    }

    public void setSouthTile(Tile southTile) {
        this.southTile = southTile;
    }
}
