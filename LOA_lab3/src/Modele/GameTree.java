package Modele;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Stack;

import Algo.SamAlgo;
import util.GameState;
import util.Node;
import util.Pos2D;

public class GameTree {
	public Node root;
	public int playerToWin = 0;
	public float bestLastScore = 0;
	public HashMap<String,Node> listEnnemisMove;
	public Node bestNode;
	public GameState lastRootGameState = null;
	
	public GameTree(int playerToWin){
		root = new Node(1);
		this.playerToWin = playerToWin;
		listEnnemisMove = new HashMap<String,Node>();
	}
	
	public void clearTree(){
		if (lastRootGameState == null){
			this.root = new Node(1);
		}else{
			this.root = new Node(lastRootGameState,null);
		}
	}
	
	public String getBestMove(long endTime) {
		long endTimeMinusBuffer = endTime-1000;
		int deepth = 3;
		Node bestNode = null;
		float bestScore = 0;
		String moveString ="nope";
		System.out.println();
		while(deepth<7){
			//this.clearTree();
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
				this.bestLastScore = bestScore;
				moveString = bestNode.getGameState().stringMoveFromParent ;
			}else{
				System.out.println("timeOut");
				break;
			}
		}
		
		System.out.println("children : " +bestNode.getChildList().size());
		this.bestNode = bestNode;
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
		
		if(moveString != null && listEnnemisMove.get(moveString)!= null){
		    this.root = listEnnemisMove.get(moveString);
		    if(this.root.children.size()==0)
		    	System.out.println("Bad guess");
		    else 
		    	System.out.println("Good guess");
	    }
			
		listEnnemisMove.clear();
		this.lastRootGameState = this.root.getGameState();
		//this.root = new Node(newRootGameState,null);
	}
	public void buildHash()
    {
	    listEnnemisMove.clear();
	    for(Node currentNode : bestNode.getChildList()){
	    	if(currentNode.getScore()>bestNode.getScore()) currentNode.children.clear();
	    	currentNode.parent = null;
	    	//System.out.println(currentNode.getScore());
	    	listEnnemisMove.put(currentNode.getGameState().stringMoveFromParent, currentNode);
	    }
	    bestNode = null;
	 }
}