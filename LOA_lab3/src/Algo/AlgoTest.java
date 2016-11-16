package Algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Modele.LOAGameLogic;
import util.GameState;
import util.Node;
import util.Pos2D;

public class AlgoTest extends LOAAlgo {
	private long startTime;
	private LOAGameLogic gameLogic;
	@Override
	public String getBestMove(GameState state, Node tree, LOAGameLogic gameLogic) {
		this.gameLogic = gameLogic;
		startTime = System.currentTimeMillis();
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
		/*System.out.println(System.currentTimeMillis()-startTime);
		if(System.currentTimeMillis()-startTime<=2000){
			gameLogic.generateTree(resultNode.getGameState(), resultNode);
			resultNode = getbestNode(resultNode);
		}*/
			
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
		//Random scoreR = new Random();
		//score = scoreR.nextInt();
		return score;
	}
	
	private int evaluationMax(GameState state){
		int score = 1;
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
		//Random scoreR = new Random();
		//score = scoreR.nextInt();
		return score;
	}
	/**
	 * 
	 * @return 1 : win
	 * @return 0 : no win no lose
	 * @return -1 : lose
	 * @return 2 : Draw
	 */
	public int winLoseCondition (GameState state) {
		int opponent = state.currentPlayer == 1 ? 2 : 1 ;
		ArrayList<ArrayList<Pos2D>> playerLinks = state.findLinks(state.getAllPawnPos(state.currentPlayer));
		ArrayList<ArrayList<Pos2D>> opponentLinks = state.findLinks(state.getAllPawnPos(opponent));
		if(playerLinks.size() == 1) {
			return 1;
		} else if (opponentLinks.size() == 1) {
			return -1;
		} else if (playerLinks.size() == 1 && opponentLinks.size() == 1) {
			return 2;
		}
		return 0;
	}
}
