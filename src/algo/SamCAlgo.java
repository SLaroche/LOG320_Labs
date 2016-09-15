package algo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class SamCAlgo extends AnagramAlgo{
	
	//HashMap<Integer, HashMap<String, int[]>> hmapBySize = new HashMap<>();
	HashMap<String, Integer> hmap = new HashMap<>();
	
	@Override
	protected void run() {
		preprocessDict("src/input/dict.txt");
		int totalAnagram = loopWordFile("src/input/words.txt");
		
		System.out.println("Il y a un total de " + totalAnagram + " annagrammes");
	}
	
	@SuppressWarnings("resource")
	private void preprocessDict(String filePath){
		Scanner textInFile;
		
		try {
			//create scanner
			textInFile = new Scanner(new File(filePath)).useDelimiter(",\\s*");
			
			//loop file
			while (textInFile.hasNextLine()) {
				//get string
				String a = textInFile.nextLine();
				//creation du key
				String key = stringToKey(a);
				
				//remplissage du hashmap
				if(hmap.get(key) == null){
					hmap.put(key, 1);
				}else{
					hmap.put(key, hmap.get(key)+1);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private String stringToKey(String str){
		int[] arr = new int[26];
		
		//traitement de la string
		str = str.replace(" ", "");
		str = str.toLowerCase();
		
		//remplissage du array
		for (char c: str.toCharArray()) {
			arr[c - 97]++;
		}
		
		return intArrayToString(arr);
	}
	
	private String intArrayToString(int[] arr){
		String str = "";
				
		for (int i: arr){
			str += i;
		}
		return str;
	}

	@SuppressWarnings("resource")
	private int loopWordFile(String filePath){
		Scanner textInFile;
		int counter = 0;
		
		try {
			//create scanner
			textInFile = new Scanner(new File(filePath)).useDelimiter(",\\s*");
			
			//loop file
			while (textInFile.hasNextLine()) {
				//get string
				String str = textInFile.nextLine();
				//creation du key
				String key = stringToKey(str);
				//show result
				int value = hmap.get(key);
				counter+=value;
				System.out.println("Il y a " + value +" anagrammes du mot " + str);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return counter;
	}
}
