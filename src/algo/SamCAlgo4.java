/*
 * Reference: http://howtodoinjava.com/java-7/nio/3-ways-to-read-files-using-java-nio/
 * http://nadeausoftware.com/articles/2008/02/java_tip_how_read_files_quickly
 */

package algo;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Optimisation Possible
 * 
 * -Implementer un meilleur lecteur de file -> FileChannel with a MappedByteBuffer and array reads (the magenta line with round dots that hits a low at 2Kbytes to 4Kbytes).
 * -run avec java -server -XX:CompileThreshold=2 -XX:+AggressiveOpts -XX:+UseFastAccessorMethods -Xmx1000m Test
 * -Linux
 * -build
 * -ouvrir seulement cette application
 */

public class SamCAlgo4 extends AnagramAlgo{
	private boolean showPrint;
	private int nbCore;
	private int[] nbPremier = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31,
	        37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103,
	        107, 109, 113 };
	private HashMap<Long, Integer> hmap = new HashMap<>();

	
	public SamCAlgo4(boolean showPrint, int nbCore) {
		this.showPrint = showPrint;
		this.nbCore = nbCore;
	}
	
	@Override
	protected void run() {
		hmap.clear();
		
		preprocessDict(this.dictPath);
		int totalAnagram = loopWordFile(this.wordsPath);
		System.out.println("Il y a un total de " + totalAnagram + " annagrammes");
	}
	
	private void preprocessDict(String filePath){
		try {
			RandomAccessFile aFile = new RandomAccessFile(filePath, "r");
			FileChannel inChannel = aFile.getChannel();
	        MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
	        buffer.load();  
	        String word = "";
	        for (int i = 0; i < buffer.limit(); i++)
	        {
	        	char c = (char) buffer.get();
	        	if(c == 10 || i == buffer.limit()){
	        		//creation du key
					Long key = stringToKey(word);
					//remplissage du hashmap
					if(hmap.get(key) == null){
						hmap.put(key, 1);
					}else{
						hmap.put(key, hmap.get(key)+1);
					}
					
	        		word = "";
	        	}else{
	        		if(c>64){
	        			word += c;
	        		}
	        	}
	        }
		    //copy
	        	//creation du key
				Long key = stringToKey(word);
				//remplissage du hashmap
				if(hmap.get(key) == null){
					hmap.put(key, 1);
				}else{
					hmap.put(key, hmap.get(key)+1);
				}
			//end copy
	        buffer.clear(); // do something with the data and clear/compact it.
	        inChannel.close();
	        aFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private long stringToKey(String str){
		long key = 1;
		
		//creation de la key
		for (char c: str.toCharArray()) {
				if(c<97){
					key *= nbPremier[c - 65];
				}else{
					key *= nbPremier[c - 97];
				}
		}
		
		return key;
	}
	
	private int loopWordFile(String filePath){
		int counter = 0;
		
		try {
			RandomAccessFile aFile = new RandomAccessFile(filePath, "r");
			FileChannel inChannel = aFile.getChannel();
	        MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
	        buffer.load();  
	        String word = "";
	        for (int i = 0; i < buffer.limit(); i++)
	        {
	        	char c = (char) buffer.get();
	        	if(c == 10){
	        		Long key = stringToKey(word);
					//show result
					int value;
					try{
						value = hmap.get(key);
					}catch(Exception e){
						value = 0;
					}
					counter+=value;
					if(showPrint) System.out.println("Il y a " + value +" anagrammes du mot " + word);
					
	        		word = "";
	        	}else{
	        		if(c>64){
	        			word += c;
	        		}
	        	}
	        }
		    //copy
	        	Long key = stringToKey(word);
				//show result
				int value;
				try{
					value = hmap.get(key);
				}catch(Exception e){
					value = 0;
				}
				counter+=value;
				if(showPrint) System.out.println("Il y a " + value +" anagrammes du mot " + word);
			//end copy
	        buffer.clear(); // do something with the data and clear/compact it.
	        inChannel.close();
	        aFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return counter;
	}
}
