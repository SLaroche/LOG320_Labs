package Algo;

import Modele.LOAGameLogic;
import util.GameState;
import util.Node;

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
	
	
	protected int evaluationMin(GameState state){
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
	
	protected int evaluationMax(GameState state){
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
}
