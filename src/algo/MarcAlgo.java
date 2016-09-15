package algo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Logique : recuper les mots et les trier en ordre alphabetique les 
 * lettres et ensuite simplement comparer les 2 string.
 * 
 * @author Marc-Antoine
 *
 */

public class MarcAlgo extends AnagramAlgo {

	@Override
	protected void run() {
		// TODO Auto-generated method stub
		// Recupere et lire les fichier
		ArrayList<String> wordsList = getListFile("src/input/words.txt");
		ArrayList<String> dictList = getListFile("src/input/dict.txt");

		// Applique les algos
		parcourtList(wordsList, dictList);
	}

	/**
	 * Parcourt les listes afin d'appliquer les algo
	 * 
	 * @param wordsList
	 *            : liste de mot (words.txt)
	 * @param dictList
	 *            : liste du dicitionnaire (dict.txt)
	 */
	public void parcourtList(ArrayList<String> wordsList, ArrayList<String> dictList) {
		// Variable pour le nombre total d'anagrammes
		int totalCp = 0;
		// Parcourt tout les mots
		for (int i = 0; i < wordsList.size(); i++) {
			int cp = 0;
			String theWord = wordsList.get(i).replace(" ", "");

			// Parcout tout les mots du dictionnaire
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
		// Affiche le message, a la fin, qui affiche le nombre total d anagramme
		// trouve
		System.out.println("Il y a un total de " + totalCp + " anagrammes");
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
		char[] word1 = firstWord.replaceAll("[\\s]", "").toCharArray();
		char[] word2 = secondWord.replaceAll("[\\s]", "").toCharArray();
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
