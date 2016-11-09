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
		return super.getBestNode(tree).getGameState().stringMoveFromParent;
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
		return score;
	}
	
	protected int evaluationMax(GameState state){
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
