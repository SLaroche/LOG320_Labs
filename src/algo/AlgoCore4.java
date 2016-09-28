/*
 * Reference: http://howtodoinjava.com/java-7/nio/3-ways-to-read-files-using-java-nio/
 * http://nadeausoftware.com/articles/2008/02/java_tip_how_read_files_quickly
 * http://www.javaworld.com/article/2077768/build-ci-sdlc/multicore-processing-for-client-side-java-applications.html?page=2
 */

package algo;

import java.util.ArrayList;
import java.util.Arrays;
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

public class AlgoCore4 extends AnagramAlgo{
	private int nbCore;
	private int[] nbPremier = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29,
			31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101,
			103, 107, 109, 113, 127, 131, 137, 139, 149, 151};
	private ConcurrentHashMap<Long, Integer> hmap;//permet d'utiliser le hashmap via plusieurs threads 
	private ArrayList<String> stringBuffer = new ArrayList<String>();
	private ArrayList<Integer> totals = new ArrayList<Integer>();
	//thread
	CountDownLatch cdLatch;
	CountDownLatch loopCdLatch;
	ExecutorService exec;
	PreprocessDictRunnable[] dictRunnables;
	WordsLoopRunnable[] loopRunnable;
	
	public AlgoCore4(int nbCore){
		this.nbCore = nbCore;
		cdLatch = new CountDownLatch(nbCore);
		loopCdLatch = new CountDownLatch(nbCore);
		exec = Executors.newFixedThreadPool(nbCore);
		dictRunnables = new PreprocessDictRunnable[nbCore];
		loopRunnable = new WordsLoopRunnable[nbCore];
		
		hmap = new ConcurrentHashMap<Long, Integer>(nbCore, 0.9f, 1);
	}

	@Override
	protected ArrayList<String> run() {
		int dictArraySampleSize = dictArray.length/nbCore;
		int wordsArraySampleSize = wordsArray.length/nbCore;
		
		for(int i=0;i<nbCore;i++){
			if(i==nbCore-1){
				dictRunnables[i] = new PreprocessDictRunnable(i*dictArraySampleSize,dictArray.length);
			}else{
				dictRunnables[i] = new PreprocessDictRunnable(i*dictArraySampleSize, ((i+1)*dictArraySampleSize));
			}
		}
		
		for(PreprocessDictRunnable r : dictRunnables) {
		    r.setLatch(cdLatch);
		    exec.execute(r);
		}
		
		for(int i=0;i<nbCore;i++){
			if(i==nbCore-1){
				loopRunnable[i] = new WordsLoopRunnable(i*wordsArraySampleSize,wordsArray.length);
			}else{
				loopRunnable[i] = new WordsLoopRunnable(i*wordsArraySampleSize, ((i+1)*wordsArraySampleSize));
			}
		}
		try {
			cdLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(WordsLoopRunnable r : loopRunnable) {
		    r.setLatch(loopCdLatch);
		    exec.execute(r);
		}
		
		try {
			loopCdLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		exec.shutdown();
		
		//int total = loopWordFile();
		int total = 0;
		for (int i = 0; i < totals.size(); i++) {
			total += totals.get(i);
		}
		stringBuffer.add("Il y a un total de " + total + " annagrammes");
		return stringBuffer;
	}
	
	private void preprocessDict(int start, int end){
		for (int i=start;i<end;i++) {
			//creation du key
			Long key = stringToKey(dictArray[i]);
			//remplissage du hashmap
			synchronized(hmap) {
				if(hmap.get(key) == null){
					hmap.put(key, 1);
				}else{
					hmap.put(key, hmap.get(key)+1);
				}
			}
		}
	}
	
	private long stringToKey(String str){
		long key = 1;
		
		//creation de la key
		for (char c: str.toCharArray()) {
			if (c > 47){
				if (c < 58){
					key *= nbPremier[c - 48 + 26];
				} else if (c < 97){
					key *= nbPremier[c - 65];
				} else {
					key *= nbPremier[c - 97];
				}
			}
		}
		
		return key;
	}
	
	private int loopWordFile(int start, int end){
		int counter = 0;
		
		//for (String str: wordsArray) {
		for (int i = start; i < end; i++) {
			Long key = stringToKey(wordsArray[i]);
			//show result
			int value;
			try{
				value = hmap.get(key);
			}catch(Exception e){
				value = 0;
			}
			counter+=value;

			//stringBuffer.add("Il y a " + value +" anagrammes du mot " + wordsArray[i]);
		}
		return counter;
	}
	
	class PreprocessDictRunnable implements Runnable {
		   int start;
		   int end;
		   private CountDownLatch latch;

		   PreprocessDictRunnable(int start, int end) {
		      this.start = start;
		      this.end = end;
		   }

		   public void run() {
			   preprocessDict(start, end);
			   //System.out.println("done "+ start);
			   latch.countDown();
		   }
		   
		   public void setLatch(CountDownLatch latch) {
			    this.latch = latch;
		  }
	}
	
	class WordsLoopRunnable implements Runnable {
		   int start;
		   int end;
		   private CountDownLatch latch;

		   WordsLoopRunnable(int start, int end) {
		      this.start = start;
		      this.end = end;
		   }

		   public void run() {
			   synchronized(totals) {
				   totals.add(loopWordFile(start, end));
			   }
			   //System.out.println("w done "+ start);
			   latch.countDown();
		   }
		   
		   public void setLatch(CountDownLatch latch) {
			    this.latch = latch;
		  }
	}
}

