

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import algo.*;

public class MainClass {

	/**
	 * Main.
	 * @param args
	 */
	public static void main(String[] args) {
		//ArrayList<String> wordsList = getListFile(args[0]);
		//ArrayList<String> dictList = getListFile(args[1]);
		//String dictPath = args[1];
		//String dictPath = "src/input/dict.txt";
		//String wordsPath = "src/input/words.txt";
		String dictPath = "src/input/words_allEnglish.txt";
		String wordsPath =  "src/input/words_allEnglish.txt";
		
		
		//look for how many core available
		int nbCore = Runtime.getRuntime().availableProcessors();
		System.out.println(nbCore + " cores available");
		
		
		warmUpTime(1);
		
		//Competition Algo
		AnagramAlgo algoCore1 = new AlgoCore1();
		AnagramAlgo algoCore2 = new AlgoCore2(2);
		AnagramAlgo algoCore3 = new AlgoCore3(nbCore);
		AnagramAlgo algoCore4 = new AlgoCore4(nbCore);
		AnagramAlgo pdfalgo = new PdfAlgo(true);
		
		int nbMots = 1000;
		int nbCaracter = 100;
		
		//String[] dictArray = generateRandomWords(nbMots, nbCaracter);
		//String[] wordsArray = generateRandomWords(nbMots, nbCaracter);;
		
		//pdfalgo.start(wordsArray,wordsArray);
		//System.out.println("^^ = pdfalgo");
		
		//algoCore4.start(wordsArray,wordsArray);
		algoCore4.start(dictPath,wordsPath);
		System.out.println("^^ = SamC Core 4");
		
		
	}
	
	public static String[] generateRandomWords(int numberOfWords,int nbCaracter) //http://stackoverflow.com/questions/4951997/generating-random-words-in-java
	{
	    String[] randomStrings = new String[numberOfWords];
	    Random random = new Random();
	    for(int i = 0; i < numberOfWords; i++)
	    {
	        char[] word = new char[nbCaracter]; // words of length 3 through 10. (1 and 2 letter words are boring.)
	        for(int j = 0; j < word.length; j++)
	        {
	            word[j] = (char)('a' + random.nextInt(26));
	        }
	        randomStrings[i] = new String(word);
	    }
	    return randomStrings;
	}
	
	/**
	 * Recuperer le ficher par son nom et le lire.
	 * @param filePath : le nom du ficher a lire
	 * @return la liste des mots du fichier
	 */
	@SuppressWarnings("resource")
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

	private static void warmUpTime(int sec){
		int warmUpTimeSec = sec;
		long startTime = System.currentTimeMillis();
		long endTime= 0;
		long duration = 0;
		long lastSec=0;
		while(duration<(warmUpTimeSec*1000)){
			endTime = System.currentTimeMillis();
			duration = (endTime - startTime);
			if((duration % 1000) == 0){
				if(lastSec != duration/1000){
					lastSec = duration/1000;
					System.out.println("Warm Up Sec " + duration/1000 + "/" + warmUpTimeSec);
				}
			}
		}
	}
}
