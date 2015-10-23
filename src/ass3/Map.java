package ass3;

import java.util.ArrayList;

/**
 * Created by Niek on 10/21/2015.
 */
public class Map {
    private int sizeX;
    private int sizeY;
    private ArrayList<ArrayList<Tile>> tileList;
    private Tile start, end;

    public Map(int _sizeX,int _sizeY) {
        tileList = new ArrayList<ArrayList<Tile>>();
        sizeX=_sizeX;
        sizeY=_sizeY;
        for (int i=0;i<sizeX;i++){
            tileList.add(new ArrayList<Tile>());
        }
    }

    public ArrayList<ArrayList<Tile>> getTileList() {
        return tileList;
    }
    public void setStart(Tile start) {
        this.start = start;
    }

    public void setEnd(Tile end) {
        this.end = end;
    }

    public Tile getTileAt(int x, int y){
        if(x>=0&&y>=0&&x<tileList.size()) {
            for (Tile tile : tileList.get(x)) {
                if (tile.gettY() == y)
                    return tile;
            }
        }
        return null;
    }

    public Tile getStart() {
        return start;
    }

    public Tile getEnd() {
        return end;
    }

    public void connectTiles(){
        for (int x=0; x<sizeX; x++) {
            for(Tile tile: tileList.get(x)){
                //north tile
                if (tile.gettY() > 0) {
                    tile.setNorthTile(getTileAt(tile.gettX(), tile.gettY() - 1));
                }
                //east tile
                if (tile.gettX() < sizeX) {
                    tile.setEastTile(getTileAt(tile.gettX() + 1, tile.gettY()));
                }

                //south tile
                if (tile.gettY() < sizeY) {
                    tile.setSouthTile(getTileAt(tile.gettX(), tile.gettY() + 1));
                }
                //west tile
                if (tile.gettX() > 0) {
                    tile.setWestTile(getTileAt(tile.gettX() - 1, tile.gettY()));
                }
            }
        }

        for (int x=0; x<sizeX; x++) {
            for(Tile tile:tileList.get(x)) {
                tile.updateAccessibleTiles();
            }
        }

    }

    public void addTile(Tile tile){
        tileList.get(tile.gettX()).add(tile);


    }
}
