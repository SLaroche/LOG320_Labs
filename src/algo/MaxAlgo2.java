package algo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MaxAlgo2 extends AnagramAlgo{
	boolean showPrint;
	private static int[] TabNbrPremier = new int[] {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};
	private int totalAnagrame = 0;
	
	public MaxAlgo2(boolean showPrint) {
		this.showPrint = showPrint;
	}
	
	@Override
	protected void run() {
		// TODO Auto-generated method stub
		// Recupere et lire les fichier
		try {
			ArrayList<String> wordsList = getListFile(this.wordsPath);
			ArrayList<String> dictList = getListFile(this.dictPath);
			startTimer();
			algo(dictList, wordsList, 0, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Applique les algos
		
	}

	private void algo(ArrayList<String> dictList, ArrayList<String> wordsList, int index1, int index2) {
		// TODO Auto-generated method stub
		while(index1 < wordsList.size() && index2 < dictList.size())
		{
			String word = wordsList.get(index1);
			String dictWord = dictList.get(index2);
			int comparaison = word.compareTo(dictWord);
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
		}
		
		/*if(index1 < wordsList.size() && index2 < dictList.size())	
			algo(dictList, wordsList, index1, index2);
		else*/
			System.out.println("Il y a "+ totalAnagrame+ " annagrammes");
	}
}
