package algo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

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
		
		//reference http://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java
		System.out.println("start");
		for(int i=0;i<nbTest;i++){
			this.run();
		}
	}
	public void startTimer(){
		DecimalFormat formatter = new DecimalFormat("0.000000000");;
		String durationStr;
		
		//reference http://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java
		long totalTestDuration = 0;
		long duration;
		for(int i=0;i<nbTest;i++){
			long startTime = System.nanoTime();
			long endTime = System.nanoTime();
			duration = (endTime - startTime);
			totalTestDuration += duration * (1.0/nbTest);
			durationStr = formatter.format((duration)/1000000000d);
			System.out.println("test # "+ (i+1) + " Temps d'exécution : " + durationStr + " secondes");
		}
		durationStr = formatter.format((totalTestDuration)/1000000000d);
		System.out.println("Moyenne Temps d'exécution : " + durationStr + " secondes");
	}
	
	private static ArrayList<String> getListFile(String filePath) throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		String a;
		BufferedReader textInFile = new BufferedReader(new FileReader(new File(filePath)));
		while ((a=textInFile.readLine()) != null) {
			list.add(a.replaceAll(" ", ""));
		}
		return list;
	}
}
