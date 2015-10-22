package ass3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Matthijs on 10/22/15.
 */
public class MapParser {
    private String mapFile;
    private Map map;
    private int sizeX;
    private int sizeY;

    public MapParser(String mapFile) {
        this.mapFile = mapFile;
    }

    public void parseMap(){
        try {Scanner scanner = new Scanner(new File(mapFile));
            sizeX=Integer.parseInt(scanner.next());
            sizeY=Integer.parseInt(scanner.next());
            map= new Map(sizeX,sizeY);

            //add tiles
            for (int y=0; y<sizeY; y++){
                for (int x=0; x<sizeX; x++){
                    Tile tile=new Tile(x,y,Boolean.parseBoolean(scanner.next()));
                    map.addTile(tile);

                }
            }

            //connect tiles
            map.connectTiles();



        }
        catch(FileNotFoundException exception){System.out.println("file does not exist");}


    }

    public Map getMap(){
        return map;
    }
}
