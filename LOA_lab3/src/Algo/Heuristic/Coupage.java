package Algo.Heuristic;
import java.util.ArrayList;

import Modele.Client;
import util.GameState;

import util.Pos2D;

public class Coupage implements HeuristicInterface {
	private static final float COUPAGE_SCORE = 0.1f;
	private static final float COUPAGE_SCORE_AVANCER = 1f;

	@Override
	public float getScore(GameState gameState, int playerNumber) {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<Pos2D>> opponentLinks = null;
		if (playerNumber == gameState.currentPlayer)      opponentLinks = gameState.opponentLinks;
		else if (playerNumber != gameState.currentPlayer) opponentLinks = gameState.playerLinks;
		
		if (Client.turn > 8) {
			return (float)opponentLinks.size() * COUPAGE_SCORE_AVANCER ;
		}
		
		return 0;
	}

}