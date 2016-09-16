

package algo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/*
 * Optimisation Possible
 * 
 * -Utiliser plusieur CPU coeur
 * -Linux
 * -ouvrir seulement cette application
 * -prehasher des mots (dictionaire ou bruteforce)
 * -trouver meilleur moyen pour generer des key
 * 
 * Probleme
 * -Algorithm seulement bon si words.txt file est tres grand car cela prend du temps a mapper le dict
 */

public class SamCAlgo3 extends AnagramAlgo{
	private boolean showPrint;
	private int nbCore;
	private int[] nbPremier = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31,
	        37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103,
	        107, 109, 113 };
	private HashMap<Long, Integer> hmap = new HashMap<>();
	
	
	public SamCAlgo3(boolean showPrint, int nbCore) {
		this.showPrint = showPrint;
		this.nbCore = nbCore;
	}
	
	@Override
	protected void run() {
		hmap.clear();
		
		preprocessDict(this.dictPath);
		int totalAnagram = loopWordFile(this.wordsPath);
		
		System.out.println("Il y a un total de " + totalAnagram + " annagrammes");
	}
	
	private void preprocessDict(String filePath){
		Scanner textInFile;
		
		try {
			//create scanner
			textInFile = new Scanner(new File(filePath));
			
			//loop file
			while (textInFile.hasNextLine()) {
				//get string
				String a = textInFile.nextLine();
				//creation du key
				Long key = stringToKey(a);
				//remplissage du hashmap
				if(hmap.get(key) == null){
					hmap.put(key, 1);
				}else{
					hmap.put(key, hmap.get(key)+1);
				}
			}
			
			textInFile.close();
		} catch (FileNotFoundException e) {
		}
	}
	
	private long stringToKey(String str){
		long key = 1;
		
		//creation de la key
		for (char c: str.toCharArray()) {
			if(c>64){
				if(c<97){
					key *= nbPremier[c - 65];
				}else{
					key *= nbPremier[c - 97];
				}
			}
		}
		
		return key;
	}
	
	@SuppressWarnings("resource")
	private int loopWordFile(String filePath){
		Scanner textInFile;
		int counter = 0;
		
		try {
			//create scanner
			textInFile = new Scanner(new File(filePath)).useDelimiter(",\\s*");
			
			//loop file
			while (textInFile.hasNextLine()) {
				//get string
				String str = textInFile.nextLine();
				//creation du key
				Long key = stringToKey(str);
				//show result
				int value;
				try{
					value = hmap.get(key);
				}catch(Exception e){
					value = 0;
				}
				counter+=value;
				if(showPrint) System.out.println("Il y a " + value +" anagrammes du mot " + str);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return counter;
	}
}
