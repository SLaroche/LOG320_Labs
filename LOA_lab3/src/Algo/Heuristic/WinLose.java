package Algo.Heuristic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import util.GameState;
import util.Pos2D;

public class WinLose implements HeuristicInterface {

	/**
	 * 
	 * @return 1 : win
	 * @return 0 : no win no lose
	 * @return -1 : lose
	 * @return 2 : Draw
	 */
	@Override
	public int getScore(GameState gameState) {
		int opponent = gameState.currentPlayer == 1 ? 2 : 1 ;
		ArrayList<ArrayList<Pos2D>> playerLinks = findLinks(gameState, gameState.currentPlayer);
		ArrayList<ArrayList<Pos2D>> opponentLinks = findLinks(gameState, opponent);
		if(playerLinks.size() == 1) {
			return 1;
		} else if (opponentLinks.size() == 1) {
			return -1;
		} else if (playerLinks.size() == 1 && opponentLinks.size() == 1) {
			return 2;
		}
		return 0;
	}
	
	public ArrayList<ArrayList<Pos2D>> findLinks(GameState gameState, int player) {
		ArrayList<Pos2D> pawnsPos = gameState.getAllPawnPos(player);
		/*for (Pos2D pos2d : pawnsPos) {
			System.out.print(" " + pos2d.toString() + " ");
		}*/
		//System.out.println();
		HashMap<Pos2D, Integer> zoneMap = new HashMap<Pos2D, Integer>();
		int zoneValue = 1;
		// tab de liaison de links
		for (int i = 0; i < pawnsPos.size(); i++) {
			Pos2D pawn = pawnsPos.get(i);
			if (zoneMap.get(pawn)==null){
				zoneMap.put(pawn, zoneValue++);
				//System.out.println("zone value: " + zoneValue);
			}
			for (int j = i; j < pawnsPos.size(); j++) {
				Pos2D otherPawn = pawnsPos.get(j);
				//System.out.println("pos to compair: " + pawn.toString() + " " + otherPawn.toString());
				if ((pawn.x+1)==otherPawn.x && pawn.y==otherPawn.y) {
					//System.out.println("condition entered: x+1");
					mapLinks(zoneMap, pawn, otherPawn);
				}
				else if ((pawn.x-1)==otherPawn.x && pawn.y==otherPawn.y){
					//System.out.println("condition entered: x-1");
					mapLinks(zoneMap, pawn, otherPawn);				
				}
				else if (pawn.x==otherPawn.x && (pawn.y+1)==otherPawn.y){
					//System.out.println("condition entered: y+1");
					mapLinks(zoneMap, pawn, otherPawn);
				}
				else if (pawn.x==otherPawn.x && (pawn.y-1)==otherPawn.y){
					//System.out.println("condition entered: y-1");
					mapLinks(zoneMap, pawn, otherPawn);
				}
				else if ((pawn.x+1)==otherPawn.x && (pawn.y+1)==otherPawn.y){
					//System.out.println("condition entered: x+1 y+1");
					mapLinks(zoneMap, pawn, otherPawn);
				}
				else if ((pawn.x-1)==otherPawn.x && (pawn.y-1)==otherPawn.y){
					//System.out.println("condition entered: x-1 y-1");
					mapLinks(zoneMap, pawn, otherPawn);
				}
				else if ((pawn.x+1)==otherPawn.x && (pawn.y-1)==otherPawn.y){
					//System.out.println("condition entered: x+1 y-1");
					mapLinks(zoneMap, pawn, otherPawn);
				}
				else if ((pawn.x-1)==otherPawn.x && (pawn.y+1)==otherPawn.y){
					//System.out.println("condition entered: x-1 y+1");
					mapLinks(zoneMap, pawn, otherPawn);
				}
			}
		}
		//faire la list de link
		ArrayList<ArrayList<Pos2D>> links = new ArrayList<ArrayList<Pos2D>>();
		for (int i = 1; i < zoneValue; i++) {
			//System.out.println("zone: " + i);
			ArrayList<Pos2D> link = new ArrayList<Pos2D>();
			Iterator<Pos2D> keySetIterator = zoneMap.keySet().iterator(); 
			while(keySetIterator.hasNext()){ 
				Pos2D key = keySetIterator.next();
				if (zoneMap.get(key)==i){
					link.add(key);
				}
			}
			links.add(link);
		}
		for (ArrayList<Pos2D> arrayList : links) {
			for (Pos2D pos2d : arrayList) {
				System.out.print(" " + pos2d.toString() + " ");
			}
			System.out.println();
		}
		
		if (player == gameState.currentPlayer) {
			gameState.playerLinks = new ArrayList<ArrayList<Pos2D>>(links);
		} else {
			gameState.opponentLinks = new ArrayList<ArrayList<Pos2D>>(links);
		}
		return links;
	}
	
	private void mapLinks (HashMap<Pos2D, Integer> zoneMap, Pos2D pawn, Pos2D otherPawn) {
		if (zoneMap.get(otherPawn)==null){
			zoneMap.put(otherPawn, zoneMap.get(pawn));
		} else if (zoneMap.get(pawn)!=zoneMap.get(otherPawn)){
			//ajouter a liaison
			int value = zoneMap.get(pawn)<zoneMap.get(otherPawn) ? zoneMap.get(pawn) : zoneMap.get(otherPawn);
			int oldValue = zoneMap.get(pawn)>zoneMap.get(otherPawn) ? zoneMap.get(pawn) : zoneMap.get(otherPawn);
			//System.out.println("old new value: " + oldValue + " " + value);
			Iterator<Pos2D> keySetIterator = zoneMap.keySet().iterator(); 
			while(keySetIterator.hasNext()){ 
				Pos2D key = keySetIterator.next();
				if (zoneMap.get(key)==oldValue){
					//System.out.println("changing value: " + key.toString());
					zoneMap.put(key, value);
				}
			}
		}
	}
}
