

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
		String dictPath = "src/input/dict.txt";
		String wordsPath =  "src/input/words.txt";
		
		
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
		
		//algoCore1.start(dictPath,WordsPath);
		//System.out.println("^^ = SamC Core 1");
		
		//algoCore2.start(dictPath,WordsPath);
		//System.out.println("^^ = SamC Core 2");
		
		algoCore3.start(dictPath,wordsPath);
		System.out.println("^^ = SamC Core 3");
		
		algoCore4.start(dictPath,wordsPath);
		System.out.println("^^ = SamC Core 4");
		
		//pdfalgo.start(dictPath,WordsPath);
		//System.out.println("^^ = pdfalgo");
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
