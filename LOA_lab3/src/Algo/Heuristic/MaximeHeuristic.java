package Algo.Heuristic;

import util.GameState;

public class MaximeHeuristic implements HeuristicInterface {

	public float getScore(GameState gameState, int playerNumber) {
		float score = 1;
		int board[][] = gameState.board;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == 1) {
					if (i > 1 && i < 7)
						score += 1;
					if (j > 1 && j < 7)
						score += 1;
					if (i > 2 && i < 6)
						score += 2;
					if (j > 2 && j < 6)
						score += 2;
				}
			}
		}			
		
		return score;
	}
}
