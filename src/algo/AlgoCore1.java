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

public class AlgoCore1 extends AnagramAlgo{
	private boolean showPrint;
	private int nbCore;
	private int[] nbPremier = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29,
			31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101,
			103, 107, 109, 113, 127, 131, 137, 139, 149, 151};
	private HashMap<Long, Integer> hmap = new HashMap<>();
	private ArrayList<String> stringBuffer = new ArrayList<String>();

	
	public AlgoCore1(boolean showPrint, int nbCore) {
		this.showPrint = showPrint;
		this.nbCore = nbCore;
	}
	
	@Override
	protected ArrayList<String> run() {
		preprocessDict();
		int total = loopWordFile();
		stringBuffer.add("Il y a un total de " + total + " annagrammes");
		return stringBuffer;
	}
	
	private void preprocessDict(){
		for (String str: dictArray) {
			//creation du key
			Long key = stringToKey(str);
			//remplissage du hashmap
			if(hmap.get(key) == null){
				hmap.put(key, 1);
			}else{
				hmap.put(key, hmap.get(key)+1);
			}
		}
	}
	
	private long stringToKey(String str){
		long key = 1;
		
		//creation de la key
		for (char c: str.toCharArray()) {
			if(c>47){
				if(c<58){
					key *= nbPremier[c - 48];
				}else if(c<97){
					key *= nbPremier[c - 65];
				}else{
					key *= nbPremier[c - 97];
				}
			}
		}
		
		return key;
	}
	
	private int loopWordFile(){
		int counter = 0;
		
		for (String str: wordsArray) {
			Long key = stringToKey(str);
			//show result
			int value;
			try{
				value = hmap.get(key);
			}catch(Exception e){
				value = 0;
			}
			counter+=value;

			stringBuffer.add("Il y a " + value +" anagrammes du mot " + str);
		}
		return counter;
	}
}
