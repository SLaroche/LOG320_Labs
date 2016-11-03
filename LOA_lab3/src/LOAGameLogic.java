import java.util.List;
import Algo.AlgoTest;
import Algo.LOAAlgo;
import util.GameState;

public class LOAGameLogic {
	public GameState currentGameState;
	private LOAAlgo algo;
	
	public LOAGameLogic(int player){
		this.currentGameState = new GameState(player);
		algo = new AlgoTest();
	}
	
	public String move(String lastMove) {
		currentGameState = currentGameState.getGameStateByMove(lastMove);
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
