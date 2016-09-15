package algo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PdfAlgo extends AnagramAlgo{

	@Override
	public void run() {
		//Recupere et lire les fichier
		ArrayList<String> wordsList = getListFile("src/input/words.txt");
		ArrayList<String> dictList = getListFile("src/input/dict.txt");	
		
		//Applique les algos
		parcourtList(wordsList, dictList);
	}
	
	/**
	 * Recuperer le ficher par son nom et le lire.
	 * @param filePath : le nom du ficher a lire
	 * @return la liste des mots du fichier
	 */
	private static ArrayList<String> getListFile(String filePath)
	{
		Scanner textInFile;
		ArrayList<String> list= new ArrayList<String>();
		
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
	
	/**
	 * Parcourt les listes afin d'appliquer les algo
	 * @param wordsList : liste de mot (words.txt)
	 * @param dictList : liste du dicitionnaire (dict.txt)
	 */
	public void parcourtList(ArrayList<String> wordsList, ArrayList<String> dictList){
		//Variable pour le nombre total d'anagrammes
		int totalCp = 0;
		//Parcourt tout les mots
		for(int i = 0; i < wordsList.size(); i++){
			int cp = 0;
			String theWord = wordsList.get(i);
			
			//Parcout tout les mots du dictionnaire
			for(int j = 0; j< dictList.size(); j++){
				String theWordDict = dictList.get(j);
				//Appelle la methode qui compare les deux mots
				boolean estAnagramme = algo(theWord, theWordDict);
				//Si les deux mots sont des anagrammes ont ajoute 1 au compteur
				if(estAnagramme)
					cp++;
			}
			totalCp += cp;
			//Affiche le message pour tout les mots de la liste words
			System.out.println("Il y a "+cp+" anagrammes pour le mot "+theWord);
		}
		//Affiche le message, a la fin, qui affiche le nombre total d anagramme trouve
		System.out.println("Il y a un total de "+totalCp+" anagrammes");
	}
	
	/**
	 * Algorithme qui compare les deux mots en parametre
	 * afin de determiner s ils sont des anagrammes
	 * @param c1 : mot 1 (words)
	 * @param c2 : mot 2 (dictionnaire)
	 * @return
	 */
	public boolean algo(String c1, String c2) {
		//Retire les espaces des mots
		c1 = c1.replace(" ", "");
		c2 = c2.replace(" ", "");
		
		//On parcourt tous les lettres du 1er mot
		for (int i = 0; i < c1.length(); i++) {
			boolean trouve = false;
			//On parcourt tous les lettre lettres du 2e mot
			for (int j = 0; j < c2.length() && trouve == false; j++) {
				char c = c2.charAt(j);
				//On compare les lettres des deux mots
				if (c1.charAt(i) == c) {
					//Si cest la meme lettre on retire la 
					//lettre dansle mot 2
					c2 = c2.replaceFirst(c + "", "");
					trouve = true;
				}
			}
			//Si on a pas trouve une meme lettre dans 
			//les deux mots, on retourne faux
			if (!trouve) {
				return false;
			}
		}

		//Si le 2e mot nest pas vide, on retourne faux,
		//car le mot 2 est plus long que le mot 1
		if (!c2.equals("")) {
			return false;
		}
		return true;
	}
}
