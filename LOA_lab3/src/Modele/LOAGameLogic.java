package Modele;
import java.util.HashMap;
import java.util.List;

import Algo.AlgoTest;
import Algo.LOAAlgo;
import Frame.LOAGamePanel;
import util.GameState;
import util.Node;
import util.Pos2D;

public class LOAGameLogic {
	public GameState currentGameState;
	private LOAAlgo algo;
	private Node tree;
	HashMap<String,Node> listGameboard;
	
	public LOAGameLogic(int player){
		this.currentGameState = new GameState(player);
		algo = new AlgoTest();
		listGameboard = new HashMap<String,Node>();
		//system.out.println("__start__");
		//system.out.println("Player : " + currentGameState.currentPlayer);
		for (int i = 0; i < currentGameState.board.length; i++) {
			for (int j = 0; j < currentGameState.board[i].length; j++) {
				if (i == 1 && j == 0) {
					//system.out.print("(" + currentGameState.board[j][i] + ") ");
				} else {
					//system.out.print(currentGameState.board[j][i] + " ");
				}
				
			}
			//system.out.println();
		}
	}
	
	public String move(String lastMove) {
		if(lastMove != null){
			lastMove = lastMove.replaceAll("\\s", "");
			lastMove = lastMove.replaceAll("-", "");
			updateBoard(lastMove);
			System.out.println("His move");
			printTab(currentGameState);
		}
		tree = findCurrentTree(currentGameState);
		if(tree.getchild()==null)
			generateTree(currentGameState, tree);
		
		String bestMove = algo.getBestMove(currentGameState, tree, this);
		updateBoard(bestMove);
		//System.out.println("My move");
		//printTab(currentGameState);
		//LOAGamePanel gamePannel = new LOAGamePanel();
		//gamePannel.LOAgame = this;
		//gamePannel.generateTree();
		System.out.println(bestMove);
		return bestMove;
	}
	
	private void  updateBoard(String move){
		Pos2D lastMovePosPawnBegin = GameState.pawnPositionToPositionUtility(move.substring(0, 2));
		Pos2D lastMovePosPawnEnd = GameState.pawnPositionToPositionUtility(move.substring(2, 4));
		currentGameState = new GameState(currentGameState, lastMovePosPawnBegin, lastMovePosPawnEnd);
	}
	public void printTab(GameState state){
		System.out.println("  ABCDEFGH");
		
		for(int i=0;i<8;i++)
		{
			System.out.print(8-i+" ");
			for(int j=0;j<8;j++)
			{
				System.out.print(state.board[j][i]);
			}
			System.out.println("");
		}
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
	public void buildHash(Node currentMove)
	{
		for(Node currentNode : currentMove.getChildList()){
			System.out.println(getStringBoardValue(currentNode.getGameState()));
			listGameboard.put(getStringBoardValue(currentNode.getGameState()), currentNode);
		}
		
	}
	private Node findCurrentTree(GameState state){
		Node tree = listGameboard.get(getStringBoardValue(state));
		System.out.println(getStringBoardValue(state));
		listGameboard = new HashMap<String,Node>();
		if(tree==null){
			System.out.println("no hash");
			return new Node(currentGameState, null);
		}
		else {
			System.out.println("hash work");
			return tree;
		}
	}
	private String getStringBoardValue(GameState state){
		String str = "";
		int[][] board = state.board;
		for(int i=0;i<board.length;i++){
			for(int j=0;j<board[i].length;j++){
				str+=""+board[j][i];
			}
		}
		return str;
	}
}

