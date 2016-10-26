package puzzle_lab2;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import puzzle_lab2.algo.BackTrackingAlgo;
import puzzle_lab2.algo.PlayShotAlgo;
import puzzle_lab2.model.Case;
import puzzle_lab2.model.GameBord;
import puzzle_lab2.model.Ply;
import puzzle_lab2.tree.Node;

public class PegSolitaire extends JPanel{
	//constantes
	private final int LINESIZE = 4;//pas toute les valeur marche
	//attributs
	private GameBord gameBoard = new GameBord(7,7);
	private Case[][] bord = gameBoard.getBord();
	private BackTrackingAlgo backAlgo;
	
	public PegSolitaire(){
		this.setMaximumSize(new Dimension(500, 500));
		this.setMinimumSize(new Dimension(500, 500));
	}
	public void loadPuzzle(){
		JFileChooser dialogue = new JFileChooser(new File("."));
		File fichier = null;
		if (dialogue.showOpenDialog(null)== 
			    JFileChooser.APPROVE_OPTION) {
			    fichier = dialogue.getSelectedFile();
			}
		if(fichier != null)
			gameBoard.getListFile(fichier.getPath());
		repaint();
	}
	
	public void solveAlgo() {
		backAlgo = new BackTrackingAlgo(gameBoard);
		backAlgo.setStopGame(false);
		long time = System.currentTimeMillis();
		int nbrNodeVisited = 0;
		
		while(!backAlgo.isGameOver() && !backAlgo.getStopGame()){
			nbrNodeVisited = backAlgo.resolveAlgo();
		}
		
		repaint();
		System.out.println("Completed in " + (System.currentTimeMillis() - time) + " ms and "+ nbrNodeVisited +" nodes visited");
		if(!backAlgo.getStopGame()) 
			backAlgo.showVictory();
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		int panWidth = (this.getWidth());
		int panHeight = (this.getHeight());
		
		//background
		g.setColor(Color.WHITE);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

		//border
		g.setColor(Color.GRAY);
		for(int i = 0; i<LINESIZE;i++){ //size
			g2d.drawRect(i, i, this.getWidth()-(i*2)-1, this.getHeight()-(i*2)-1);
		}
		
		//Vertical little Line
		for(int i=0; i<7;i++){//number of block
			for(int j=0; j<LINESIZE;j++){
				int position = j+panWidth/7*i+1;
				g2d.drawLine(position, 0,position, panHeight);
			}
		}
		
		//Horizontal little Line
		for(int i=0; i<7;i++){//number of block
			for(int j=0; j<LINESIZE;j++){
				int position = j+panHeight/7*i+1;
				g2d.drawLine(0, position,panWidth, position);
			}
		}
		
		//Draw Circle
		for(int i=0; i<bord.length;i++){//number of block
			for(int j=0; j<bord[i].length;j++){
				int positionDiv = panHeight/7;
				int diametre = panHeight/7-5;
				//If the case is not playable do nothing
				System.out.print(gameBoard.getValueAt(j, i).getValue());
				if(gameBoard.getValueAt(j, i).getValue() == 0)
					continue;
				g2d.setColor(Color.black);
				g2d.drawOval(positionDiv*j, positionDiv*i, diametre, diametre);
				//If the case is empty but playable
				if(gameBoard.getValueAt(j, i).getValue() == 1)
					g2d.setColor(Color.green);
				//else the case is full and the color is black because of the border
				g2d.fillOval(positionDiv*j, positionDiv*i, diametre, diametre);
					
			}
			System.out.println("");
		}
		System.out.println("");
	}
}
