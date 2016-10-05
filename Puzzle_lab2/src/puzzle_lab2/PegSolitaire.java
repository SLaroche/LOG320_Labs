package puzzle_lab2;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javax.swing.JPanel;

public class PegSolitaire extends JPanel {
	//constantes
	private final int LINESIZE = 4;//pas toute les valeur marche
	//attributs
	private int[][] pegSolitaireBoard = new int[9][9];
	
	public PegSolitaire(){
		this.setMaximumSize(new Dimension(500, 500));
		this.setMinimumSize(new Dimension(500, 500));
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
		for(int i=0; i<7;i++){//number of block
			for(int j=0; j<7;j++){
				int positionDiv = panHeight/7;
				int diametre = panHeight/7-5;
				g2d.drawOval(positionDiv*i, positionDiv*j, diametre, diametre);
			}
		}
		
	}
}
