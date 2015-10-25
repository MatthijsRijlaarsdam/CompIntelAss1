package src;
import java.util.ArrayList;

public class GenPop {
	
	private int LengthGene = MazeLoader.GetColums()-2;			// Lengte van een gen, 0 en 19 niet meegerekend
	private int TotLength;										// Lengte van een gen
	private int PopCount;										// grootte van de populatie
	private int[][] DistanceMat = DriverGenAlg.DistanceMatrix;	// De matrix uit de driver halen
	private ArrayList<ArrayList<Integer>> Population;			// De matrix voor de populatie

	/** Constructor voor de Genetic Population. <br>
	 * 	De genen bestaan uit 10 getallen van 1 tot 10. <br>
	 * 	Op de x-de plek in het gen staat waar de route dan naar toe gaat. <br>
	 * 	Genen worden random gemaakt, maar er staan geen dubbele getallen in en
	 *  de getallen gaan altijd van 1 tot 10 als je het gen volgt.
	 * 
	 * @param nr - Grootte van de Populatie
	 */
	public GenPop(int nr){
		Population = new ArrayList<ArrayList<Integer>>(nr);
		PopCount = nr;
		for (int i = 0; i < PopCount; i++){
			ArrayList<Integer> a = buildGen();
			Population.add(a);
		}
	}
	
	/** Methode om de Populatie door te geven
	 * 
	 * @return
	 */
	public ArrayList<ArrayList<Integer>> getPopulation(){
		return Population;
	}
	
	/** Methode om een Arraylist van de lengen van de genen in de Pop te krijgen
	 * 
	 * @return -  ArrayList van de lengtes van de genen in de Pop
	 */
	public ArrayList<Integer> GetTotLength(){
		ArrayList<Integer> temp = new ArrayList<Integer>(PopCount);
		for (int i = 0; i < PopCount; i++){
			TotLength = 0;
			for (int j = 0; j < LengthGene+1; j++){
				int index = Population.get(i).get(j);
				int toIndex = Population.get(i).get(j+1);
				TotLength = TotLength + DistanceMat[index][toIndex]; 		// van boven naar beneden is de van, links naar rechts is de naar
			}
			temp.add(TotLength);
		}
		return temp;
	}

	/** Methode om de Populatie te vervangen. <br>
	 * 	De nr beste worden bewaard, de rest wordt door een random gen vervangen.
	 * 
	 * @param nr - nummer van het aantal beste dat bewaard moet blijven
	 */
	public void CrossoverPopRandom(int nr){
		ArrayList<Integer> beste = SelectLowest(nr);
		for (int i = 0; i < PopCount; i++){
			if (!beste.contains(i)){
				ArrayList<Integer> a = buildGen();
				Population.set(i, a);
			}
		}
	}
	
	/** Methode om de Populatie te verbeteren
	 * 
	 * @param nrkeep - Nummer van genen dat bewaard moet blijven (beste)
	 * @param nrParent - Nummer van genen die als Parent dienen (beste)
	 */
	public void CrossoverPopSchema(int nrKeep, int nrParent, int nrGenKeep, int nrGenKeepMin, float kansNietRandom){
		ArrayList<Integer> besteKeep = SelectLowest(nrKeep);							// Beste selecteren om niet te verwijderen/aan te passen (index ind de populatie)
		ArrayList<Integer> Parents = SelectLowest(nrParent);							// De Parents selecteren (alleen lengte values, dus index in de populatie)
		ArrayList<ArrayList<Integer>> tempParent = new ArrayList<ArrayList<Integer>>();
		
		// Parents selecteren
		for (int i = 0; i < PopCount; i++){
			if (Parents.contains(i)){
				tempParent.add(Population.get(i));
			}
		}
		
		for (int i = 0; i < PopCount; i++){
			if (!besteKeep.contains(i)){				// De beste bewaren
				double Ontdek = Math.random()*100;		// De kans op mutatie
				boolean Doorgaan = true;
				if (Ontdek > kansNietRandom){
					Doorgaan = false;
				}
				
				if (Doorgaan){
					ArrayList<Integer> nrGen = new ArrayList<Integer>();
					int parent = (int)Math.round(Math.random()*(nrParent-1));
					ArrayList<Integer> Parent = tempParent.get(parent);
					ArrayList<Integer> a = new ArrayList<Integer>();
					int GenKeep = nrGenKeepMin + (int)Math.round(Math.random()*(nrGenKeep-nrGenKeepMin));
					ArrayList<Integer> volgende = new ArrayList<Integer>(LengthGene-GenKeep);

					// De chromosomen selecteren die bewaard blijven
					while (nrGen.size() != GenKeep){
						int temp = 1+(int)Math.round(Math.random()*(LengthGene));
						if (!nrGen.contains(temp)){
							nrGen.add(temp);
						}
					}

					// De variabele waarden van een gen selecteren
					for (int j = 1; j < LengthGene+1; j++){
						if (!nrGen.contains(j)){
							volgende.add(Parent.get(j));
						}
					}

					// begin van de chromosoom
					a.add(0);
					for (int j = 1; j < LengthGene+1; j++){
						if (nrGen.contains(j)){
							a.add(Parent.get(j));
						} else if (!nrGen.contains(j)) {
							int temp2 = volgende.get((int)Math.round(Math.random()*(volgende.size()-1)));
							a.add(temp2);
							for (int k = 0; k < volgende.size(); k++){
								if (volgende.get(k)==temp2){
									volgende.remove(k);
								}
							}
						}
					}
					a.add(19);
					Population.set(i, a);
				} else if (!Doorgaan){						// Als een gen volledig muteerd, een random gen bouwen
					boolean nieuw = false;
					ArrayList<Integer> a = buildGen();
					while (nieuw){							// Bestaande waarden niet toevoegen aan de Populatie (niet in gebruik)
						if (!Population.contains(a)){
							nieuw = true;
							break;
						}
						a = buildGen();
					}
					Population.set(i, a);
				}
			}
		}
	}
	
	/** Methode om de laagste x waarden uit de lengtematrix te halen
	 * 
	 * @param nr - aantal laagste waarden dat terug komt
	 * @return - ArrayList van indexen van laagste lengtes
	 */
	public ArrayList<Integer> SelectLowest(int nr){
		ArrayList<Integer> temp = new ArrayList<Integer>(nr);
		ArrayList<Integer> temp2 = GetTotLength();
		int nrHighest = 0; 
		for (int j = 0; j < nr; j++){
			int temp3 = 10000;
			for (int i = 0; i < temp2.size(); i++){
				if (temp2.get(i)<temp3){ 
					temp3 = temp2.get(i);
					nrHighest = i;
				}
			}
			temp.add(nrHighest);
			temp2.set(nrHighest, 10000);
		}
		return temp;
	}

	/** Methode om een random Gen op te bouwen. <br>
	 * 	Geen dubbele getallen of loops.
	 * 
	 * @return - een gen van lengte zoals in de matrix beschreven staat
	 */
	public ArrayList<Integer> buildGen(){
		ArrayList<Integer> a = new ArrayList<Integer>(LengthGene);
		ArrayList<Integer> volgende = new ArrayList<Integer>(LengthGene);
		int temp = 0;
		for (int j = 0; j < LengthGene; j++){
			volgende.add(j+1);
		}
		a.add(0);
		for (int j = 0; j < LengthGene; j++){
				temp = volgende.get((int)Math.round(Math.random()*(volgende.size()-1)));
				a.add(temp);
				for (int k = 0; k<volgende.size(); k++){
					if ((volgende.get(k) == temp)&&(temp!=0)){
						volgende.remove(k);
					}
				}
			
		}
		a.add(LengthGene+1);
		return a;
	}
}