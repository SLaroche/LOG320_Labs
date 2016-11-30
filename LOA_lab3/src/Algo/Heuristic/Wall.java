package Algo.Heuristic;

import java.util.List;

import util.GameState;
import util.Pos2D;

public class Wall implements HeuristicInterface{

	private static final float ESCAPE_SCORE = 6;
	private static final float BLOCK_SCORE = 3;
	
	/**
	 * Get score
	 * @param gameState
	 * @param playerNumber
	 * @return
	 */
	@Override
	public float getScore(GameState gameState, int playerNumber) {
		
		/*
		 + de score lorsque NOUS bloquons une piece
		 
		 ++ de score lorsque nous avons seulement 1 deplacement qui ne touche pas a un autre piece frendly...
		 
		 */
		float scoreWall = 0;
		
		scoreWall += findScoreToBlockingOpponent(gameState, playerNumber);
		scoreWall += findScoreToEscape(gameState, playerNumber);
		
		return scoreWall;
	}
	
	/**
	 * Get la liste des pions du joueur adverse
	 * @param gameState
	 * @param playerNumber
	 * @return
	 */
	private List<Pos2D> getPawnOfOpponent(GameState gameState, int playerNumber){
		
		if(playerNumber == 1){
			return gameState.pawnOfPlayer[2];
		}else{
			return gameState.pawnOfPlayer[1];
		}
	}
	
	/**
	 * Get score lorsque NOUS bloquons une piece
	 * @param gameState
	 * @param playerNumber
	 * @return
	 */
	private float findScoreToBlockingOpponent(GameState gameState, int playerNumber){
		//Alors nous prenons tous les positions des pions 
		// afin de trouver les pions qui ont aucun deplacement
		List<Pos2D> pawn = getPawnOfOpponent(gameState,playerNumber);
		float scoreWall = 0;
		for(Pos2D p : pawn){
			//Get tous les coups posible pour chaque piece adverse
			List<GameState> listGameStateMove = gameState.findPawnMove(p);
			if(listGameStateMove != null && listGameStateMove.size() == 0){
				scoreWall += BLOCK_SCORE;
			}
		}
		
		return scoreWall;
	}
	
	/**
	 * Get score lorsque nous avons seulement un seul deplacement qui ne touche pas une de nos pieces
	 * @param gameState
	 * @param playerNumber
	 * @return
	 */
	private float findScoreToEscape(GameState gameState, int playerNumber){
		List<Pos2D> pawn = gameState.pawnOfPlayer[playerNumber];
		float scoreWall = 0;
		for(Pos2D p : pawn){
			//Get tous les coups posible pour chacune de nos pieces
			List<GameState> pawnMode = gameState.findPawnMove(p);
			if(pawnMode != null && pawnMode.size() == 1){
				int[][] board = pawnMode.get(0).board;
				
				if(isPawnTouchFrendlyPawn(board, p, playerNumber)){
					scoreWall += ESCAPE_SCORE;
				}
			}
		}
		
		return scoreWall;
	}
	
	/**
	 * Verifie si notre piece ne touche pas une autre de nos piece
	 * @param board
	 * @param position
	 * @param player
	 * @return
	 */
	private boolean isPawnTouchFrendlyPawn(int[][] board, Pos2D position, int player){
		
		//Up
		return (isLinked(board, position.x+1, position.y, player)) ||
		//Up - left
				(isLinked(board, position.x+1, position.y+1, player)) ||
		// Left
				(isLinked(board, position.x, position.y+1, player)) ||
		//Left - down
				(isLinked(board, position.x-1, position.y+1, player)) ||
		//Down
				(isLinked(board, position.x-1, position.y, player)) ||
		//Down - right
				(isLinked(board, position.x-1, position.y-1, player)) ||
		// Right
				(isLinked(board, position.x, position.y-1, player)) ||
		// Right - Up 
				(isLinked(board, position.x+1, position.y-1, player));
	}
	
	/**
	 * Verefie si au coordonne(row, col), en parametre, corespond a une piece du player
	 * @param board
	 * @param row
	 * @param col
	 * @param player
	 * @return
	 */
	private boolean isLinked(int[][] board, int row, int col, int player){
		
		if(board.length <= row || board.length <= col || 0 > row || 0 > col)
			return false;
		
		if(board[row][col] == player)
			return true;
		else
			return false;
	}

}
