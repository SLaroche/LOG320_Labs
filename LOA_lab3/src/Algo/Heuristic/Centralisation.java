package Algo.Heuristic;

import util.GameState;
import util.Pos2D;

public class Centralisation implements HeuristicInterface{
	private float[][] scorePerPosition = new float[][]{
		{-80,-25,-20,-20,-20,-20,-25,-80},
		{-25, 10, 10, 10, 10, 10, 10,-25},
		{-20, 10, 25, 25, 25, 25, 10,-20},
		{-20, 10, 25, 50, 50, 25, 10,-20},
		{-20, 10, 25, 50, 50, 25, 10,-20},
		{-20, 10, 25, 25, 25, 25, 10,-20},
		{-25, 10, 10, 10, 10, 10, 10,-25},
		{-80,-25,-20,-20,-20,-20,-25,-80},
	};


	@Override
	public float getScore(GameState gameState, int playerNumber) {
		float score = 0;
		
		for(Pos2D pos :gameState.pawnOfPlayer[playerNumber]){
			score += scorePerPosition[pos.x][pos.y];
		}
		
		return 0;
	};
}
