package puzzle_lab2.model;

import java.awt.Point;

/**
 * Model qui represente une case du plateau de jeu
 * @author Marc-Antoine
 *
 */
public class Case {

	/*VARIABLE*/
	//position en X
	private int x;
	//position en Y
	private int y;
	
	//valeur de la case
	private int value;
	
	/**
	 * Constructeur par default
	 */
	public Case(){
		this.x = -1;
		this.y = -1;
		value = 0;
	}
	
	/**
	 * Constructeur
	 * @param x : position en X
	 * @param y : position en Y
	 * @param value : valeur de la case
	 */
	public Case(int x, int y, int value){
		this.x = x;
		this.y = y;
		this.value = value;
	}
	
	/**
	 * Constructeur
	 * @param p : position en X et Y
	 * @param value : valeur de la case
	 */
	public Case(Point p, int value){
		this.x = (int) p.getX();
		this.y = (int) p.getY();
		this.value = value;
	}

	/**
	 * GET la position en X
	 * @return la position en X
	 */
	public int getX() {
		return x;
	}

	/**
	 * SET la position en X
	 * @param x : position en X
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * GET la position en Y
	 * @return la position en Y
	 */
	public int getY() {
		return y;
	}

	/**
	 * SET la position en Y
	 * @param y : position en Y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * GET la valeur de la case
	 * @return la valeur de la case
	 */
	public int getValue() {
		return value;
	}

	/**
	 * SET la valeur de la case
	 * @param value : valeur de la case
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
