package algo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MaxAlgo extends AnagramAlgo{
	boolean showPrint;
	private static int[] TabNbrPremier = new int[] {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};
	private int totalAnagrame = 0;
	
	public MaxAlgo(boolean showPrint) {
		this.showPrint = showPrint;
	}
	
	@Override
	protected void run() {
		// TODO Auto-generated method stub
		// Recupere et lire les fichier
		try {
			ArrayList<String> wordsList = getListFile(this.wordsPath);
			ArrayList<String> dictList = getListFile(this.dictPath);
			algo(dictList, wordsList, 0, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Applique les algos
		
	}

	private void algo(ArrayList<String> dictList, ArrayList<String> wordsList, int index1, int index2) {
		// TODO Auto-generated method stub
		String word = wordsList.get(index1);
		String dictWord = dictList.get(index2);
		int comparaison = wordsList.get(index1).compareTo(dictWord);
		//If the word is smaller than the dict word up the word 
		if(comparaison<0)
			index1++;
		//If the word is bigger than the dict word up the dict		
		else if(comparaison>0)
			index2++;
		else {
			totalAnagrame++;
			index2++;
		}
		
		if(index1 < wordsList.size() && index2 < dictList.size())	
			algo(dictList, wordsList, index1, index2);
		else
			System.out.println("Il y a "+ totalAnagrame+ " annagrammes");
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
	 * @throws IOException 
	 */
	
	@SuppressWarnings("resource")
	private static ArrayList<String>  getListFile(String filePath) throws IOException {
		BufferedReader textInFile;
		ArrayList<String>  list = new ArrayList<String>();
		String a;
		textInFile = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));
		while ((a=textInFile.readLine()) != null) {
			char[] chars = a.toCharArray();
			Arrays.sort(chars);
			list.add(new String(chars).replaceAll(" ", ""));
		}
		list.sort(null);
		return list;
	}
}
