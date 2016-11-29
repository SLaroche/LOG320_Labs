package Algo.Heuristic;
import java.util.ArrayList;

import util.GameState;

import util.Pos2D;

public class Coupage implements HeuristicInterface {
	private static final float COUPAGE_SCORE = 0.25f;

	@Override
	public float getScore(GameState gameState, int playerNumber) {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<Pos2D>> opponentLinks = null;
		if (playerNumber == gameState.currentPlayer)      opponentLinks = gameState.opponentLinks;
		else if (playerNumber != gameState.currentPlayer) opponentLinks = gameState.playerLinks;
		
		return opponentLinks.size() * COUPAGE_SCORE;
	}

}