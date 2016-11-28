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
	public String getBestMove(long endTime) {
		int deepth = 4;
		
		SamAlgo.evalTree(this,deepth); //<-- cette fonction		
		
		Node bestNode = root.getChildList().get(0);
		float bestScore =  bestNode.getScore();
		
		for(Node n: root.getChildList()){
			if(n.getScore() > bestScore){
				bestNode = n;
				bestScore = n.getScore();
			}
		}
		bestLastScore = bestScore;
		return bestNode.getGameState().stringMoveFromParent;
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

