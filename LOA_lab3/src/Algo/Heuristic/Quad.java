package Algo.Heuristic;

import java.util.ArrayList;

import util.GameState;
import util.Pos2D;

public class Quad implements HeuristicInterface {
	
	private static final float QUAD_SCORE = 0.5f;
	private static final float CROSS_SCORE = 0.25f;

	@Override
	public float getScore(GameState gameState, int playerNumber) {
		int[][] board = gameState.board;
		int quadCount = 0;
		int crossCount = 0;
		float value = 0f;
		for (int i = 0; i < board.length - 1; i++) {
			for (int j = 0; j < board[i].length - 1; j++) {
				if (board[i][j] == playerNumber) quadCount++; crossCount++;
				if (board[i+1][j] == playerNumber) quadCount++;
				if (board[i][j+1] == playerNumber) quadCount++;
				if (board[i+1][j+1] == playerNumber) quadCount++; crossCount++;
				
				if (quadCount == 3) value += QUAD_SCORE;
				if (quadCount == 4) value += QUAD_SCORE;
				if (quadCount == 2 && crossCount != 1) value += CROSS_SCORE;
				quadCount = 0;
				crossCount = 0;
			}
		}
		return value;
	}

}
