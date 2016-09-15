import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import algo.AnagramAlgo;
import algo.SamCAlgo;

public class MainClass {

	public static void main(String[] args) {
		
		//ArrayList<String> wordsList = getListFile('');
		//ArrayList<String> dictList = getListFile("src/input/dict.txt");	
		//System.out.println("ff"+dictList.get(0));
		
		AnagramAlgo samCAlgo = new SamCAlgo();
		
		samCAlgo.start();
	}
	
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
