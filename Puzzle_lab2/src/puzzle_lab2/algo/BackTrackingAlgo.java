package puzzle_lab2.algo;

import java.util.ArrayList;
import java.util.List;

import puzzle_lab2.model.Case;
import puzzle_lab2.model.GameBord;
import puzzle_lab2.model.Ply;
import puzzle_lab2.tree.Node;

/**
 * Algo de backtracking
 * @author Maxime
 *
 */
public class BackTrackingAlgo {
	
	private GameBord gameBoard = new GameBord(7,7);
	private Case[][] bord = gameBoard.getBord();
	private List<String> listGameboard = new ArrayList<String>();
	
	private Node currentNode = new Node(null, null);
	private List<Node> listNode = new ArrayList<Node>();
	private PlayShotAlgo algo = new PlayShotAlgo();
	
	private int nbShot;
	private int topNode = 1000;
	private int nbrNodeVisited = 0;
	
	private boolean stopGame = true;
	
	public BackTrackingAlgo(){
		
	}
	
	/**
	 * Construit les nodes enfants et les ajoute au node courant
	 * @param listPly liste des coups possibles � partir de ce node
	 */
	private void buildTree(List<Ply> listPly){
		for(int i=0;i<listPly.size();i++)
		{
			listNode.add(new Node(listPly.get(i),currentNode));	
			currentNode.addChildren(listNode.get(listNode.size()-1));
		}
	}
	
	/**
	 * Joue le mouvement li� au node courant
	 * @return  nbrNodeVisited, le nombre de noeuds visit�s
	 */
	public int resolveAlgo(){
		if(algo.findShot(bord).size() == 0 && !isGameOver())
			goToParentNode();
		
		while(algo.findShot(bord).size() != 0){
			System.out.println(currentNode.getChildList().size());
			if(currentNode.getChildList().size() == 0 && algo.findShot(bord).size() != 0)
				buildTree(algo.findShot(bord));
		
			goToParentNode();
			nbShot++;
			currentNode = currentNode.getchild();
			gameBoard.makeMove(currentNode.getPly());
			nbrNodeVisited++;
		}
		System.out.println("Fin :"+nbShot);	
		
		return nbrNodeVisited;
	}
	/**
	 * Retourne au noeud parent si tout les enfants ont �t� vus ou si le tableau a d�j� �t� vu
	 */
	private void goToParentNode(){ 
		if(currentNode.getParent() == null)
		{
			System.out.println("Aucune solution trouv�");
			stopGame = true;
		}
		
		boolean isCurrentBoardExist = isTabExist();
		
		if(currentNode.getchild() == null || isCurrentBoardExist)
		{
			if(!isCurrentBoardExist)
				listGameboard.add(getStringBoardValue());
			
			nbShot--;
			currentNode.setVisited();
			
			gameBoard.unMove(currentNode.getPly());
			currentNode = currentNode.getParent();
			
			
			goToParentNode();
		}
	}
	/**
	 * V�rifie si le tableau n'a pas d�j� �t� vu dans le pass�
	 * @return  si le tableau courant a d�j� �t� vu
	 */
	private boolean isTabExist(){
		String board = getStringBoardValue();
		System.out.println("Total board: " + listGameboard.size());
		for(int i=0;i<listGameboard.size();i++){
			if(listGameboard.get(i).equals(board)){
				System.out.println("He esxistsghdksnklasjlkas");
				return true;
			}
		}
		return false;
	}
	/**
	 * transforme le tableau courant en string pour le comparer avec les autres
	 * @return  str, le tableau en string
	 */
	private String getStringBoardValue(){
		String str = "";
		for(int i=0;i<bord.length;i++){
			for(int j=0;j<bord[i].length;j++){
				str+=""+bord[j][i].getValue();
			}
		}
		return str;
	}
	/**
	 * V�rifie s'il ne reste qu'un seul pion, donc que la partie est termin�
	 * @return  si la partie est termin�
	 */
	public boolean isGameOver(){
		int nbrPion = 0;
		for(int i=0;i<bord.length;i++){
			for(int j=0;j<bord[i].length;j++){
				if(bord[j][i].getValue() == 1)
					nbrPion++;
			}
		}
		if(nbrPion > 1)
			return false;
		else 
			return true;
	}
	
	public boolean getStopGame(){
		return stopGame;
	}
}
