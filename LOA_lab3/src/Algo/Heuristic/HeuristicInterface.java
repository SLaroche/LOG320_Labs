package Algo.Heuristic;

import util.GameState;

public interface HeuristicInterface {
	abstract public float getScore(GameState gameState);
}
