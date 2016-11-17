package Algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Algo.Heuristic.HeuristicInterface;
import Algo.Heuristic.Mobility;
import Algo.Heuristic.WinLose;
import Modele.LOAGameLogic;
import util.GameState;
import util.Node;
import util.Pos2D;

public class AlgoTest extends LOAAlgo {
	private long startTime;
	private LOAGameLogic gameLogic;
	private float limit = 0;
	private HeuristicInterface heuristicMobility = new Mobility();
	private HeuristicInterface heuristicWinLose = new WinLose();
	@Override
	public String getBestMove(GameState state, Node tree, LOAGameLogic gameLogic) {
		this.gameLogic = gameLogic;
		startTime = System.currentTimeMillis();
		return getbestNode(tree).getGameState().stringMoveFromParent;
	}
	
	private Node getbestNode(Node tree){
		float currentScoreMax = -1000;
		Node resultNode = null;
		for(Node currentNode : tree.getChildList()){
			float score = minmaxAlphaBeta(currentNode,"Max",-1000,1000);
			currentNode.setScore(score);
			if(currentScoreMax<score)
			{
				currentScoreMax = score;
				resultNode = currentNode;
			}
		}
		
		//system.out.println(//system.currentTimeMillis()-startTime);
		if(System.currentTimeMillis()-startTime<=3000 && limit<4){
			limit++;
			gameLogic.generateTree(resultNode.getGameState(), resultNode);
			resultNode = getbestNode(resultNode);
			limit --;
		}
		tree.sortChildScore();
		return resultNode;
	}
	
	private float minmaxAlphaBeta(Node node, String player, float alpha, float beta){
		List<Node> listChildren = node.getChildList();
		float score = 0;
		if(player.equals("Max")){
			float alphaT = -1000; 
			alphaT = evaluation(node.getGameState());
			for(Node currentNode : listChildren){
				score = minmaxAlphaBeta(currentNode, "Min", max(alpha,alphaT), beta);
				alphaT = max(alphaT,score);
				if(alphaT >= beta)
					return alphaT;
			}
			return alphaT;
		}
		else if(player.equals("Min")){
			float betaT = 1000; 
			betaT = evaluation(node.getGameState());
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
	
	private float min(float score1, float score2)
	{
		if(score1 < score2)
			return score1;
		return score2;
	}
	
	private float max(float score1, float score2)
	{
		if(score1 > score2)
			return score1;
		return score2;
	}

	private float evaluation(GameState state){
		float score = 1;
		int board[][] = state.board;

		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++){
				if(board[i][j] == 1) 
				{
					if(i>1 && i<7)score+=1;
					if(j>1 && j<7)score+=1;
					if(i>2 && i<6)score+=2;
					if(j>2 && j<6)score+=2;
				}
			}
		}
		if(didWinNextTurn(state)) score = 1000;

		score+= heuristicMobility.getScore(state,-1);
		score+= heuristicWinLose.getScore(state,-1);
		//Random scoreR = new Random();
		//score = scoreR.nextInt();
		return score;
	}
	private boolean didWinNextTurn(GameState state){
		int board[][] = state.board;
		int player = state.currentPlayer;
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++){
				if(board[i][j] == player) 
				{
					if(i+1<8 && board[i+1][j]==player)continue;
					else if(i+1<8 && j+1<8 && board[i+1][j+1]==player)continue;
					else if(i+1<8 && j-1>-1 && board[i+1][j-1]==player)continue;
					else if(i-1>-1 && board[i-1][j]==player)continue;
					else if(i-1>-1 && j+1<8 && board[i-1][j+1]==player)continue;
					else if(i-1>-1 && j-1>-1 && board[i-1][j-1]==player)continue;
					else if(j+1<8 && board[i][j+1]==player)continue;
					else if(j-1>-1 && board[i][j-1]==player)continue;
					else return false;
				}
			}
		}
		//system.out.println("god dmdndaijhdasiujbdjsahbadsjhbgdasjbgadsj");
		return true;
	}
}
