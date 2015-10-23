package ass3;

import java.util.ArrayList;

/**
 * Created by Niek on 10/21/2015.
 */
public class Tile {

    protected int tY;
    protected Tile eastTile, northTile, westTile, southTile;
    protected ArrayList<Ant> antList;
    ArrayList<Tile> accessibleTiles;
    private boolean accessible;
    protected double tPheromone;

    public Tile(int x, int y,boolean _accessible) {
        tX = x;
        tY = y;
        accessible=_accessible;
        antList = new ArrayList<Ant>();
        accessibleTiles = new ArrayList<Tile>();
        tPheromone = 0;
    }

    public double getPheromone() {
        return tPheromone;
    }

    public void addPheromone(double tPheromone) {
        this.tPheromone += tPheromone;
    }

    public int gettX() {
        return tX;
    }

    public void settX(int tX) {
        this.tX = tX;
    }

    protected int tX;

    public int gettY() {
        return tY;
    }

    public void settY(int tY) {
        this.tY = tY;
    }

    public void addAnt(Ant ant) {
        antList.add(ant);
    }

    public void removeAnt(Ant ant) {
        antList.remove(ant);
    }

    public void updateAccessibleTiles() {
        if (hasEastTile() && eastTile.isAccessable()) {
            accessibleTiles.add(eastTile);
        }
        if (hasNorthTile() && northTile.isAccessable()) {

            accessibleTiles.add(northTile);
        }
        if (hasWestTile() && westTile.isAccessable()) {

            accessibleTiles.add(westTile);
        }
        if (hasSouthTile() && southTile.isAccessable()) {

            accessibleTiles.add(southTile);
        }
    }

    public ArrayList<Tile> getAccessibleTiles() {
        return accessibleTiles;
    }

    public boolean moveAnt(Ant ant, int direction) {
        int action = 4;
        switch (direction) {
            case 0:
                if (hasEastTile()) {
                        ant.setTile(eastTile);
                        eastTile.addAnt(ant);
                        this.removeAnt(ant);
                        action = 0;
                    return true;
                }
                break;
            case 1:
                if (hasNorthTile()) {
                    ant.setTile(northTile);
                    northTile.addAnt(ant);
                    this.removeAnt(ant);
                    action = 1;
                    return true;
                }
                break;
            case 2:
                if (hasWestTile()) {
                    ant.setTile(westTile);
                    westTile.addAnt(ant);
                    this.removeAnt(ant);
                    action = 2;
                    return true;
                }
                break;
            case 3:
                if (hasSouthTile()) {
                    ant.setTile(southTile);
                    southTile.addAnt(ant);
                    this.removeAnt(ant);
                    action = 3;
                    return true;
                }
                break;
        }
        return false;
    }

    public void evaporatePheromone(double constant) {
        tPheromone = (1 - constant) * tPheromone;
    }

    public boolean isAccessable() {
        return accessible;
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

    public boolean equals(Tile that) {
        if (that == null) {
            return false;
        }
        return (tX == that.gettX() && tY == that.gettY());
    }
}
