package puzzle_lab2.model;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import puzzle_lab2.algo.PlayShotAlgo;

/**
 * Model qui represente le plateau de jeu
 * @author Marc-Antoine
 *
 */
public class GameBord {

	/*VARIABLE*/
	//Tableau de case
	private static Case[][] bord;
	private PlayShotAlgo shotAlgo;
	
	/**
	 * Constructeur
	 * @param rowCount : la hauteur du plateau de jeu
	 * @param colCount : la largeur du plateau de jeu
	 */
	public GameBord(int rowCount, int colCount){
		this.bord = new Case[rowCount][colCount];
		getListFile("test.puzzle");
		System.out.print(getValueAt(4,4).getValue());
		shotAlgo = new PlayShotAlgo();
		System.out.println("Nombre de coup possible :"+shotAlgo.findShot(bord).size());
	}
	
	/**
	 * INSERT une case dans le tableau de case
	 * @param c : la case a inserrer
	 */
	public static void insertCase(Case c){
		bord[c.getX()][c.getY()] = c;
	}
	
	/**
	 * INSERT une case dans le tableau de case
	 * @param x : position en X
	 * @param y : position en Y
	 * @param value : la valeur de la case
	 */
	public static void insertCase(int x, int y, int value){
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
	
	public Case[][] getBord(){
		return this.bord;
	}
	
	public void makeMove(Ply ply){
		Case start = ply.getCaseStart();
		Case end = ply.getCaseEnd();
		bord[start.getX()][start.getY()].setValue(2);
		bord[start.getX()+(end.getX()-start.getX())][start.getY()+(end.getY()-start.getY())].setValue(2);
		bord[start.getX()][start.getY()].setValue(1);
	}
	public void unMove(Ply ply){
		Case start = ply.getCaseStart();
		Case end = ply.getCaseEnd();
		bord[start.getX()][start.getY()].setValue(1);
		bord[start.getX()+(end.getX()-start.getX())][start.getY()+(end.getY()-start.getY())].setValue(1);
		bord[start.getX()][start.getY()].setValue(2);
	}
	public static void getListFile(String filePath)
	{
		Scanner textInFile;
		
		try {
			textInFile = new Scanner(new File(filePath)).useDelimiter(",\\s*");
			int i = 0;
			while (textInFile.hasNextLine()) 
			{
				String line = textInFile.nextLine();
				for(int j=0;j<line.length();j++)
				{
					insertCase(i,j,Character.getNumericValue(line.charAt(j)));
					System.out.print(line.charAt(j));
				}
				i++;
				System.out.println("");
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
