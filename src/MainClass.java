import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import algo.AnagramAlgo;
import algo.MarcAlgo;
import algo.PdfAlgo;
import algo.SamCAlgo;

public class MainClass {

	/**
	 * Main.
	 * @param args
	 */
	public static void main(String[] args) {
		
		//ArrayList<String> wordsList = getListFile(args[0]);
		//ArrayList<String> dictList = getListFile(args[1]);
		
		
		AnagramAlgo algoPDF = new PdfAlgo();
		AnagramAlgo algoSamC = new SamCAlgo();
		AnagramAlgo algoMarc = new MarcAlgo();
		
		
		algoPDF.start();
		System.out.println("^^ = PDF Algo");
		algoSamC.start();
		System.out.println("^^ = SamC Algo");
		algoMarc.start();
		System.out.println("^^ = Marc Algo");
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
