package Modele;
import java.util.List;

import Algo.AlgoTest;
import Algo.LOAAlgo;
import util.GameState;
import util.Pos2D;

public class LOAGameLogic {
	public GameState currentGameState;
	private LOAAlgo algo;
	
	public LOAGameLogic(int player){
		this.currentGameState = new GameState(player);
		algo = new AlgoTest();
		System.out.println("__start__");
		System.out.println("Player : " + currentGameState.currentPlayer);
		List<GameState> list = currentGameState.findPawnMove(new Pos2D(7, 4));
		List<GameState> list2 = currentGameState.getAllMove();
		/*for (GameState gameState : list2) {
			for (int i = 0; i < gameState.board.length; i++) {
				for (int j = 0; j < gameState.board[i].length; j++) {
					if(gameState.posPawnEnd.x==j&&gameState.posPawnEnd.y==i) {
						System.out.print("("+gameState.board[j][i]+")");
					} else {
						System.out.print(" "+gameState.board[j][i]+" ");
					}
				}
				System.out.println();
			}
			System.out.println();
		}*/
		generateTree(currentGameState);
		System.out.println("number of children : " + currentGameState.children.size());
		for (GameState gameState : currentGameState.children) {
			System.out.println("level 0.5 coup player " + gameState.currentPlayer + " " + gameState.posPawnBegin.x + gameState.posPawnBegin.y + " " + gameState.posPawnEnd.x + gameState.posPawnEnd.y);
			for (GameState gameState1 : gameState.children) {
				System.out.println("level 1 coup player " + gameState1.currentPlayer + " " + gameState1.posPawnBegin.x + gameState1.posPawnBegin.y + " " + gameState1.posPawnEnd.x + gameState1.posPawnEnd.y);
			}
		}
	}
	
	public String move(String lastMove) {
		Pos2D lastMovePosPawnBegin = GameState.pawnPositionToPositionUtility(lastMove.substring(0, 6));
		Pos2D lastMovePosPawnEnd = GameState.pawnPositionToPositionUtility(lastMove.substring(lastMove.length()-2, lastMove.length()));
		currentGameState = new GameState(currentGameState, lastMovePosPawnBegin, lastMovePosPawnEnd);
		generateTree(currentGameState);
		return algo.getBestMove(currentGameState);
	}
	
	private void generateTree(GameState state) {
		List <GameState> allMove = state.getAllMove();
		for (GameState stateLel0 : allMove) {
			state.children.add(stateLel0); // Level 0,5 adverser
			List <GameState>  allMoveLvl1 = state.getAllMove();
			for (GameState stateLvl1 : allMoveLvl1) {
				stateLel0.children.add(stateLvl1); // Level 1 prochaine coup
			}
		}
		
	}
}
