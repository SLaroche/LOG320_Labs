package puzzle_lab2.algo;

import java.util.ArrayList;
import java.util.List;

import puzzle_lab2.model.Case;
import puzzle_lab2.model.Ply;

/**
 * Coup jouer
 * @author Marc-Antoine
 *
 */
public class PlayShotAlgo {

	/*VARIABLE*/
	private static Case[][] bord;
	private List<Ply> listDeplacement;
	
	/*CONSTANTE*/
	private static final int PION = 1;
	private static final int VIDE = 0;
	private static final int TROU = 2;
	
	/**
	 * Trouve tout les coups possible
	 * @param bord : le tableau des piece
	 * @return la liste des coups possible
	 */
	public List<Ply> findShot(Case[][] bord){
		this.bord = bord;
		listDeplacement = new ArrayList<Ply>();
		findPion();
		return listDeplacement;
	}
	
	/**
	 * Parcourt tout les case du plateau afin de trouver tout les pions
	 */
	private void findPion(){
		for(int r = 0; r < bord.length; r++){
			for(int c = 0; c < bord.length; c++){
				if(bord[r][c].getValue() == PION){
					findAllShot(bord[r][c]);
				}
			}
		}
	}
	
	/**
	 * Verifie si la piece peut aller a gauche, droite, haut et bas
	 * @param c : la case de depart
	 */
	private void findAllShot(Case c){		
		Ply pRight = findShotRight(c);
		Ply pLeft = findShotLeft(c);
		Ply pDown = findShotDown(c);
		Ply pUp = findShotUp(c);
		
		if(pRight != null){
			listDeplacement.add(pRight);
		}
		else if(pLeft != null){
			listDeplacement.add(pLeft);
		}
		else if(pDown != null){
			listDeplacement.add(pDown);
		}
		else if(pUp != null){
			listDeplacement.add(pUp);
		}	
	}
	
	/**
	 * Verifie si la piece peut aller a gauche
	 * @param c : la case de depart de la piece
	 * @return le deplacement
	 */
	private Ply findShotLeft(Case c){
		int x = c.getX();
		int y = c.getY();
		
		//Verification que le mouvement n est pas a l exterieur du tableau
		if(x-1 > 0 && x-2 > 0){
			Case c1 = bord[x-1][y];
			Case c2 = bord[x-2][y];
			if(verifiateShot(c1, c2)){
				return new Ply(c, c2);
			}
		}
		return null;
	}
	
	/**
	 * Verifie si la piece peut aller a droite
	 * @param c : la case de depart de la piece
	 * @return le deplacement
	 */
	private Ply findShotRight(Case c){
		int x = c.getX();
		int y = c.getY();
		
		//Verification que le mouvement n est pas a l exterieur du tableau
		if(x+1 < bord.length-1 && x+2 < bord.length-1){
			Case c1 = bord[x+1][y];
			Case c2 = bord[x+2][y];
			if(verifiateShot(c1, c2)){
				return new Ply(c, c2);
			}
		}
		return null;
	}
	
	/**
	 * Verifie si la piece peut aller en haut
	 * @param c : la case de depart de la piece
	 * @return le deplacement
	 */
	private Ply findShotUp(Case c){
		int x = c.getX();
		int y = c.getY();
		
		//Verification que le mouvement n est pas a l exterieur du tableau
		if(y-1 > 0 && y-2 > 0){
			Case c1 = bord[x][y-1];
			Case c2 = bord[x][y-2];
			if(verifiateShot(c1, c2)){
				return new Ply(c, c2);
			}
		}
		return null;
	}
	
	/**
	 * Verifie si la piece peut aller en bas
	 * @param c : la case de depart de la piece
	 * @return la deplacement
	 */
	private Ply findShotDown(Case c){
		int x = c.getX();
		int y = c.getY();
		
		//Verification que le mouvement n est pas a l exterieur du tableau
		if(y+1 < bord.length-1 && y+2 < bord.length-1){
			Case c1 = bord[x][y+1];
			Case c2 = bord[x][y+2];
			if(verifiateShot(c1, c2)){
				return new Ply(c, c2);
			}
		}
		return null;
	}
	
	/**
	 * Verifie si la case1 est un pion et la case2 est un trou,
	 * afin de capturer la piece a la case1.
	 * @param c1 : case a coter de la case de depart
	 * @param c2 : case a coter de la case de depart
	 * @return Vrai si la piece peut capturer la piece a la case1
	 */
	private boolean verifiateShot(Case c1, Case c2){
		return c1.getValue() == PION && c2.getValue() == TROU;
	}
}
