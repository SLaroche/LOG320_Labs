package Algo;

import java.util.ArrayList;
import java.util.List;

import Modele.LOAGameLogic;
import util.GameState;
import util.Node;
import util.Pos2D;

public class AlgoMarc extends LOAAlgo{

	private long startTime;
	private LOAGameLogic gameLogic;
	
	@Override
	public String getBestMove(GameState state, Node tree, LOAGameLogic gameLogic) {
		this.gameLogic = gameLogic;
		startTime = System.currentTimeMillis();
		return super.getBestNode(tree).getGameState().stringMoveFromParent;
	}

	@Override
	protected int evaluationMin(GameState state) {
		return calculatScore(state);
	}

	@Override
	protected int evaluationMax(GameState state) {
		return calculatScore(state);
	}
	
	private int calculatScore(GameState state){
		int[][] board = state.board;
		int currentPlayer = state.currentPlayer;
		List<Pos2D> listPlayerPion = new ArrayList<Pos2D>();
		
		int score = 0;
		for(int row = 0; row< board.length; row++){
			for(int col = 0; col < board.length; col++){
				if(board[row][col] == currentPlayer){
					
					Pos2D pion = new Pos2D(row, col);
					listPlayerPion.add(pion);
					score += pionLinked(board, row, col,currentPlayer);
				}
			}
		}
		return numDepl(listPlayerPion,board) + score;
	}
	
	private int numDepl(List<Pos2D> listPlayerPion, int[][] board){
		int dep = 0;
		
		for(Pos2D posDepart : listPlayerPion){
			for(Pos2D pos : listPlayerPion){
				if(pos.x > posDepart.x){
					dep += pos.x - posDepart.x;
				}else{
					dep+= posDepart.x = pos.x;
				}
				
				if(pos.y > posDepart.y){
					dep+= pos.y - posDepart.y;
				}else{
					dep += posDepart.y -pos.y;
				}
				
			}
		}
		return dep;
	}
	
	private int pionLinked(int[][] board, int row, int col, int player){
		int score = 1;
		if(board[row][col] == player){
			//Up
			if(isLinked(board, row+1, col, player)){
				score+=2;
			}
			
			//Up - Up
			if(isLinked(board, row+2, col, player)){
				score++;
			}
			
			//Up - left
			if(isLinked(board, row+1, col+1, player)){
				score+=2;
			}
			
			// Left
			if(isLinked(board, row, col+1, player)){
				score+=2;
			}
			
			// Left - left
			if(isLinked(board, row, col+2, player)){
				score++;
			}
			
			//Left - down
			if(isLinked(board, row-1, col+1, player)){
				score+=2;
			}
			
			//Down
			if(isLinked(board, row-1, col, player)){
				score+=2;
			}
			
			//Down - Down
			if(isLinked(board, row-2, col, player)){
				score++;
			}
			
			//Down - right
			if(isLinked(board, row-1, col-1, player)){
				score+=2;
			}
			
			// Right
			if(isLinked(board, row, col-1, player)){
				score*=2;
			}
			
			// Right - Right
			if(isLinked(board, row, col-2, player)){
				score++;
			}
			
			// Right - Up 
			if(isLinked(board, row+1, col-1, player)){
				score+=2;
			}
		}
		return score;
	}
	
	private boolean isLinked(int[][] board, int row, int col, int player){
		
		if(board.length <= row || board.length <= col || 0 > row || 0 > col)
			return false;
		
		if(board[row][col] == player)
			return true;
		else
			return false;
	}

}
