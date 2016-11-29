package Modele;
import java.util.Stack;

import Algo.SamAlgo;
import util.GameState;
import util.Node;
import util.Pos2D;

public class GameTree {
	public Node root;
	public int playerToWin = 0;
	public float bestLastScore = 0;
	
	public GameTree(int playerToWin){
		root = new Node(1);
		this.playerToWin = playerToWin;
	}
	
	public void clearTree(){
		this.root = new Node(1);
	}
	
	
	public String getBestMove(long endTime) {
		long endTimeMinusBuffer = endTime-500;
		int deepth = 3;
		Node bestNode = null;
		float bestScore = 0;
		String moveString ="nope";
		System.out.println();
		while(true){
			this.clearTree();
			SamAlgo algo = new SamAlgo();
			
			long deepthBeginTime = System.currentTimeMillis();
			algo.evalTree(this,deepth,endTimeMinusBuffer);
			
			if(algo.timeUp == false){
				System.out.println("deepth = " + deepth + "  Time took = " + (System.currentTimeMillis()-deepthBeginTime));
				deepth++;
				
				bestNode = root.getChildList().get(0);
				bestScore =  bestNode.getScore();
				
				
				
				for(Node n: root.getChildList()){
					if(n.getScore() > bestScore){
						bestNode = n;
						bestScore = n.getScore();
					}
				}
				
				this.bestLastScore = bestScore; //for debug
				moveString = bestNode.getGameState().stringMoveFromParent ;
			}else{
				System.out.println("timeOut");
				break;
			}
		}
		
		System.out.println("children : " +bestNode.getChildList().size());
		
		return moveString;
	}
	public void updateRoot(String move){
		String moveString = move.toUpperCase();
		moveString = moveString.replaceAll("\\s", "");
		moveString = moveString.replaceAll("-", "");
		moveString = moveString.replaceAll(" ", "");
		moveString = moveString.substring(0, 4);
		
		Pos2D lastMovePosPawnBegin = GameState.pawnPositionToPositionUtility(moveString.substring(0, 2));
		Pos2D lastMovePosPawnEnd = GameState.pawnPositionToPositionUtility(moveString.substring(2, 4));
		
		GameState newRootGameState = new GameState(root.getGameState(), lastMovePosPawnBegin, lastMovePosPawnEnd);
		this.root = new Node(newRootGameState,null); 
	}
}

