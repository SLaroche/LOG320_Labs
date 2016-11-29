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
/*	public float getScore(GameState gameState, int PlayerNumber) {
		int opponent = PlayerNumber == 1 ? 2 : 1 ;
		ArrayList<ArrayList<Pos2D>> playerLinks = findLinks(gameState, gameState.currentPlayer);
		ArrayList<ArrayList<Pos2D>> opponentLinks = findLinks(gameState, opponent);
		if(playerLinks.size() == 1 && gameState.currentPlayer == PlayerNumber)          return 1000;//player num gagne
		else if (opponentLinks.size() == 1 && opponent == PlayerNumber) 	            return 1000;//player num gagne
		else if (playerLinks.size() == 1 && opponent == PlayerNumber)                   return -1000;//ennemie player num gagne
		else if (opponentLinks.size() == 1 && gameState.currentPlayer == PlayerNumber)  return -1000;//ennemie player num gagne
		else if (playerLinks.size() == 1 && opponentLinks.size() == 1)                  return 0;
																					    return 0;
	}*/
	public float getScore(GameState gameState, int PlayerNumber) {
		int opponent = PlayerNumber == 1 ? 2 : 1 ;
		ArrayList<ArrayList<Pos2D>> playerLinks = findLinks(gameState, PlayerNumber);
		ArrayList<ArrayList<Pos2D>> opponentLinks = findLinks(gameState, opponent);

		if (playerLinks.size() == 1 && opponentLinks.size() == 1) return 0.01f; //draw
		else if (playerLinks.size() == 1) return 1; //win
		else if (opponentLinks.size() == 1) return -1; //lose

		return 0;
	}
	
	private ArrayList<ArrayList<Pos2D>> findLinks(GameState gameState, int player) {
		ArrayList<Pos2D> pawnsPos;
		
		pawnsPos = (ArrayList<Pos2D>) gameState.pawnOfPlayer[player];
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
				if ((pawn.x+1)==otherPawn.x && pawn.y==otherPawn.y)          mapLinks(zoneMap, pawn, otherPawn);
				else if ((pawn.x-1)==otherPawn.x && pawn.y==otherPawn.y)     mapLinks(zoneMap, pawn, otherPawn);
				else if (pawn.x==otherPawn.x && (pawn.y+1)==otherPawn.y)     mapLinks(zoneMap, pawn, otherPawn);
				else if (pawn.x==otherPawn.x && (pawn.y-1)==otherPawn.y)     mapLinks(zoneMap, pawn, otherPawn);
				else if ((pawn.x+1)==otherPawn.x && (pawn.y+1)==otherPawn.y) mapLinks(zoneMap, pawn, otherPawn);
				else if ((pawn.x-1)==otherPawn.x && (pawn.y-1)==otherPawn.y) mapLinks(zoneMap, pawn, otherPawn);
				else if ((pawn.x+1)==otherPawn.x && (pawn.y-1)==otherPawn.y) mapLinks(zoneMap, pawn, otherPawn);
				else if ((pawn.x-1)==otherPawn.x && (pawn.y+1)==otherPawn.y) mapLinks(zoneMap, pawn, otherPawn);
			}
		}
		//faire la list de link
		ArrayList<ArrayList<Pos2D>> links = new ArrayList<ArrayList<Pos2D>>();
		for (int i = 1; i < zoneValue; i++) {
			ArrayList<Pos2D> link = new ArrayList<Pos2D>();
			Iterator<Pos2D> keySetIterator = zoneMap.keySet().iterator(); 
			while(keySetIterator.hasNext()){ 
				Pos2D key = keySetIterator.next();
				if (zoneMap.get(key) == i) link.add(key);
			}
			links.add(link);
		}
		zoneMap = null;
		if (player == gameState.currentPlayer) gameState.playerLinks = new ArrayList<ArrayList<Pos2D>>(links);
		else                   		           gameState.opponentLinks = new ArrayList<ArrayList<Pos2D>>(links);
		return links;
	}
	
	/**
	 * Gard en memoir les pions qui sont connecte entre eux, zoneMap represente la liste de pion qui sont dans le meme groupe
	 * @param zoneMap
	 * @param pawn
	 * @param otherPawn
	 */
	private void mapLinks (HashMap<Pos2D, Integer> zoneMap, Pos2D pawn, Pos2D otherPawn) {
		if (zoneMap.get(otherPawn)==null){
			zoneMap.put(otherPawn, zoneMap.get(pawn));
		} else if (zoneMap.get(pawn)!=zoneMap.get(otherPawn)){
			//ajouter a liaison
			int value = zoneMap.get(pawn)<zoneMap.get(otherPawn) ? zoneMap.get(pawn) : zoneMap.get(otherPawn);
			int oldValue = zoneMap.get(pawn)>zoneMap.get(otherPawn) ? zoneMap.get(pawn) : zoneMap.get(otherPawn);
			Iterator<Pos2D> keySetIterator = zoneMap.keySet().iterator(); 
			while(keySetIterator.hasNext()){ 
				Pos2D key = keySetIterator.next();
				if (zoneMap.get(key) == oldValue) zoneMap.put(key, value);
			}
		}
	}
}
