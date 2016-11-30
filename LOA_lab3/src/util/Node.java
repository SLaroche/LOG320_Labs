package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import Algo.Heuristic.Centralisation;
import Algo.Heuristic.Concentration;
import Algo.Heuristic.HeuristicInterface;

public class Node {
	@JsonIgnore
	public Node parent;
	@JsonIgnore
	public int deepness = 0;
	
	@JsonProperty("children")
	public ArrayList<Node> children = new ArrayList<Node>();
	@JsonProperty("score")
	public float score = Float.MIN_VALUE;
	@JsonProperty("gameState")
	public GameState gameState;
	
	@JsonIgnore
	public Node (int playerToStart){
		gameState = new GameState(playerToStart);
	}
	@JsonIgnore
	public Node (GameState gameState, Node parent) {
		this.gameState = gameState;
		if(parent != null){
			this.parent = parent;
			this.deepness = parent.deepness+1;
		}
	}
	@JsonIgnore
	public void sortChildScore(){
		for(int g=0;g<children.size();g++){
			for(int i=0;i<children.size();i++)
			{
				
				if(i+1 < children.size()){
					Node n1 = children.get(i);
					Node n2 = children.get(i+1);
					//System.out.println("hdhadjshdasj"+n1.getScore());
					//System.out.println(n2.getScore());
					if(n1.getScore()<n2.getScore())
					{
						this.children.set(i,n2);
						this.children.set(i+1,n1);
						//System.out.println("hdhadjshdasj"+n1.getScore());
					}
				}
			}
		}
	}
	@JsonIgnore
	public void addChildren(Node child) {
		children.add(child);
	}
	@JsonIgnore
	public List<Node> getChildList(){
		return children;
	}
	@JsonIgnore
	public Node getParent(){
		return parent;
	}
	@JsonIgnore
	public GameState getGameState (){
		return gameState;
	}
	@JsonIgnore
	public float getScore(){
		return score;
	}
	@JsonIgnore
	public void setScore(float score){
		this.score = score;
	}
	@JsonIgnore
	public List<Node> getAllPossibleChild(int playerToWin) {
		List<Node> allPossibleChild = new ArrayList<Node>();
		List<GameState> listGameStateMove = new ArrayList<GameState>(gameState.getAllMove());
		HeuristicInterface centralisationHeuristic = new Centralisation();
		
		/*
		for(GameState gState: listGameStateMove){
			gState.approxScore = centralisationHeuristic.getScore(gState, playerToWin);
		}
		
		Collections.sort(listGameStateMove);
		*/
		
		for(GameState gState: listGameStateMove){
			allPossibleChild.add(new Node(gState,this));
		}
		
		return allPossibleChild;
	}	
}

