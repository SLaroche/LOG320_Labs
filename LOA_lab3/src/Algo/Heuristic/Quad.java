package Algo.Heuristic;

import java.util.ArrayList;

import util.GameState;
import util.Pos2D;

public class Quad implements HeuristicInterface {

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
				
				if (quadCount == 3) value += 0.5f;
				if (quadCount == 4) value += 0.5f;
				if (quadCount == 2 && crossCount != 1) value += 0.25f;
				quadCount = 0;
				crossCount = 0;
			}
		}
		return value;
	}

}
