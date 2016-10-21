package puzzle_lab2;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javax.swing.JPanel;

import puzzle_lab2.algo.PlayShotAlgo;
import puzzle_lab2.model.Case;
import puzzle_lab2.model.GameBord;
import puzzle_lab2.model.Ply;
import puzzle_lab2.tree.Node;

public class PegSolitaire extends JPanel implements MouseListener{
	//constantes
	private final int LINESIZE = 4;//pas toute les valeur marche
	//attributs
	private GameBord gameBoard = new GameBord(7,7);
	
	private Case[][] bord = gameBoard.getBord();
	private Node currentNode;
	private PlayShotAlgo algo = new PlayShotAlgo();
	private int nbShot;
	
	public PegSolitaire(){
		this.setMaximumSize(new Dimension(500, 500));
		this.setMinimumSize(new Dimension(500, 500));
		nbShot = 0;
		addMouseListener(this);
		
	}
	public void mouseClicked(MouseEvent e) {
		if(algo.findShot(bord).size() != 0){
			nbShot++;
			gameBoard.makeMove(algo.findShot(bord).get(0));
			System.out.println("Nombre de coup possible :"+algo.findShot(bord).size());
			repaint();
		}else{
			System.out.println("FIN DE PARTIE!!! en "+nbShot+" coups");
		}
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
				System.out.print(gameBoard.getValueAt(i, j).getValue());
				if(gameBoard.getValueAt(i, j).getValue() == 0)
					continue;
				g2d.setColor(Color.black);
				g2d.drawOval(positionDiv*i, positionDiv*j, diametre, diametre);
				//If the case is empty but playable
				if(gameBoard.getValueAt(i, j).getValue() == 1)
					g2d.setColor(Color.white);
				//else the case is full and the color is black because of the border
				g2d.fillOval(positionDiv*i, positionDiv*j, diametre, diametre);
					
			}
			System.out.println("");
		}
		System.out.println("");
	}
	public void jump(int index){
		Node nodeParent = currentNode;
		currentNode = nodeParent.getchild();
		if(currentNode == null)
			unjump();
	}
	
	public void unjump(){
		currentNode = currentNode.getParent();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
