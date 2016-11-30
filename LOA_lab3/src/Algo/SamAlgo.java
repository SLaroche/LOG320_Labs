package Algo;

import Algo.Heuristic.*;
import Modele.GameTree;
import util.Node;

public class SamAlgo {
	public boolean timeUp = false;
	
	public void evalTree(GameTree tree, int depth,long endTimeMinusBuffer){	
		alphaBeta(tree.root,depth,Integer.MIN_VALUE,Integer.MAX_VALUE,true,tree.playerToWin,endTimeMinusBuffer);
	}
	
	public float evaluateNode(Node node,int playerToEval){
		HeuristicInterface winLoseHeuristic = new WinLose();
		HeuristicInterface concentrationHeuristic = new Concentration();
		HeuristicInterface quadHeuristic = new Quad();
		HeuristicInterface centralisationHeuristic = new Centralisation();
		HeuristicInterface wallHeuristic = new Wall();
		HeuristicInterface coupageHeuristic = new Coupage();
		
		float HeuristicScore = 0;
		float win = (float)1000.0 * winLoseHeuristic.getScore(node.getGameState(), playerToEval);
		HeuristicScore += win;
		//System.out.print(win+" ");
		float a = 40*concentrationHeuristic.getScore(node.getGameState(), playerToEval); //my Score
		HeuristicScore += a;
		//System.out.print(a+" ");
		float b = quadHeuristic.getScore(node.getGameState(), playerToEval);
		HeuristicScore += b;
		//System.out.print(b+" ");
		
		if (a  >= 9) {
			float c = centralisationHeuristic.getScore(node.getGameState(), playerToEval); //my Score
			HeuristicScore += c;
			//System.out.print(c+" ");
			float d = wallHeuristic.getScore(node.getGameState(), playerToEval); //my Score
			//HeuristicScore += d;
			//System.out.print(d+" ");
			float e = coupageHeuristic.getScore(node.getGameState(), playerToEval); //my Score
			HeuristicScore += e;
			//System.out.print(e+" ");
		}
		//System.out.println();

		//return HeuristicScore - 0.01f*node.deepness;
		return HeuristicScore;
	}
	
	private float alphaBeta(Node node, int maxDepth, float a, float b, boolean isMax,int playerToEval,long endTimeMinusBuffer){
		//Time Up
		if(node.parent==null)node.deepness = 0;
		else node.deepness = node.parent.deepness+1;
		if(timeUp){
			return -1;
		}
		if(System.currentTimeMillis() > endTimeMinusBuffer){
			timeUp = true; 
		}
		//if node = leaf
		if(node.deepness == maxDepth-1){
			float score = evaluateNode(node,playerToEval);
			node.score = score;
			return score;
		}
		
		WinLose WinLoseHeuristic = new WinLose();
		float winLoseScore = WinLoseHeuristic.getScore(node.gameState, playerToEval);
		if(winLoseScore!=0){
			node.score = winLoseScore*1000;
			return winLoseScore*1000;
		}
		WinLoseHeuristic = null;
		if(timeUp){
			return -1;
		}
		if(System.currentTimeMillis() > endTimeMinusBuffer){
			timeUp = true; 
		}
		if(node.children.size() == 0) node.children.addAll(node.getAllPossibleChild(playerToEval));
		if(timeUp){
			return -1;
		}
		if(System.currentTimeMillis() > endTimeMinusBuffer){
			timeUp = true; 
		}
		//if Max
		if(isMax){
			for(Node child: node.children){
				a  = Math.max(a, alphaBeta(child,maxDepth,a,b,!isMax,playerToEval,endTimeMinusBuffer));
				if(b <= a){
					child.getParent().children.remove(child);
					child = null;
					break; //pruning
				}
			}
			node.score = a;
			return a;
		}else{ //if Min

			node.children.addAll(node.getAllPossibleChild((playerToEval == 1) ? 2 : 1));
			for(Node child: node.children){
				b  = Math.min(b, alphaBeta(child,maxDepth,a,b,!isMax,playerToEval,endTimeMinusBuffer));
				if(b <= a){
					child.getParent().children.remove(child);
					child = null;
					break; //pruning
				}
			}
			node.score = b;
			return b;
		}
		
	}
}
