package algo;

import java.text.DecimalFormat;
import java.util.Date;

public abstract class AnagramAlgo {

	
	protected abstract void run();
	
	public void start() {
		//reference http://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java
		System.out.println("start");
		long startTime = System.nanoTime();
		this.run();
		long endTime = System.nanoTime();
		//long duration = endTime - startTime;
		
		DecimalFormat formatter = new DecimalFormat("0.000000000");
		String duration = formatter.format((endTime - startTime)/1000000000d);
		System.out.println("Temps d'exécution : " + duration + " secondes");
	}
	
}
