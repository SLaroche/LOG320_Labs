package algo;

import java.text.DecimalFormat;

public abstract class AnagramAlgo {
	String dictPath;
	String wordsPath;
	int nbTest = 1;
	
	protected abstract void run();
	
	/**
	 * Execute le timer et les algos
	 */
	public void start(String dictPath,String wordsPath) {
		this.dictPath = dictPath;
		this.wordsPath = wordsPath;
		
		
		DecimalFormat formatter = new DecimalFormat("0.000000000");;
		String durationStr;
		
		//reference http://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java
		System.out.println("start");
		long totalTestDuration = 0;
		long duration;
		for(int i=0;i<nbTest;i++){
			long startTime = System.nanoTime();
			this.run();
			long endTime = System.nanoTime();
			duration = (endTime - startTime);
			totalTestDuration += duration * (1.0/nbTest);
			durationStr = formatter.format((duration)/1000000000d);
			System.out.println("test # "+ (i+1) + " Temps d'exécution : " + durationStr + " secondes");
		}
		durationStr = formatter.format((totalTestDuration)/1000000000d);
		System.out.println("Moyenne Temps d'exécution : " + durationStr + " secondes");
	}
}
