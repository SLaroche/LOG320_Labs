package puzzle_lab2.model;

import java.awt.Point;

/**
 * Model qui represente le plateau de jeu
 * @author Marc-Antoine
 *
 */
public class GameBord {

	/*VARIABLE*/
	//Tableau de case
	private Case[][] bord;
	
	/**
	 * Constructeur
	 * @param rowCount : la hauteur du plateau de jeu
	 * @param colCount : la largeur du plateau de jeu
	 */
	public GameBord(int rowCount, int colCount){
		this.bord = new Case[rowCount][colCount];
	}
	
	/**
	 * INSERT une case dans le tableau de case
	 * @param c : la case a inserrer
	 */
	public void insertCase(Case c){
		this.bord[c.getX()][c.getY()] = c;
	}
	
	/**
	 * INSERT une case dans le tableau de case
	 * @param x : position en X
	 * @param y : position en Y
	 * @param value : la valeur de la case
	 */
	public void insertCase(int x, int y, int value){
		Case c = new Case(x, y, value);
		insertCase(c);
	}
	
	/**
	 * INSERT une case dans le tableau de case
	 * @param p : position en X et Y
	 * @param value : la valeur de la case
	 */
	public void insertCase(Point p, int value){
		Case c = new Case(p, value);
		insertCase(c);
	}
	
	/**
	 * GET la valeur au position X et Y
	 * @param x : position X
	 * @param y : position Y
	 * @return la valeur de la case
	 */
	public Case getValueAt(int x, int y){
		return this.bord[x][y];
	}
	
	/**
	 * GET la valeur de la case au position
	 * @param p : position X et Y
	 * @return la valeur de la case
	 */
	public Case getValueAt(Point p){
		return this.bord[(int) p.getX()][(int) p.getY()];
	}
}
