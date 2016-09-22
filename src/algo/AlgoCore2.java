/*
 * Reference: http://howtodoinjava.com/java-7/nio/3-ways-to-read-files-using-java-nio/
 * http://nadeausoftware.com/articles/2008/02/java_tip_how_read_files_quickly
 * http://www.javaworld.com/article/2077768/build-ci-sdlc/multicore-processing-for-client-side-java-applications.html?page=2
 */

package algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Optimisation Possible
 * 
 * -Implementer un meilleur lecteur de file -> FileChannel with a MappedByteBuffer and array reads (the magenta line with round dots that hits a low at 2Kbytes to 4Kbytes).
 * -run avec java -server -XX:CompileThreshold=2 -XX:+AggressiveOpts -XX:+UseFastAccessorMethods -Xmx1000m Test
 * -Linux
 * -build
 * -ouvrir seulement cette application
 * -core
 * -case(si dict > hash) et deux petit
 */

public class AlgoCore2 extends AnagramAlgo{
	private int nbCore;
	private int[] nbPremier = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29,
			31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101,
			103, 107, 109, 113, 127, 131, 137, 139, 149, 151};
	private ConcurrentHashMap<Long, Integer> hmap = new ConcurrentHashMap<>();//permet d'utiliser le hashmap via plusieurs threads 
	private ArrayList<String> stringBuffer = new ArrayList<String>();
	//thread
	CountDownLatch doneSignal;
	ExecutorService e;

	
	public AlgoCore2(int nbCore){
		this.nbCore = nbCore;
		doneSignal = new CountDownLatch(nbCore);
		e = Executors.newFixedThreadPool(nbCore);		
	}

	@Override
	protected ArrayList<String> run() {
		int dictArraySampleSize = dictArray.length/nbCore;
		
		//for(int i=0;i<nbCore;i++){
		//	e.execute(new preprocessDictRunnable(i*dictArraySampleSize, (i+1)*dictArraySampleSize));
		//}
		e.execute(new preprocessDictRunnable(0,dictArraySampleSize));
		e.execute(new preprocessDictRunnable(dictArraySampleSize,dictArray.length));
		try {
		   doneSignal.await(); // wait for all to finish
		} catch (InterruptedException ie) {
		}
		e.shutdown();
		
		int total = loopWordFile();
		stringBuffer.add("Il y a un total de " + total + " annagrammes");
		return stringBuffer;
	}
	
	private void preprocessDict(int start, int end){
		for (int i=start;i<end;i++) {
			//creation du key
			Long key = stringToKey(dictArray[i]);
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

			//stringBuffer.add("Il y a " + value +" anagrammes du mot " + str);
		}
		return counter;
	}
	
	class preprocessDictRunnable implements Runnable {
		   int start;
		   int end;

		   preprocessDictRunnable(int start, int end) {
		      this.start = start;
		      this.end = end;
		   }

		   public void run() {
			   preprocessDict(start, end);
			   doneSignal.countDown();
		   }
		}
}
