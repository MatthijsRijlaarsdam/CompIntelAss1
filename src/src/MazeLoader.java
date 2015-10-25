package src;

import java.io.File;
import java.util.Scanner;

public class MazeLoader {
	
	private static int rows;
	private static int colums;
	
	public static int[][] LoadFrom(String file) throws Exception{ 
	File bestand = new File(file);
	Scanner sc = new Scanner(bestand);
	
	rows = sc.nextInt();
	colums = sc.nextInt();
	
	int[][] matrix1 = new int[colums][rows];
	
	for (int i = 0; i < colums; i++){
		for (int j = 0; j < rows; j++){
			matrix1[i][j] = sc.nextInt(); 
		}
	}
	sc.close();
	return matrix1;
	}
	
	public static int GetRows(){
		return rows;
	}
	
	public static int GetColums(){
		return colums;
	}

}
