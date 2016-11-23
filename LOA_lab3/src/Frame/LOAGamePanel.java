package Frame;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Algo.SamAlgo;
import Modele.LOAGameLogic;
import util.GameState;
import util.Pos2D;

public class LOAGamePanel extends JPanel{
	//constantes
	private final int LINESIZE = 4;
	//attributs
	public LOAGameLogic LOAgame = new LOAGameLogic(1);
	ArrayList<Pos2D> possibleMove = new ArrayList<Pos2D>();
	
	public LOAGamePanel(){
		this.setMaximumSize(new Dimension(501, 501));
		this.setMinimumSize(new Dimension(501, 501));
		mouseListenerSetup();
	}
	
	private void mouseListenerSetup() {
		this.addMouseListener(new MouseAdapter() {
			@Override
		    public void mouseClicked(MouseEvent e) {
				int x = e.getX() * 8 / 501;
				int y = e.getY() * 8 / 501;

				possibleMove.clear();
				
		        System.out.println("x:" + x + " y:" + y);
		        
		        ArrayList<GameState> childGameState = LOAgame.currentGameState.findPawnMove(new Pos2D(x,y));
		        
		        
		        if(childGameState != null){
			        for(GameState gState: childGameState){
			        	Pos2D move = GameState.pawnPositionToPositionUtility(gState.stringMoveFromParent.substring(2));
			        	possibleMove.add(move);
			        	System.out.println(move.x +" "+ move.y);
			        }
		        }
		        
		        
		        LOAGamePanel.this.repaint();
		    }
        });
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int panWidth = (this.getWidth());
		int panHeight = (this.getHeight());
		
		//background
		g.setColor(new Color(205,205,205));
		g2d.fillRect(0, 0, this.getWidth()+1, this.getHeight()+1);

		//border
		g.setColor(Color.GRAY);
		for(int i = 0; i<LINESIZE;i++){ //size
			g2d.drawRect(i, i, this.getWidth()-(i*2)-1, this.getHeight()-(i*2)-1);
		}
		
		//Vertical little Line
		for(int i=0; i<8;i++){//number of block
			for(int j=0; j<LINESIZE;j++){
				int position = j+panWidth/8*i+1;
				g2d.drawLine(position, 0,position, panHeight);
			}
		}
		
		//Horizontal little Line
		for(int i=0; i<8;i++){//number of block
			for(int j=0; j<LINESIZE;j++){
				int position = j+panHeight/8*i+1;
				g2d.drawLine(0, position,panWidth, position);
			}
		}
		
		//Draw Pawn
		int[][] board = LOAgame.currentGameState.board;
		
		int positionDiv = panHeight/8;
		int diametre = panHeight/8-13;
		int offsetX = 6;
		int offsetY = 6;
		
		for(int i=0; i<8; i++){//number of block
			for(int j=0; j<8; j++){
				
				
				if(board[j][i] == 1){
					g2d.setColor(Color.white);
					g2d.fillOval(positionDiv*j+offsetX, positionDiv*i+offsetY, diametre+offsetX, diametre+offsetY);
					g2d.setColor(Color.black);
					g2d.drawOval(positionDiv*j+offsetX, positionDiv*i+offsetY, diametre+offsetX, diametre+offsetY);
				}else if(board[j][i] == 2){
					g2d.setColor(Color.blue);
					g2d.fillOval(positionDiv*j+offsetX, positionDiv*i+offsetY, diametre+offsetX, diametre+offsetY);
					g2d.setColor(Color.black);
					g2d.drawOval(positionDiv*j+offsetX, positionDiv*i+offsetY, diametre+offsetX, diametre+offsetY);
				}
			}
		}
		
		//Draw Possible move
		for(Pos2D move: possibleMove){
			g2d.setColor(Color.GREEN);
			System.out.println(move.x*(501/8) + " "+ move.y*(501/8));
			g2d.fillOval(positionDiv*move.x+offsetX, positionDiv*move.y+offsetY, diametre+offsetX, diametre+offsetY);
			g2d.setColor(Color.black);
			g2d.drawOval(positionDiv*move.x+offsetX, positionDiv*move.y+offsetY, diametre+offsetX, diametre+offsetY);
        }
	}

	public void generateTree() {
		ObjectMapper mapper = new ObjectMapper();
		//Object to JSON in String
		GameState root = LOAgame.currentGameState;
		
		//generate Basic Tree for Test
		SamAlgo.generateTree(root,6);
		SamAlgo.evalTree(root);
		
		try {
			String jsonInString = mapper.writeValueAsString(root);
			try{
			    PrintWriter writer = new PrintWriter("GameTree.txt", "UTF-8");
			    writer.print(jsonInString);
			    writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("gameTree Generated");
			
			String indexPath = new java.io.File("").getAbsolutePath();
			indexPath = indexPath.substring(0, indexPath.lastIndexOf('\\')) + "\\LOA_GameTree_Visualization\\index.html";

			File htmlFile = new File(indexPath);
			try {
				Desktop.getDesktop().browse(htmlFile.toURI());
				System.out.println("View Opened");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
