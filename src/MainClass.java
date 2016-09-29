
import algo.*;

public class MainClass {

	/**
	 * Main.
	 * @param args
	 */
	public static void main(String[] args) {
		//ArrayList<String> wordsList = getListFile(args[0]);
		//ArrayList<String> dictList = getListFile(args[1]);
		//String dictPath = args[1];
		//String dictPath = "src/input/dict.txt";
		//String wordsPath = "src/input/words.txt";
		String dictPath = "src/input/dict.txt";
		String wordsPath =  "src/input/words.txt";
		
		
		//look for how many core available
		int nbCore = Runtime.getRuntime().availableProcessors();
		System.out.println(nbCore + " cores available");
		
		
		warmUpTime(1);
		
		//Competition Algo
		AnagramAlgo algoCore4 = new AlgoCore4(nbCore);
		AnagramAlgo pdfalgo = new PdfAlgo(true);
		
		pdfalgo.start(dictPath,wordsPath);
		System.out.println("^^ = PDF de base");
		
		algoCore4.start(dictPath,wordsPath);
		System.out.println("^^ = Algo");
	}

	/**
	 * 
	 * @param sec
	 */
	private static void warmUpTime(int sec){
		int warmUpTimeSec = sec;
		long startTime = System.currentTimeMillis();
		long endTime= 0;
		long duration = 0;
		long lastSec=0;
		while(duration<(warmUpTimeSec*1000)){
			endTime = System.currentTimeMillis();
			duration = (endTime - startTime);
			if((duration % 1000) == 0){
				if(lastSec != duration/1000){
					lastSec = duration/1000;
					System.out.println("Warm Up Sec " + duration/1000 + "/" + warmUpTimeSec);
				}
			}
		}
	}
}
