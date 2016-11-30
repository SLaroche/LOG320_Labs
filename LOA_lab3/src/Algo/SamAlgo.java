package Algo;

import Algo.Heuristic.*;
import Modele.GameTree;
import util.Node;

public class SamAlgo {
	public static final float WINLOSE_SCORE = 1000000;
	
	float centralisationWeight;
	float concentrationPlayerWeight;
	float concentrationEnemyWeight;
	float mobilityWeight;
	float quadWeight;
	float wallWeight;
	int playerToWin;
	
	WinLose winLoseHeuristic = new WinLose();
	public boolean timeUp;
	
	
	public SamAlgo(int playerToWin){
		this.centralisationWeight = 0;
		this.concentrationPlayerWeight = 100.0f;
		this.concentrationEnemyWeight = 900.0f;
		this.mobilityWeight = 0;
		this.quadWeight = 0;
		this.wallWeight = 0;
		
		this.playerToWin = playerToWin;
		timeUp = false;
	}
	
	public String getWeightStringFormat(){
		String result="";
		result+= Float.toString(centralisationWeight)+"/";
		result+= Float.toString(concentrationPlayerWeight)+"/";
		result+= Float.toString(concentrationEnemyWeight)+"/";
		result+= Float.toString(mobilityWeight)+"/";
		result+= Float.toString(quadWeight)+"/";
		result+= Float.toString(wallWeight);
		return result;
	}
	
	
	
	public SamAlgo(float centralisationWeight,float concentrationPlayerWeight,float concentrationEnemyWeight,float mobilityWeight,float quadWeight,float wallWeight,int playerToWin){
		this.centralisationWeight = centralisationWeight;
		this.concentrationPlayerWeight = concentrationPlayerWeight;
		this.concentrationEnemyWeight = concentrationEnemyWeight;
		this.mobilityWeight = mobilityWeight;
		this.quadWeight = quadWeight;
		this.wallWeight = wallWeight;
		this.playerToWin = playerToWin;
		
		timeUp = false;
	}
	
	public void evalTree(GameTree tree, int depth,long endTimeMinusBuffer){	
		timeUp = false;
		alphaBeta(tree.root,depth,Integer.MIN_VALUE,Integer.MAX_VALUE,true,endTimeMinusBuffer);
	}
	
	public float evaluateNode(Node node){
		HeuristicInterface centralisationHeuristic = new Centralisation();
		HeuristicInterface concentrationHeuristic = new Concentration();
		HeuristicInterface mobilityHeuristic = new Mobility();
		HeuristicInterface quadHeuristic = new Quad();
		HeuristicInterface wallHeuristic = new Wall();
		
		float HeuristicScore = 0;
		//WinLoss
		HeuristicScore += WINLOSE_SCORE*winLoseHeuristic.getScore(node.getGameState(), playerToWin);
		//Centralisation
		if(centralisationWeight!=0)
			HeuristicScore += centralisationWeight*centralisationHeuristic.getScore(node.getGameState(), playerToWin); //my Score
		//Concentration
		if(concentrationPlayerWeight!=0)
			HeuristicScore += concentrationPlayerWeight*concentrationHeuristic.getScore(node.getGameState(), playerToWin); //my Score
		if(concentrationEnemyWeight!=0)	
		HeuristicScore -= concentrationEnemyWeight*concentrationHeuristic.getScore(node.getGameState(), (playerToWin == 1) ? 2 : 1); //Enemy Score
		//Mobility
		if(mobilityWeight!=0)
			HeuristicScore += mobilityWeight*mobilityHeuristic.getScore(node.getGameState(), playerToWin);
		//Quad Heuristic
		if(quadWeight!=0)
			HeuristicScore += quadWeight*quadHeuristic.getScore(node.getGameState(), playerToWin);
		//Wall Heuristic
		if(wallWeight!=0)
			HeuristicScore += wallWeight*wallHeuristic.getScore(node.getGameState(), playerToWin);
		
		return HeuristicScore - 0.01f*(float)node.deepness;
	}
	
	private float alphaBeta(Node node, int maxDepth, float a, float b, boolean isMax,long endTimeMinusBuffer){
		//Time Up
		if(timeUp){
			return -1;
		}
		if(System.currentTimeMillis() > endTimeMinusBuffer){
			timeUp = true; 
		}
		//if node = leaf
		if(node.deepness == maxDepth-1){
			float score = evaluateNode(node);
			node.score = score;
			return score;
		}
		
		float winLoseScore = winLoseHeuristic.getScore(node.gameState, playerToWin);
		if(winLoseScore!=0){
			node.score = winLoseScore*WINLOSE_SCORE;
			return winLoseScore*WINLOSE_SCORE;
		}
		//if Max
		if(isMax){
			node.children.addAll(node.getAllPossibleChild(playerToWin));
			for(Node child: node.children){
				a  = Math.max(a, alphaBeta(child,maxDepth,a,b,!isMax,endTimeMinusBuffer));
				if(b <= a) break; //pruning
			}
			node.score = a;
			return a;
		}else{ //if Min
			node.children.addAll(node.getAllPossibleChild((playerToWin == 1) ? 2 : 1));
			for(Node child: node.children){
				b  = Math.min(b, alphaBeta(child,maxDepth,a,b,!isMax,endTimeMinusBuffer));
				if(b <= a) break; //pruning
			}
			node.score = b;
			return b;
		}
		
	}
}
