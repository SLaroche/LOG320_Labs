

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import algo.*;

public class MainClass {

	/**
	 * Main.
	 * @param args
	 */
	public static void main(String[] args) {
		//ArrayList<String> wordsList = getListFile(args[0]);
		//ArrayList<String> dictList = getListFile(args[1]);
		String dictPath = "src/input/words_allEnglish.txt";
		String WordsPath = "src/input/words_allEnglish.txt";
		
		//look for how many core available
		int nbCore = Runtime.getRuntime().availableProcessors();
		System.out.println(nbCore + " cores available");
		
		//Competition Algo
		AnagramAlgo algoSamL = new SamLAlgo(false);
		AnagramAlgo algoSamC1 = new SamCAlgo(false);
		AnagramAlgo algoSamC2 = new SamCAlgo2(false);
		AnagramAlgo algoSamC3 = new SamCAlgo3(false,nbCore);
		AnagramAlgo algoSamC4 = new SamCAlgo4(false,nbCore);
		
		algoSamC1.start(dictPath,WordsPath);
		System.out.println("^^ = SamC Algo 1");
		algoSamC2.start(dictPath,WordsPath);
		System.out.println("^^ = SamC Algo 2");
		algoSamC3.start(dictPath,WordsPath);
		System.out.println("^^ = SamC Algo 3");
		algoSamC4.start(dictPath,WordsPath);
		System.out.println("^^ = SamC Algo 4");
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

}
