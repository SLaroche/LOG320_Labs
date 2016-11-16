package Modele;
import java.util.List;

import Algo.AlgoTest;
import Algo.LOAAlgo;
import util.GameState;
import util.Node;
import util.Pos2D;

public class LOAGameLogic {
	public GameState currentGameState;
	private LOAAlgo algo;
	private Node tree;
	
	public LOAGameLogic(int player){
		this.currentGameState = new GameState(player);
		algo = new AlgoTest();
		System.out.println("__start__");
		System.out.println("Player : " + currentGameState.currentPlayer);
		for (int i = 0; i < currentGameState.board.length; i++) {
			for (int j = 0; j < currentGameState.board[i].length; j++) {
				if (i == 1 && j == 0) {
					System.out.print("(" + currentGameState.board[j][i] + ") ");
				} else {
					System.out.print(currentGameState.board[j][i] + " ");
				}
				
			}
			System.out.println();
		}
		List<GameState> list = currentGameState.findPawnMove(new Pos2D(1, 0));
		//System.out.println("number of children : " + currentGameState.children.size());
	}
	
	public String move(String lastMove) {
		if(lastMove != null){
			lastMove = lastMove.replaceAll("\\s", "");
			lastMove = lastMove.replaceAll("-", "");
			updateBoard(lastMove);
		}
		tree = new Node(currentGameState, null);
		generateTree(currentGameState, tree);
		String bestMove = algo.getBestMove(currentGameState, tree, this);
		updateBoard(bestMove);
		
		return bestMove;
	}
	
	private void  updateBoard(String move){
		Pos2D lastMovePosPawnBegin = GameState.pawnPositionToPositionUtility(move.substring(0, 2));
		Pos2D lastMovePosPawnEnd = GameState.pawnPositionToPositionUtility(move.substring(2, 4));
		currentGameState = new GameState(currentGameState, lastMovePosPawnBegin, lastMovePosPawnEnd);
	}
	
	public void generateTree(GameState state, Node tree) {
		List <GameState> AllMove= state.getAllMove();
		for (GameState stateLel0 : AllMove) {
			Node maxNode = new Node(stateLel0,tree);
			tree.addChildren(maxNode); // Level 0,5 adverser
			List <GameState>  AllMoveLvl1 = stateLel0.getAllMove();
			for (GameState stateLvl1 : AllMoveLvl1) {
				Node minNode = new Node(stateLvl1,maxNode);
				maxNode.addChildren(minNode); // Level 1 prochaine coup
			}
		}
	}
}
