package Algo;

import Algo.Heuristic.*;
import Modele.GameTree;
import util.Node;

public class SamAlgo {
	WinLose WinLoseHeuristic = new WinLose();
	public boolean timeUp = false;
	
	public void evalTree(GameTree tree, int depth,long endTimeMinusBuffer){	
		alphaBeta(tree.root,depth,Integer.MIN_VALUE,Integer.MAX_VALUE,true,tree.playerToWin,endTimeMinusBuffer);
	}
	
	public float evaluateNode(Node node,int playerToEval){
		HeuristicInterface concentrationHeuristic = new Concentration();
		HeuristicInterface winLoseHeuristic = new WinLose();
		//HeuristicInterface mobilityHeuristic = new Mobility();
		//HeuristicInterface maximeHeuristic = new MaximeHeuristic();
		HeuristicInterface quadHeuristic = new Quad();
		
		float HeuristicScore = 0;
		HeuristicScore += 1000*winLoseHeuristic.getScore(node.getGameState(), playerToEval);
		if (HeuristicScore >= 1000 || HeuristicScore <= -1000){
			return (float) (HeuristicScore - (0.01*(float)node.deepness));
		}
		HeuristicScore += 40*concentrationHeuristic.getScore(node.getGameState(), playerToEval); //my Score
		HeuristicScore += 1/2*quadHeuristic.getScore(node.getGameState(), playerToEval);
		//HeuristicScore += mobilityHeuristic.getScore(currentState, 1);
		
		
		return (float) (HeuristicScore - (0.01*(float)node.deepness));
	}
	
	private float alphaBeta(Node node, int maxDepth, float a, float b, boolean isMax,int playerToEval,long endTimeMinusBuffer){
		//Time Up
		if(timeUp){
			return -1;
		}
		if(System.currentTimeMillis() > endTimeMinusBuffer){
			timeUp = true; 
		}
		//if node = leaf
		if(node.deepness == maxDepth-1){
			float score = evaluateNode(node,playerToEval);
			node.score = score;
			return score;
		}
		
		float winLoseScore = WinLoseHeuristic.getScore(node.gameState, playerToEval);
		if(winLoseScore!=0){
			return winLoseScore*1000;
		}
		//if Max
		if(isMax){
			node.children.addAll(node.getAllPossibleChild(playerToEval));
			for(Node child: node.children){
				a  = Math.max(a, alphaBeta(child,maxDepth,a,b,!isMax,playerToEval,endTimeMinusBuffer));
				if(b <= a) break; //pruning
			}
			node.score = a;
			return a;
		}else{ //if Min
			node.children.addAll(node.getAllPossibleChild((playerToEval == 1) ? 2 : 1));
			for(Node child: node.children){
				b  = Math.min(b, alphaBeta(child,maxDepth,a,b,!isMax,playerToEval,endTimeMinusBuffer));
				if(b <= a) break; //pruning
			}
			node.score = b;
			return b;
		}
		
	}
}
