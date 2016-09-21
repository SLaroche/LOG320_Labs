//reference http://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java

package algo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public abstract class AnagramAlgo {
	private String dictPath;
	private String wordsPath;
	protected String[] dictArray;
	protected String[] wordsArray;
	
	protected abstract void run();
	
	/**
	 * Execute le timer et les algos
	 */
	public void start(String dictPath,String wordsPath) {
		this.dictPath = dictPath;
		this.wordsPath = wordsPath;
		
		ArrayList<String> dictArrayL = getListFile(dictPath);
		ArrayList<String> wordsArrayL = getListFile(wordsPath);
		
		dictArray = dictArrayL.toArray(new String[0]);
		wordsArray = wordsArrayL.toArray(new String[0]);
		
		System.out.println("start");
		
		DecimalFormat formatter = new DecimalFormat("0.000000000");;
		String durationStr;
		
		long startTime = System.nanoTime();
		this.run();
		long endTime = System.nanoTime();
		
		long duration;
		
		duration = (endTime - startTime);
		durationStr = formatter.format((duration)/1000000000d);
		System.out.println("Temps d'execution : " + durationStr + " secondes");
	}
	
	private static ArrayList<String> getListFile(String filePath) {
		ArrayList<String> list = new ArrayList<String>();
		String a;
		BufferedReader textInFile;
		try {
			textInFile = new BufferedReader(new FileReader(new File(filePath)));
		
			while ((a=textInFile.readLine()) != null) {
				list.add(a.replaceAll(" ", ""));
			}
		} catch (Exception e) {
		}
		return list;
	}
}
