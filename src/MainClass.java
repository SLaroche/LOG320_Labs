

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
		String dictPath = "src/input/dict.txt";
		String WordsPath = "src/input/words.txt";
		
		//look for how many core available
		int nbCore = Runtime.getRuntime().availableProcessors();
		System.out.println(nbCore + " cores available");
		
		//Competition Algo
		AnagramAlgo algoCore1 = new AlgoCore1(false, nbCore);
		
		algoCore1.start(dictPath,WordsPath);
		System.out.println("^^ = SamC Core 1");
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
