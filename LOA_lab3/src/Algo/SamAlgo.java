package Algo;

import java.util.Stack;

import Algo.Heuristic.*;
import util.GameState;

public class SamAlgo {
	static public void generateTree(GameState root,int maxDeepness){
		Stack<GameState> GameStateToDig = new Stack<GameState>();
		
		GameStateToDig.push(root);
		
		while(!GameStateToDig.isEmpty()){
			GameState currentState = (GameState) GameStateToDig.pop();
			
			if(currentState.children.isEmpty() && currentState.deepness < maxDeepness){
				currentState.children.addAll(currentState.getAllMove());
				for(int i=0;i<currentState.children.size();i++){
					GameStateToDig.push(currentState.children.get(i));
				}
			}
		}
	}
	
	static public void evalTree(GameState root){
		Stack<GameState> GameStateToEval = new Stack<GameState>();
		HeuristicInterface concentrationHeuristic = new Concentration();
		HeuristicInterface mobilityHeuristic = new Mobility();
		HeuristicInterface maximeHeuristic = new MaximeHeuristic();
		
		GameStateToEval.push(root);
		
		while(!GameStateToEval.isEmpty()){
			GameState currentState = (GameState) GameStateToEval.pop();
			
			float HeuristicScore = 0;
			//HeuristicScore += concentrationHeuristic.getScore(currentState, 1);
			//HeuristicScore += mobilityHeuristic.getScore(currentState, 1);
			HeuristicScore += maximeHeuristic.getScore(currentState, 1);
			
			currentState.gameStateScore = HeuristicScore;
			
			for(int i=0;i<currentState.children.size();i++){
				GameStateToEval.push(currentState.children.get(i));
			}
		}
	}
}
