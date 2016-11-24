package Algo;

import Algo.Heuristic.*;
import Modele.GameTree;
import util.Node;

public class SamAlgo {
	static public void evalTree(GameTree tree, int depth){				
		alphaBeta(tree.root,depth,Integer.MIN_VALUE,Integer.MAX_VALUE,true,tree.playerToStart);
	}
	
	static public float evaluateNode(Node node,int playerToEval){
		HeuristicInterface concentrationHeuristic = new Concentration();
		HeuristicInterface winLoseHeuristic = new WinLose();
		//HeuristicInterface mobilityHeuristic = new Mobility();
		//HeuristicInterface maximeHeuristic = new MaximeHeuristic();
		//HeuristicInterface quadHeuristic = new Quad();
		
		float HeuristicScore = 0;
		HeuristicScore += winLoseHeuristic.getScore(node.getGameState(), playerToEval);
		HeuristicScore += 2*concentrationHeuristic.getScore(node.getGameState(), playerToEval); //my Score
		HeuristicScore -= concentrationHeuristic.getScore(node.getGameState(), (playerToEval == 1)? 2 : 1); //Enemie Score
		//HeuristicScore += q.getScore(currentNode.getGameState(), tree.playerToStart);
		//HeuristicScore += mobilityHeuristic.getScore(currentState, 1);
		//HeuristicScore += maximeHeuristic.getScore(currentState, 1);
		
		return HeuristicScore;
	}
	
	private static float alphaBeta(Node node, int maxDepth, float a, float b, boolean isMax,int playerToEval){
		//if node = leaf
		if(node.deepness == maxDepth-1){
			float score = evaluateNode(node,playerToEval);
			node.score = score;
			return score;
		}
		//if Max
		if(isMax){
			node.children.addAll(node.getAllPossibleChild());
			for(Node child: node.children){
				a  = Math.max(a, alphaBeta(child,maxDepth,a,b,!isMax,playerToEval));
				if(b <= a) break; //pruning
			}
			node.score = a;
			return a;
		}else{ //if Min
			node.children.addAll(node.getAllPossibleChild());
			for(Node child: node.children){
				b  = Math.min(b, alphaBeta(child,maxDepth,a,b,!isMax,playerToEval));
				if(b <= a) break; //pruning
			}
			node.score = b;
			return b;
		}
		
	}
}
