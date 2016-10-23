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
import java.util.List;
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
	private List<String> listGameboard = new ArrayList<String>();
	private Case[][] bord = gameBoard.getBord();
	private Node currentNode = new Node(null, null);
	private List<Node> listNode = new ArrayList<Node>();
	private PlayShotAlgo algo = new PlayShotAlgo();
	private int nbShot;
	private int topNode = 1000;
	public PegSolitaire(){
		this.setMaximumSize(new Dimension(500, 500));
		this.setMinimumSize(new Dimension(500, 500));
		nbShot = 0;
		addMouseListener(this);
		
	}
	private void buildTree(List<Ply> listPly){
		for(int i=0;i<listPly.size();i++)
		{
			listNode.add(new Node(listPly.get(i),currentNode));
			
			currentNode.addChildren(listNode.get(listNode.size()-1));
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		long time = System.currentTimeMillis();
		while(!isGameOver())
			resolveAlgo();
		System.out.println("Completed in " + (System.currentTimeMillis() - time) + " ms.");
	}
	
	private void resolveAlgo(){
		if(algo.findShot(bord).size() == 0 && !isGameOver())
			goToParentNode();
		
		while(algo.findShot(bord).size() != 0){
			System.out.println(currentNode.getChildList().size());
			if(currentNode.getChildList().size() == 0 && algo.findShot(bord).size() != 0)
				buildTree(algo.findShot(bord));
		
			goToParentNode();
			checkVisitedNode();
			nbShot++;
			currentNode = currentNode.getchild();
			gameBoard.makeMove(currentNode.getPly());
			repaint();
		}
		System.out.println("Fin :"+nbShot);
		repaint();	
	}
	private void goToParentNode(){ 
		if(currentNode.getchild() == null || isTabExist())
		{
			nbShot--;
			currentNode.setVisited();
			listGameboard.add(getStringBoardValue());
			gameBoard.unMove(currentNode.getPly());
			currentNode = currentNode.getParent();
			goToParentNode();
		}
	}
	private boolean isTabExist(){
		String board = getStringBoardValue();
		for(int i=0;i<listGameboard.size();i++){
			if(listGameboard.get(i).equals(board)){
				System.out.println("He esxistsghdksnklasjlkas");
				return true;
			}
		}
		return false;
	}
	private void checkVisitedNode(){
		System.out.println("Total node: "+currentNode.getChildList().size());
		for(int i=0;i<currentNode.getChildList().size();i++)
		{
			if(currentNode.getChildList().get(i).isVisited()){
				System.out.println(currentNode.getChildList().get(i).isVisited());
				System.out.println("How deep"+ nbShot);
				if(topNode>nbShot)
					topNode=nbShot;
			}
		}
		System.out.println("Total modif: "+topNode);
	}
	private String getStringBoardValue(){
		String str = "";
		for(int i=0;i<bord.length;i++){
			for(int j=0;j<bord[i].length;j++){
				str+=""+bord[j][i].getValue();
			}
		}
		return str;
	}
	private boolean isGameOver(){
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
