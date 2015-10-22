package ass3;

import java.util.ArrayList;

/**
 * Created by Niek on 10/21/2015.
 */
public class Map {
    private int sizeX;
    private int sizeY;
    private ArrayList<ArrayList<Tile>> tileList;

    public Map(int _sizeX,int _sizeY) {
        tileList = new ArrayList<ArrayList<Tile>>();
        sizeX=_sizeX;
        sizeY=_sizeY;
        for (int i=0;i<sizeX;i++){
            tileList.add(new ArrayList<Tile>());
        }
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

    public void connectTiles(){
        for (int x=0; x<sizeX; x++) {
            for(Tile tile: tileList.get(x)){
                //north tile
                if (getTileAt(tile.gettX(), tile.gettY()-1) != null)
                    tile.setNorthTile(getTileAt(tile.gettX(), tile.gettY() - 1));
                //east tile
                if (getTileAt(tile.gettX() + 1, tile.gettY()) != null)
                    tile.setWestTile(getTileAt(tile.gettX() + 1, tile.gettY()));
                //south tile
                if (getTileAt(tile.gettX() , tile.gettY()+1) != null)
                    tile.setSouthTile(getTileAt(tile.gettX() , tile.gettY()+1));
                //west tile
                if (getTileAt(tile.gettX() - 1, tile.gettY()) != null)
                    tile.setWestTile(getTileAt(tile.gettX() - 1, tile.gettY()));

            }
        }

    }

    public void addTile(Tile tile){
        tileList.get(tile.gettX()).add(tile);


    }
}
