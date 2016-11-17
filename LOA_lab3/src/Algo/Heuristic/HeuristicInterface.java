package Algo.Heuristic;

import util.GameState;

public interface HeuristicInterface {
	//playerNumber = player que tu veux qui gagne
	abstract public float getScore(GameState gameState, int playerNumber);
}
