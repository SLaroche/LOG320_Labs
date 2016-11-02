import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.text.Position;

public class GameState {
	public ArrayList<GameState> children = new ArrayList<GameState>();
	public int[][] board = new int[8][8];
	private double gameStateScore;
	private int currentPlayer;
	private String stringMoveFromParent;
	
	public static final String[][] positionNameYX = new String[][]{
		{"A8","B8","C8","D8","E8","F8","G8","H8"},
		{"A7","B7","C7","D7","E7","F7","G7","H7"},
		{"A6","B6","C6","D6","E6","F6","G6","H6"},
		{"A5","B5","C5","D5","E5","F5","G5","H5"},
		{"A4","B4","C4","D4","E4","F4","G4","H4"},
		{"A3","B3","C3","D3","E3","F3","G3","H3"},
		{"A2","B2","C2","D2","E2","F2","G2","H2"},
		{"A1","B1","C1","D1","E1","F1","G1","H1"},
	};
	
	//default constructor, setup the initial state
	public GameState(){
		board[1][0] = 2;
		board[2][0] = 2;
		board[3][0] = 2;
		board[4][0] = 2;
		board[5][0] = 2;
		board[6][0] = 2;
		
		board[1][7] = 2;
		board[2][7] = 2;
		board[3][7] = 2;
		board[4][7] = 2;
		board[5][7] = 2;
		board[6][7] = 2;
		
		board[0][1] = 1;
		board[0][2] = 1;
		board[0][3] = 1;
		board[0][4] = 1;
		board[0][5] = 1;
		board[0][6] = 1;
		
		board[7][1] = 1;
		board[7][2] = 1;
		board[7][3] = 1;
		board[7][4] = 1;
		board[7][5] = 1;
		board[7][6] = 1;
		
		this.currentPlayer = 1;
	}
	//change player
	public void changePlayer(){
		currentPlayer = (currentPlayer == 1) ? 2 : 1;
	}
	
	//retourne les GameStates qui sont généré par le pawn qui se deplacer
	private ArrayList<GameState> findPawnMove(int x, int y){
		ArrayList<GameState> result = new ArrayList<GameState>();
		int pawnPlayer;
		
		if(board[x][y] == 1){
			pawnPlayer = 1;
		}else if(board[x][y] == 2){
			pawnPlayer = 2;
		}else{
			System.out.println("pawnPositionVide");
			return null;
		}
		
		String pawnPosition = positionNameYX[x][y];
		int verticalPawn = 0;
		int horizontalPawn= 0;
		int topRightDiagonalPawn = 0;
		int topLeftDiagonalPawn = 0;
		
		//vertical
		for(int i=0;i<8;i++){
			if(board[x][i] != 0){
				verticalPawn++;
			}
		}
		//horizontal
		for(int i=0;i<8;i++){
			if(board[i][y] != 0){
				horizontalPawn++;
			}
		}
		//topRightDiagonal
		topRightDiagonalPawn++;
		//vers top
		for(int i=0;(x+i)<8;i++){
			if(board[x+i][y-i] != 0){
				topRightDiagonalPawn++;
			}
		}
		//vers bot
		for(int i=0;(x-i)>=0;i++){
			if(board[x-i][y+i] != 0){
				topRightDiagonalPawn++;
			}
		}
		//topRightDiagonal
		topLeftDiagonalPawn++;
		//vers top
		for(int i=0;(x-i)>=0;i++){
			if(board[x-i][y-i] != 0){
				topLeftDiagonalPawn++;
			}
		}
		//vers bot
		for(int i=0;(x+i)<8;i++){
			if(board[x+i][y+i] != 0){
				topLeftDiagonalPawn++;
			}
		}
		
		//8 move
		
		//Move Nord
		if(y-verticalPawn >= 0){
			
		}
		
		return result;
	}
	
	public static Point2D pawnPositionToPositionUtility(String letters){
		Point2D result = new Point2D.Double();
		
		int x = letters.charAt(0) - 65;
		int y = 8 - letters.charAt(1);
		
		result.setLocation(x, y);
		
		return result;
	}
}
