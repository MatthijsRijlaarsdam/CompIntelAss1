package src;
import java.util.ArrayList;

public class DriverGenAlg {

	// Beginwaarden stellen en output managen (de waarden zoals ze hier staan geven een zeer goede benadering van een max)
	private static int aantal = 200;			// Grootte van de populatie
	private static int nrCross = 50000;			// aantal iteraties
	private static int nrKeep = 4;				// Genen die blijven bestaan bij de cross-over, zijn de beste 
	private static int nrParent = 20;			// Aantal Parents dat wordt aangepast bij de cross-over
	private static float kansNietRandom = 60;	// De kans dat een gen niet random wordt gezet bij de cross-over
	private static int nrGenKeep = 15;			// maximaal aantal chromosomen in een gen dat gelijk blijft bij de cross-over
	private static int nrGenKeepMin = 6;		// minimaal aantal chromosomen in een gen dat gelijk blijft bij de cross-over
	
	// Waarden om output te managen
	private static boolean print1stPop = false;
	private static boolean printBestPop = true;
	private static boolean printLatestPop = false;
	private static boolean printAverage = true;
	private static GenPop test;
	private static ArrayList<Integer> Lengtes;
	public static int[][] DistanceMatrix;
	
	public static void main(String[] args){
		try {
			// Matrix voor de afstanden inladen (zie CoordinatesProducts4.txt voor opmaak)
			DistanceMatrix = MazeLoader.LoadFrom("CoordinatesProducts4.txt");
			test = new GenPop(aantal);
			
			PrintPop(print1stPop);
			
			// Printen gemiddelde lengte van de genen van de eerste populatie
			if (printAverage){
				Lengtes = test.GetTotLength();
				float average = 0;
				for (int i = 0; i < Lengtes.size(); i++){
					average = average + Lengtes.get(i);
				}
				average = average / Lengtes.size();
				System.out.println(average);
			}
			
			/*
			 * Cross-over volledig random
			for (int i = 0; i < nrCross; i++){
			test.CrossoverPopRandom(nrKeep);
			}
			*/
			
			// Cross-over met schemata en mutatie
			for (int i = 0; i < nrCross; i++){
				test.CrossoverPopSchema(nrKeep, nrParent, nrGenKeep, nrGenKeepMin, kansNietRandom);
			}
			
			
			PrintPop(printLatestPop);

			if (printBestPop){
				Lengtes = test.GetTotLength();
				for (int i = 0; i<nrKeep;i++){
					int a = test.SelectLowest(nrKeep).get(i);
					System.out.println(test.getPopulation().get(a) + " " +  Lengtes.get(a));
				}
			}
			
			// Printen gemiddelde lengte van de genen van de laatste populatie
			if (printAverage){
				ArrayList<Integer> Lengtes2 = test.GetTotLength();
				float average2 = 0;
				for (int i = 0; i < Lengtes2.size(); i++){
					average2 = average2 + Lengtes2.get(i);
				}
				average2 = average2 / Lengtes.size();
				System.out.println(average2);
				System.out.println();
			}
			
			System.out.println("Klaar");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	/** Methode om de populatie en beste x te printen
	 * 
	 * @param bool - Aangeven wat je wil printen
	 */
	public static void PrintPop(boolean bool){
		if (bool){
			Lengtes = test.GetTotLength();
			for (int i = 0; i < aantal; i++){
				System.out.println(test.getPopulation().get(i) + " " + Lengtes.get(i));
			}
			System.out.println();
			System.out.println(test.SelectLowest(nrKeep).toString());
			System.out.println();
		}
	}

}

