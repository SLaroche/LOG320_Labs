package util;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Position;

public class GameState {
	public ArrayList<GameState> children = new ArrayList<GameState>();
	public GameState parent;
	public int[][] board = new int[8][8];
	public double gameStateScore;
	public int currentPlayer;
	public String stringMoveFromParent;
	
	private static final String[][] positionNameYX = new String[][]{
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
	public GameState(int player){
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
		
		this.currentPlayer = player;
	}
	//constructeur bas� sur un deplacement 
	public GameState(GameState parentGameState,Pos2D posPawnBegin,Pos2D posPawnEnd){
		this.parent = parentGameState;
		this.currentPlayer = (parentGameState.currentPlayer == 1) ? 2 : 1;
		stringMoveFromParent = positionNameYX[posPawnBegin.y][posPawnBegin.x]+positionNameYX[posPawnEnd.y][posPawnEnd.x];
		this.board = GameState.cloneBoard(parentGameState.board);
		this.board[posPawnEnd.x][posPawnEnd.y] = this.board[posPawnBegin.x][posPawnBegin.y];
		this.board[posPawnBegin.x][posPawnBegin.y] = 0;
	}
	
	public GameState updateBoard(Pos2D posPawnBegin,Pos2D posPawnEnd)
	{
		System.out.println(posPawnBegin.x+" "+posPawnBegin.y+" "+posPawnEnd.x+" "+posPawnEnd.y);
		stringMoveFromParent = positionNameYX[posPawnBegin.y][posPawnBegin.x]+positionNameYX[posPawnEnd.y][posPawnEnd.x];
		System.out.println(stringMoveFromParent);
		this.board[posPawnEnd.x][posPawnEnd.y] = this.board[posPawnBegin.x][posPawnBegin.y];
		this.board[posPawnBegin.x][posPawnBegin.y] = 0;
		return this;	
	}
	
	//retourne les GameStates qui sont g�n�r� par le pawn qui se deplacer
	public ArrayList<GameState> findPawnMove(Pos2D pawnPosition){
		ArrayList<GameState> result = new ArrayList<GameState>();
		int enemyPlayer = (currentPlayer == 1) ? 2 : 1;
		int x = pawnPosition.x;
		int y = pawnPosition.y;
		
		if(board[x][y] != currentPlayer){
			return null;
		}
		
		int verticalPawn = 0;
		int horizontalPawn= 0;
		int topRightDiagonalPawn = 0;
		int topLeftDiagonalPawn = 0;
		
		// {{ SetupDirectionCounter
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
		for(int i=1;(x+i)<8&&(y-i)>=0;i++){
			if(board[x+i][y-i] != 0){
				topRightDiagonalPawn++;
			}
		}
		//vers bot
		for(int i=1;(x-i)>=0&&(y+i)<8;i++){
			if(board[x-i][y+i] != 0){
				topRightDiagonalPawn++;
			}
		}
		//topRightDiagonal
		topLeftDiagonalPawn++;
		//vers top
		for(int i=1;(x-i)>=0&&(y-i)>=0;i++){
			if(board[x-i][y-i] != 0){
				topLeftDiagonalPawn++;
			}
		}
		//vers bot
		for(int i=1;(x+i)<8&&(y+i)<8;i++){
			if(board[x+i][y+i] != 0){
				topLeftDiagonalPawn++;
			}
		}
		// }}
		
		// {{ find move for all 8 directions
		//1-Move Nord
		if(y-verticalPawn>=0){
			boolean moveOk = true;
			for(int i=0;i<=verticalPawn-1/*&&(x+i)<8&&(x-i)>=0&&(y+i)<8&&(y-i)>=0*/;i++){
				if(board[x][y-i] == enemyPlayer){
					moveOk = false;
				}
			}
			if(moveOk){
				if(board[x][y-verticalPawn] != currentPlayer){
					Pos2D posPawnEnd = new Pos2D(x,y-verticalPawn);
					result.add(new GameState(this,pawnPosition,posPawnEnd));
				}
			}
		}
		
		//2-Move Sud
		if(y+verticalPawn<8){
			boolean moveOk = true;
			for(int i=0;i<=verticalPawn-1/*&&(x+i)<8&&(x-i)>=0&&(y+i)<8&&(y-i)>=0*/;i++){
				if(board[x][y+i] == enemyPlayer){
					moveOk = false;
				}
			}
			if(moveOk){
				if(board[x][y+verticalPawn] != currentPlayer){
					Pos2D posPawnEnd = new Pos2D(x,y+verticalPawn);
					result.add(new GameState(this,pawnPosition,posPawnEnd));
				}
			}
		}
		
		//3-Move Est
		if(x+horizontalPawn < 8){
			boolean moveOk = true;
			for(int i=0;i<=horizontalPawn-1/*&&(x+i)<8&&(x-i)>=0&&(y+i)<8&&(y-i)>=0*/;i++){
				if(board[x+i][y] == enemyPlayer){
					moveOk = false;
				}
			}
			if(moveOk){
				if(board[x+horizontalPawn][y] != currentPlayer){
					Pos2D posPawnEnd = new Pos2D(x+horizontalPawn,y);
					result.add(new GameState(this,pawnPosition,posPawnEnd));
				}
			}
		}
		
		//4-Move Ouest
		if(x-horizontalPawn >=0){
			boolean moveOk = true;
			for(int i=0;i<=horizontalPawn-1/*&&(x+i)<8&&(x-i)>=0&&(y+i)<8&&(y-i)>=0*/;i++){
				if(board[x-i][y] == enemyPlayer){
					moveOk = false;
				}
			}
			if(moveOk){
				if(board[x-horizontalPawn][y] != currentPlayer){
					Pos2D posPawnEnd = new Pos2D(x-horizontalPawn,y);
					result.add(new GameState(this,pawnPosition,posPawnEnd));
				}
			}
		}
		
		//5-Move Nord-Est
		if(x+topRightDiagonalPawn<8&&y-topRightDiagonalPawn>=0){
			boolean moveOk = true;
			for(int i=0;i<=topRightDiagonalPawn-1/*&&(x+i)<8&&(x-i)>=0&&(y+i)<8&&(y-i)>=0*/;i++){
				if(board[x+i][y-i] == enemyPlayer){
					moveOk = false;
				}
			}
			if(moveOk){
				if(board[x+topRightDiagonalPawn][y-topRightDiagonalPawn] != currentPlayer){
					Pos2D posPawnEnd = new Pos2D(x+topRightDiagonalPawn,y-topRightDiagonalPawn);
					result.add(new GameState(this,pawnPosition,posPawnEnd));
				}
			}
		}
		
		//6-Move Sud-Ouest
		if(x-topRightDiagonalPawn>=0&&y+topRightDiagonalPawn<8){
			boolean moveOk = true;
			for(int i=0;i<=topRightDiagonalPawn-1/*&&(x+i)<8&&(x-i)>=0&&(y+i)<8&&(y-i)>=0*/;i++){
				if(board[x-i][y+i] == enemyPlayer){
					moveOk = false;
				}
			}
			if(moveOk){
				if(board[x-topRightDiagonalPawn][y+topRightDiagonalPawn] != currentPlayer){
					Pos2D posPawnEnd = new Pos2D(x-topRightDiagonalPawn,y+topRightDiagonalPawn);
					result.add(new GameState(this,pawnPosition,posPawnEnd));
				}
			}
		}
		
		//7-Nord-Ouest
		if(x-topLeftDiagonalPawn>=0&&y-topLeftDiagonalPawn>=0){
			boolean moveOk = true;
			for(int i=0;i<=topLeftDiagonalPawn-1/*&&(x+i)<8&&(x-i)>=0&&(y+i)<8&&(y-i)>=0*/;i++){
				if(board[x-i][y-i] == enemyPlayer){
					moveOk = false;
				}
			}
			if(moveOk){
				if(board[x-topLeftDiagonalPawn][y-topLeftDiagonalPawn] != currentPlayer){
					Pos2D posPawnEnd = new Pos2D(x-topLeftDiagonalPawn,y-topLeftDiagonalPawn);
					result.add(new GameState(this,pawnPosition,posPawnEnd));
				}
			}
		}
		
		//8-Sud-Est
		if(x+topLeftDiagonalPawn<8&&y+topLeftDiagonalPawn<8){
			boolean moveOk = true;
			for(int i=0;i<=topLeftDiagonalPawn-1/*&&(x+i)<8&&(x-i)>=0&&(y+i)<8&&(y-i)>=0*/;i++){
				if(board[x+i][y+i] == enemyPlayer){
					moveOk = false;
				}
			}
			if(moveOk){
				if(board[x+topLeftDiagonalPawn][y+topLeftDiagonalPawn] != currentPlayer){
					Pos2D posPawnEnd = new Pos2D(x+topLeftDiagonalPawn,y+topLeftDiagonalPawn);
					result.add(new GameState(this,pawnPosition,posPawnEnd));
				}
			}
		}
		// }}
		
		return result;
	}

	/**
	 * Trouve tous les coups de ce game state, la classe  il connait le joueur qui doit jouer ces coups
	 * Il utilise la methode findPawnMove(int, int) pour trouver tous les coups d'un pion
	 * @return result
	 */
	public List<GameState> getAllMove(){
		ArrayList<GameState> result = new ArrayList<GameState>();
		
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				Pos2D position = new Pos2D(i,j);
				ArrayList<GameState> pawnMovePosition = findPawnMove(position);
				if(pawnMovePosition != null) 
					result.addAll(pawnMovePosition);
			}			
		}
		return result;
	}
	
	/**
	 * Transforme les données rapport par le serveur en donnée utilisable
	 * @param letters
	 * @return la position en point2D
	 */
	public static Pos2D pawnPositionToPositionUtility(String letters){
		int x = letters.charAt(0) - 65;
		int y = 8 - Character.getNumericValue(letters.charAt(1));
		
		return new Pos2D(x,y);
	}
	
	/**
	 * Transforme les données rapport par l'algo en donnée utilisable par le serveur
	 * @param x
	 * @param y
	 * @return position du pion en String
	 */
	public static String pawnPositionToStringUtility(int x, int y){
		return positionNameYX[y][x];
	}
	
	public static int[][] cloneBoard(int [][] board){
		int[][] result = new int[8][8];
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				result[i][j] = board[i][j];
			}
		}
		return result;
	}
}
