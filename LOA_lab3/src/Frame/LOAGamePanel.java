package Frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

public class LOAGamePanel extends JPanel{
	//constantes
	private final int LINESIZE = 4;
	//attributs
	//private LOAGameLogic LOAgame = new LOAGameLogic();
	
	public LOAGamePanel(){
		this.setMaximumSize(new Dimension(501, 501));
		this.setMinimumSize(new Dimension(501, 501));
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
		//int[][] board = LOAgame.currentGameState.board;
				
/*		for(int i=0; i<8; i++){//number of block
			for(int j=0; j<8; j++){
				int positionDiv = panHeight/8;
				int diametre = panHeight/8-13;
				int offsetX = 6;
				int offsetY = 6;
				
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
		}*/
	}
}
