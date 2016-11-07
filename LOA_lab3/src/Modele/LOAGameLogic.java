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
