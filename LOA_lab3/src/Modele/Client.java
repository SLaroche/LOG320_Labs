package Modele;
import java.io.*;
import java.net.*;

import Algo.Arena;
import Algo.SamAlgo;


class Client {
	public static void main(String[] args) {

		Socket MyClient;
		BufferedInputStream input;
		BufferedOutputStream output;
		GameTree gameTree = null;
		int[][] board = new int[8][8];
		
		SamAlgo algo = null;
		
		System.out.println("Start Client");
		
		try {
			MyClient = new Socket("localhost", 8888);
			input    = new BufferedInputStream(MyClient.getInputStream());
			output   = new BufferedOutputStream(MyClient.getOutputStream());
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));  
			
			while(true){
				char cmd = 0;

				cmd = (char)input.read();

				// Début de la partie en joueur blanc
				if(cmd == '1'){
					byte[] aBuffer = new byte[1024];

					int size = input.available();
					////system.out.println("size " + size);
					input.read(aBuffer,0,size);
					String s = new String(aBuffer).trim();
					//system.out.println(s);
					String[] boardValues;
					boardValues = s.split(" ");
					int x=0,y=0;
					for(int i=0; i<boardValues.length;i++){
						board[x][y] = Integer.parseInt(boardValues[i]);
						x++;
						if(x == 8){
							x = 0;
							y++;
						}
					}
					gameTree = new GameTree();
					algo = new SamAlgo(1);
					
					//Mon Coup
					System.out.println("Nouvelle partie! Vous jouer blanc, entrez votre premier coup : ");
					String move = gameTree.getBestMove(System.currentTimeMillis()+5000,algo);
					gameTree.updateRoot(move);
					System.out.println(move);
					
					output.write(move.getBytes(),0,move.length());
					output.flush();
				}
				// Début de la partie en joueur Noir
				if(cmd == '2'){
					//system.out.println("Nouvelle partie! Vous jouer noir, attendez le coup des blancs");
					byte[] aBuffer = new byte[1024];

					int size = input.available();
					////system.out.println("size " + size);
					input.read(aBuffer,0,size);
					String s = new String(aBuffer).trim();
					//system.out.println(s);
					String[] boardValues;
					boardValues = s.split(" ");
					int x=0,y=0;
					for(int i=0; i<boardValues.length;i++){
						board[x][y] = Integer.parseInt(boardValues[i]);
						x++;
						if(x == 8){
							x = 0;
							y++;
						}
					}
					gameTree = new GameTree();
					algo = new SamAlgo(2);
				}


				// Le serveur demande le prochain coup
				// Le message contient aussi le dernier coup joué.
				if(cmd == '3'){
					byte[] aBuffer = new byte[16];
					int size = input.available();
					input.read(aBuffer,0,size);
					String lastMove = new String(aBuffer);
					String moveString = lastMove.toUpperCase();
					moveString = moveString.replaceAll("\\s", "");
					moveString = moveString.replaceAll("-", "");
					moveString = moveString.replaceAll(" ", "");
					moveString = moveString.substring(0, 4);
					
					//Dernier Coup
					System.out.println("Dernier coup : "+ moveString);
					gameTree.updateRoot(lastMove);
					
					//Mon Coup
					System.out.println("Entrez votre coup : ");
					String move = gameTree.getBestMove(System.currentTimeMillis()+5000,algo);
					gameTree.updateRoot(move);
					System.out.println(move);
					System.out.println(gameTree.bestLastScore);
					
					output.write(move.getBytes(),0,move.length());
					output.flush();
				}
				// Le dernier coup est invalide
				if(cmd == '4'){
					//Mon Coup
					System.out.println("Coup invalide, entrez un nouveau coup : ");
					String move = gameTree.root.children.get(0).getGameState().stringMoveFromParent;
					gameTree.updateRoot(move);
					System.out.println(move);
					
					output.write(move.getBytes(),0,move.length());
					output.flush();
				}
			}
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}
}
