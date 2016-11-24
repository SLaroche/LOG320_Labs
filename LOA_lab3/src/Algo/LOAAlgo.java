package Algo;

import java.util.List;

import Modele.GameTree;
import util.GameState;
import util.Node;

public abstract class LOAAlgo {

	public abstract String getBestMove(GameState state, Node tree, GameTree gameLogic);

	public int findDeepness(Node tree) {
		// TODO Auto-generated method stub
		return 0; 
	}
} 
