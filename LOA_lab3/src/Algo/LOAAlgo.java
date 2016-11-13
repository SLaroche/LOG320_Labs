package Algo;

import java.util.List;

import Modele.LOAGameLogic;
import util.GameState;
import util.Node;

public abstract class LOAAlgo {

	public abstract String getBestMove(GameState state, Node tree, LOAGameLogic gameLogic);
	
	protected Node getBestNode(Node tree){
		int currentScoreMax = -1000;
		Node resultNode = null;
		for(Node currentNode : tree.getChildList()){
			int score = minmaxAlphaBeta(currentNode,"Max",-1000,1000);
			if(currentScoreMax<score)
			{
				currentScoreMax = score;
				resultNode = currentNode;
			}
		}
		return resultNode;
	}
	
	protected int minmaxAlphaBeta(Node node, String player, int alpha, int beta){
		List<Node> listChildren = node.getChildList();
		int score = 0;
		if(player.equals("Max")){
			int alphaT = -1000; 
			alphaT = evaluationMax(node.getGameState());
			for(Node currentNode : listChildren){
				score = minmaxAlphaBeta(currentNode, "Min", max(alpha,alphaT), beta);
				alphaT = max(alphaT,score);
				if(alphaT >= beta)
					return alphaT;
			}
			return alphaT;
		}
		else if(player.equals("Min")){
			int betaT = 1000; 
			betaT = evaluationMin(node.getGameState());
			for(Node currentNode : listChildren){
				score = minmaxAlphaBeta(currentNode, "Max", alpha, min(beta,betaT));
				betaT = min(betaT,score);
				if(betaT <= alpha)
					return betaT;
			}
			return betaT;
		}
		return 0;
	}
	
	private int min(int score1, int score2)
	{
		if(score1 < score2)
			return score1;
		return score2;
	}
	
	private int max(int score1, int score2)
	{
		if(score1 > score2)
			return score1;
		return score2;
	}
	
	protected abstract int evaluationMin(GameState state);
	
	protected abstract int evaluationMax(GameState state);

} 
