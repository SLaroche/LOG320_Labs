package algo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Logique : 
 * 
 * 		- Lire et boucler une seule fois dans la liste des words
 * 		- Verifier la longueur des mots
 * 		- Trier les lettres en ordre alphabetique
 * 		- Simplement comparer les deux String
 * 
 * Etape :
 * 1 - recupere les mots du fichier dict.txt afin de les ajouter dans une liste
 * 2 - Lire le document word.txt mot par mot
 * 3 - Prend le mot et boucle dans la liste dict
 * 4 - Applique l'algo (trier en ordre alphabetique les 
 * lettres et ensuite simplement comparer les 2 string.)
 * 5 - affiche le nombre d anagramme pour le mot lu
 * 6 - Refaire les etapes precedent, jusqu au moment ou tout les mots sont lus
 * 7 - Afficher le nombre total d anagramme trouve
 * 
 * @author Marc-Antoine
 *
 */

public class MarcAlgo extends AnagramAlgo {

	@Override
	protected void run() {
		// TODO Auto-generated method stub
		// Recupere et lire les fichier
		//ArrayList<String> wordsList = getListFile("src/input/words.txt");
		ArrayList<String> dictList = getListFile("src/input/dict.txt");

		// Applique les algos
		algo(dictList, "src/input/words.txt");
	}

	/**
	 * Lire le ficher word.txt et parcourt la liste de dictionnaire 
	 * afin d'appliquer les algo
	 * 
	 * @param dictList
	 *            : liste du dicitionnaire (dict.txt)
	 * @param wordPath : le chemin du fichier word
	 */
	public void algo(ArrayList<String> dictList, String wordPath){
		Scanner textInFile;
		int totalCp = 0;
		//ArrayList<String> list = new ArrayList<String>();

		try {
			textInFile = new Scanner(new File(wordPath)).useDelimiter(",\\s*");
			while (textInFile.hasNextLine()) {
				String theWord = textInFile.nextLine().replace(" ", "");
				int cp = 0;
				for (int j = 0; j < dictList.size(); j++) {
					String theWordDict = dictList.get(j).replace(" ", "");
					
					//Verifie la longueur des mots
					if (theWord.length() == theWordDict.length()) {
						// Appelle la methode qui compare les deux mots
						boolean estAnagramme = isAnagram(theWord, theWordDict);
						// Si les deux mots sont des anagrammes ont ajoute 1 au
						// compteur
						if (estAnagramme)
							cp++;
					}
				}
				totalCp += cp;
				// Affiche le message pour tout les mots de la liste words
				System.out.println("Il y a " + cp + " anagrammes pour le mot " + theWord);
			}
			System.out.println("Il y a un total de " + totalCp + " anagrammes");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Reference :
	 * http://stackoverflow.com/questions/15045640/how-to-check-if-two-words-are
	 * -anagrams Compare les deux mots
	 * 
	 * @param firstWord
	 * @param secondWord
	 * @return
	 */
	public boolean isAnagram(String firstWord, String secondWord) {
		char[] word1 = firstWord.replaceAll("[\\s]", "").toLowerCase().toCharArray();
		char[] word2 = secondWord.replaceAll("[\\s]", "").toLowerCase().toCharArray();
		Arrays.sort(word1);
		Arrays.sort(word2);
		return Arrays.equals(word1, word2);
	}

	/**
	 * Recuperer le ficher par son nom et le lire.
	 * 
	 * @param filePath
	 *            : le nom du ficher a lire
	 * @return la liste des mots du fichier
	 */
	private static ArrayList<String> getListFile(String filePath) {
		Scanner textInFile;
		ArrayList<String> list = new ArrayList<String>();

		try {
			textInFile = new Scanner(new File(filePath)).useDelimiter(",\\s*");
			while (textInFile.hasNextLine()) {
				list.add(textInFile.nextLine());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
