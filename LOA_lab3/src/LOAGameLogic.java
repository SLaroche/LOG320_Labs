
public class LOAGameLogic {
	public GameState currentGameState;
	
	public LOAGameLogic(int player){
		this.currentGameState = new GameState();
	}
	
	public String move(String lastMove) {
		updateCurrentGameState(currentGameState.getChildByPly(lastMove));
		return null;
	}
	
	private void updateCurrentGameState(GameState state) {
		this.currentGameState = state;
	}
	
	private void generateTree(GameState state) {
		GameState [] AllMove = state.getAllMove();
		for (GameState stateLel0 : AllMove) {
			state.children.add(stateLel0);
			GameState [] AllMoveLvl1 = state.getAllMove();
			for (GameState stateLvl1 : AllMoveLvl1) {
				stateLel0.children.add(stateLvl1);
			}
		}
	}
}
