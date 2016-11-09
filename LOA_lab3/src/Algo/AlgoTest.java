package Algo;

import java.util.List;
import java.util.Random;

import util.GameState;
import util.Node;

public class AlgoTest extends LOAAlgo {
	@Override
	public String getBestMove(GameState state, Node tree) {
		
		return getbestNode(tree).getGameState().stringMoveFromParent;
	}
	
	private Node getbestNode(Node tree){
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
	
	private int minmaxAlphaBeta(Node node, String player, int alpha, int beta){
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
	
	private int evaluationMin(GameState state){
		int score = 1;
		int board[][] = state.board;
		/*for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++){
				if(board[i][j] == 2) 
				{
					if(j<7 && board[i][j+1] == 2)score++;
					if(i<0){
						if(board[i+1][j] == 2)score++;
						if(j<7 && board[i+1][j+1] == 2)score++;
					}
				}
			}
		}*/
		return score;
	}
	
	private int evaluationMax(GameState state){
		int score = 1;
		int board[][] = state.board;
		/*for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++){
				if(board[i][j] == 1) 
				{
					if(j<7 && board[i][j+1] == 1)score++;
					
					if(i<0){
						if(board[i+1][j] == 1)score++;
						if(j<7 && board[i+1][j+1] == 1)score++;
					}
				}
			}
		}*/
		return score;
	}
}
