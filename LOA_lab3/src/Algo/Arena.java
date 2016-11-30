package Algo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import Modele.GameTree;

public class Arena {
	public final long TIME_PER_TURN_MILLIS = 5000;
	public int lastWinner = 0;
	
	public static void main(String[] args) {
		Arena arena = new Arena();
		
		while(true){
			String[] lastLine = arena.getLastLine();
			
			SamAlgo bestAlgoYet = arena.getBestAlgoYet(lastLine,1);
			SamAlgo bestAlgoYetMutation =  arena.mutateAlgo(lastLine,1.0f,2);
			
			System.out.println(bestAlgoYet.getWeightStringFormat());
			System.out.println("vs");
			System.out.println(bestAlgoYetMutation.getWeightStringFormat());
			
			arena.start(bestAlgoYet,bestAlgoYetMutation);
		}
	}

	public void start(SamAlgo algo1,SamAlgo algo2){
		GameTree gameTree = new GameTree();
		
		boolean gameOver = false;
		int playerTurn = 1;
		int turnCounter = 1;
		
		while(!gameOver && turnCounter < 100){
			System.out.println("TURN # " + turnCounter);
			turnCounter++;
			if(playerTurn==1){
				System.out.println("Player 1 turn");
				String move = gameTree.getBestMove(System.currentTimeMillis()+TIME_PER_TURN_MILLIS,algo1);
				gameTree.updateRoot(move);
				System.out.println("player 1 play : " + move);
				System.out.println("	move score : " + gameTree.bestLastScore);
				if(gameTree.bestLastScore == SamAlgo.WINLOSE_SCORE){
					gameOver = true;
					System.out.println("player 1 win!");
					writeLog(algo1,false);
					this.lastWinner = 1;
				}else if(gameTree.bestLastScore == -SamAlgo.WINLOSE_SCORE){
					gameOver = true;
					System.out.println("player 2 win!");
					writeLog(algo2,true);
					this.lastWinner = 2;
				}
			}else{
				System.out.println("Player 2 turn");
				String move = gameTree.getBestMove(System.currentTimeMillis()+TIME_PER_TURN_MILLIS,algo2);
				gameTree.updateRoot(move);
				System.out.println("player 2 play : " + move);
				System.out.println("	move score : " + gameTree.bestLastScore);
				if(gameTree.bestLastScore == SamAlgo.WINLOSE_SCORE){
					gameOver = true;
					System.out.println("player 2 win!");
					writeLog(algo2,true);
					this.lastWinner = 2;
				}else if(gameTree.bestLastScore == -SamAlgo.WINLOSE_SCORE){
					gameOver = true;
					System.out.println("player 1 win!");
					writeLog(algo1,false);
					this.lastWinner = 1;
				}
			}
			
			playerTurn = (playerTurn == 1) ? 2 : 1;
		}
	}
	
	public static float getRandomRange(float min, float max){
	   return min + new Random().nextFloat() * (max - min);
	}
	
	public void writeLog(SamAlgo algo,boolean isNewCombinaison) {
		//source: http://alvinalexander.com/java/edu/qanda/pjqa00009.shtml
		BufferedWriter bw = null;
		try {
			// APPEND MODE SET HERE
			
			bw = new BufferedWriter(new FileWriter("WeightLog.txt", true));
			if(isNewCombinaison){
				bw.newLine();
				bw.write("New Best Combinaison");
			}
				bw.newLine();
				bw.write(algo.getWeightStringFormat());
			
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally { // always close the file
			if (bw != null)
				try {
					bw.close();
				} catch (IOException ioe2) {
					// just ignore it
				}
		} // end try/catch/finally

		System.out.println("Log Appended");
	}
	
	public String[] getLastLine() {
		String lastLine = "";

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("WeightLog.txt"));
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) 
		    {
		       
		        lastLine = sCurrentLine;
		    }

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally { // always close the file
			if (br != null){
				try {
					br.close();
				} catch (IOException ioe2) {

				}
			}
		}
		
		System.out.println("last Line = " + lastLine);
		return lastLine.split("/");
	}
	
	public SamAlgo getBestAlgoYet(String[] lastLine,int playerToWin) {
		return new SamAlgo(Float.parseFloat(lastLine[0]),
						Float.parseFloat(lastLine[1]),
						Float.parseFloat(lastLine[2]),
						Float.parseFloat(lastLine[3]),
						Float.parseFloat(lastLine[4]),
						Float.parseFloat(lastLine[5]),
						playerToWin);
	}
	public SamAlgo mutateAlgo(String[] lastLine,float variation,int playerToWin){
		return new SamAlgo(
				clamp(Float.parseFloat(lastLine[0]) + getRandomRange(-variation, variation),0.0f,10.0f),
				clamp(Float.parseFloat(lastLine[1]) + getRandomRange(-variation, variation),0.0f,10.0f),
				clamp(Float.parseFloat(lastLine[2]) + getRandomRange(-variation, variation),0.0f,10.0f),
				clamp(Float.parseFloat(lastLine[3]) + getRandomRange(-variation, variation),0.0f,10.0f),
				clamp(Float.parseFloat(lastLine[4]) + getRandomRange(-variation, variation),0.0f,10.0f),
				clamp(Float.parseFloat(lastLine[5]) + getRandomRange(-variation, variation),0.0f,10.0f),
				playerToWin
				);	
	}
	public static float clamp(float val, float min, float max) {
	    return Math.max(min, Math.min(max, val));
	}
}
