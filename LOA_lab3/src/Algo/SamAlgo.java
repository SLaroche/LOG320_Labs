package Algo;

import java.util.Stack;

import Algo.Heuristic.*;
import Modele.GameTree;
import util.GameState;
import util.Node;

public class SamAlgo {
	
	static public String findBestMove(GameTree tree){
		tree.generateTree(4);
		evalTree(tree);
		
		return tree.getBestMove();
	}
	
	static public void evalTree(GameTree tree){
		Stack<Node> NodeToEval = new Stack<Node>();
		HeuristicInterface concentrationHeuristic = new Concentration();
		HeuristicInterface mobilityHeuristic = new Mobility();
		HeuristicInterface maximeHeuristic = new MaximeHeuristic();
		
		NodeToEval.push(tree.root);
		
		while(!NodeToEval.isEmpty()){
			Node currentNode = NodeToEval.pop();
			
			float HeuristicScore = 0;
			HeuristicScore += concentrationHeuristic.getScore(currentNode.getGameState(), 1);
			//HeuristicScore += mobilityHeuristic.getScore(currentState, 1);
			//HeuristicScore += maximeHeuristic.getScore(currentState, 1);
			
			currentNode.setScore(HeuristicScore);
			
			for(Node currentNodeChild : currentNode.getChildList()){
				NodeToEval.push(currentNodeChild);
			}
		}
	}
}
