package Algo.Heuristic;

import util.GameState;
import util.Pos2D;

public class Concentration implements HeuristicInterface{
	GameState gameState;
	
	int MinSumOfDistances[] = {0,0,1,2,3,4,5,6,7,8,10,12,14}; 
	
	@Override
	public float getScore(GameState gameState, int PlayerNumber) {
		this.gameState = gameState;
		
		//TODO CAREFUL THIS IS HARD CODED TO WORK ONLY IF YOUR PLAYER 1
		return ComputeConcentration(PlayerNumber);
	}
	
	//Compute total distance of all the pieces towards center of mass
	private float ComputeConcentration(int player){
		int nbOfPawn = gameState.pawnOfPlayer[player].size();
		Pos2D centerMass = getCenterOfMass(player);
		int sumOfDistances = 0;
		
		for(int i=0;i<nbOfPawn;i++){
			Pos2D pawnPos = gameState.pawnOfPlayer[player].get(i);
			
			int deltaX = Math.abs(centerMass.x - pawnPos.x);
			int deltaY = Math.abs(centerMass.y - pawnPos.y);
			sumOfDistances += (deltaX > deltaY) ? deltaX : deltaY;
		}
		
		int SurplusOfDistances = sumOfDistances - MinSumOfDistances[nbOfPawn];

		return 1 / SurplusOfDistances;
	}
	
	private Pos2D getCenterOfMass(int player){
		int centerX = 0;
		int centerY = 0;
		int nbOfPawn = gameState.pawnOfPlayer[player].size();
		
		for(int i=0;i<nbOfPawn;i++){
			centerX += gameState.pawnOfPlayer[player].get(i).x;
			centerY += gameState.pawnOfPlayer[player].get(i).y;
		}
		
		return new Pos2D(Math.round(centerX/nbOfPawn),Math.round(centerY/nbOfPawn));
	}
}
