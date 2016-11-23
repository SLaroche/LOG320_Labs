package Algo.Heuristic;

import java.util.List;

import util.GameState;
import util.Pos2D;

public class Mobility implements HeuristicInterface{

	@Override
	public float getScore(GameState gameState , int PlayerNumber) {
		
		//Get tous les childs
		List<GameState> childs = gameState.getAllMove();
		float scoreMobility = 0;
		for(GameState child : childs){
			
			String deplacement = child.stringMoveFromParent;
			if(deplacement != null){
				Pos2D start = child.pawnPositionToPositionUtility(deplacement.substring(0,2));
				Pos2D end = child.pawnPositionToPositionUtility(deplacement.substring(2,4));
				
				//Get le score en fonction des priorite
				scoreMobility += findPriority(gameState, child, start, end, PlayerNumber);
			}
		}
		return scoreMobility;
	}
	
	/**
	 * Donne un score en fonction des priorite
	 * @param parent : board avant deplacement
	 * @param child : board apres du deplacement
	 * @param start : deplacement debut
	 * @param end : deplacement fin
	 * @return retourne le scores
	 */
	private float findPriority(GameState parent, GameState child, Pos2D start, Pos2D end, int playerNumber){
		float score = 1;
		
		//Capture
		if(parent.board[end.x][end.y] != 0){
			//Proche du ledge
			if(end.x <= 1 || end.x >= parent.board.length-1 || end.y <= 1 || end.y >= parent.board.length-1){
				score = 1;
			}
			//coin haut
			else if(end.x == 0 && (end.y == 0 || end.y == parent.board.length)){
				score = 0.5f;
			}
			//coin bas
			else if(end.y == parent.board.length && (end.y == 0 || end.y == parent.board.length)){
				score = 0.5f;
			}
			else{
				score =2;
			}
		}

		return score;
	}

}
