import java.util.ArrayList;

public class GameState {
	private int currentPlayer;
	private ArrayList<GameState> childs = new ArrayList<GameState>();
	public ArrayList<String> player1PawnsPosition = new ArrayList<String>();
	public ArrayList<String> player2PawnsPosition = new ArrayList<String>();
	private double BoardScore;
	private String stringMoveFromParent;
	
	public static final String[][] positionName = new String[][]{
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
		//Player1
		player1PawnsPosition.add("A7");
		player1PawnsPosition.add("A6");
		player1PawnsPosition.add("A5");
		player1PawnsPosition.add("A4");
		player1PawnsPosition.add("A3");
		player1PawnsPosition.add("A2");
		
		player1PawnsPosition.add("H7");
		player1PawnsPosition.add("H6");
		player1PawnsPosition.add("H5");
		player1PawnsPosition.add("H4");
		player1PawnsPosition.add("H3");
		player1PawnsPosition.add("H2");
		
		//Player2
		player2PawnsPosition.add("B1");
		player2PawnsPosition.add("C1");
		player2PawnsPosition.add("D1");
		player2PawnsPosition.add("E1");
		player2PawnsPosition.add("F1");
		player2PawnsPosition.add("G1");
		
		player2PawnsPosition.add("B8");
		player2PawnsPosition.add("C8");
		player2PawnsPosition.add("D8");
		player2PawnsPosition.add("E8");
		player2PawnsPosition.add("F8");
		player2PawnsPosition.add("G8");
		
		this.currentPlayer = 1;
	}
	//change player
	private void changePlayer(){
		currentPlayer = (currentPlayer == 1) ? 2 : 1;
	}
	
	//
	private ArrayList<String> findPosibleMove(String pawnPosition){
		ArrayList<String> result = new ArrayList<String>();
		int pawnPlayer;
		
		if(player1PawnsPosition.contains(pawnPosition)){
			pawnPlayer = 1;
		}else if(player2PawnsPosition.contains(pawnPosition)){
			pawnPlayer = 2;
		}else{
			System.out.println("pawnPositionVide");
			return null;
		}
		
		
		
		return result;
	}
}
