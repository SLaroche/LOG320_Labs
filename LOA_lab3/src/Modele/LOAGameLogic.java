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
		for (int i = 0; i < currentGameState.board.length; i++) {
			for (int j = 0; j < currentGameState.board[i].length; j++) {
				if (i == 1 && j == 0) {
					System.out.print("(" + currentGameState.board[i][j] + ") ");
				} else {
					System.out.print(currentGameState.board[i][j] + " ");
				}
				
			}
			System.out.println();
		}
		List<GameState> list = currentGameState.findPawnMove(new Pos2D(1, 0));
		System.out.println("number of children : " + currentGameState.children.size());
		for (GameState gameState : list) {
			for (int i = 0; i < gameState.board.length; i++) {
				for (int j = 0; j < gameState.board[i].length; j++) {
						System.out.print(gameState.board[i][j] + " ");
				}
				System.out.println();
			}
		}
	}
	
	public String move(String lastMove) {
		Pos2D lastMovePosPawnBegin = GameState.pawnPositionToPositionUtility(lastMove.substring(0, 2));
		Pos2D lastMovePosPawnEnd = GameState.pawnPositionToPositionUtility(lastMove.substring(lastMove.length()-2, lastMove.length()));
		currentGameState = new GameState(currentGameState, lastMovePosPawnBegin, lastMovePosPawnEnd);
		generateTree(currentGameState);
		return algo.getBestMove(currentGameState);
	}
	
	private void generateTree(GameState state) {
		List <GameState> AllMove= state.getAllMove();
		for (GameState stateLel0 : AllMove) {
			state.children.add(stateLel0); // Level 0,5 adverser
			List <GameState>  AllMoveLvl1 = state.getAllMove();
			for (GameState stateLvl1 : AllMoveLvl1) {
				stateLel0.children.add(stateLvl1); // Level 1 prochaine coup
			}
		}
	}
}
