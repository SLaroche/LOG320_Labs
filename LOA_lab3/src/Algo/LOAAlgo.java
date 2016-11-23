package Algo;

import java.util.List;

import Modele.LOAGameLogic;
import util.GameState;
import util.Node;

public abstract class LOAAlgo {

	public abstract String getBestMove(GameState state, Node tree, LOAGameLogic gameLogic);

	public int findDeepness(Node tree) {
		// TODO Auto-generated method stub
		return 0; 
	}
} 
