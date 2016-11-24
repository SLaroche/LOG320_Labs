package Modele;
import java.util.Stack;

import Algo.SamAlgo;
import util.GameState;
import util.Node;
import util.Pos2D;

public class GameTree {
	public Node root;
	
	public GameTree(int playerToStart){
		root = new Node(playerToStart);
	}	
	public String getBestMove() {
		this.generateTree(3);
		//TODO :  Max remplace cette fonction devaluation par la tienne 
		SamAlgo.evalTree(this); //<-- cette fonction		
		
		Node bestNode = root.getChildList().get(0);
		float bestScore =  bestNode.getScore();
		
		for(Node n: root.getChildList()){
			if(n.getScore() > bestScore){
				bestNode = n;
				bestScore = n.getScore();
			}
		}
		
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
	
	public void generateTree(int maxDeepness) {
		Stack<Node> nodesToDig = new Stack<Node>();
		
		nodesToDig.push(this.root);
		
		while(!nodesToDig.isEmpty()){
			Node currentNode = (Node)nodesToDig.pop();
			
			if(currentNode.children.isEmpty() && currentNode.deepness < (maxDeepness-1)){
				currentNode.children.addAll(currentNode.getAllPossibleChild());
				for(Node n : currentNode.getChildList()){
					nodesToDig.push(n);
				}
			}
		}
	}
}

