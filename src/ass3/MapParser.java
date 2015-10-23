package ass3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Matthijs on 10/22/15.
 */
public class MapParser {
    private String mapFile;
    private String coordinateFile;
    private Map map;
    private int sizeX, sizeY, startX, startY, endX, endY;

    public MapParser(String mapFile, String coordinateFile) {
        this.mapFile = mapFile;
        this.coordinateFile = coordinateFile;
    }

    public void parseMap(){
        try {
            Scanner scanner = new Scanner(new File(coordinateFile)).useDelimiter("\\s*;\\s*");
            String startCoords = scanner.next();
            String endCoords = scanner.next();
            String[] startxy = startCoords.split(", ");
            String[] endxy = endCoords.split(", ");
            startX = Integer.parseInt(startxy[0]);
            startY = Integer.parseInt(startxy[1]);
            endX = Integer.parseInt(endxy[0]);
            endY = Integer.parseInt(endxy[1]);


            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("file does not exist");
        }

        try {Scanner scanner = new Scanner(new File(mapFile));
            sizeX=Integer.parseInt(scanner.next());
            sizeY=Integer.parseInt(scanner.next());
            map= new Map(sizeX,sizeY);

            //add tiles
            for (int y=0; y<sizeY; y++){
                for (int x=0; x<sizeX; x++){
                    boolean accessible = scanner.nextInt() == 1;
                    Tile tile=new Tile(x,y,accessible);
                    map.addTile(tile);
                }
            }

            map.setStart(map.getTileAt(startX, startY));
            map.setEnd(map.getTileAt(endX, endY));

            //connect tiles
            map.connectTiles();

            scanner.close();
        }
        catch(FileNotFoundException exception){System.out.println("file does not exist");}




    }

    public void readCoords() {

    }

    public Map getMap(){
        return map;
    }
}
