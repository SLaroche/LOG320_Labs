package util;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	private Node parent;
	private List<Node> children;
	private GameState value;
	private boolean visited;
	private boolean isLeaf;
	private int score;
	
	public Node (GameState gameState, Node parent) {
		children = new ArrayList<Node>();
		this.parent = parent;
		value = gameState;
		visited = false;
		isLeaf = true;
		score = 0;
	}
	public void sortChildScore(){
		for(int g=0;g<children.size();g++){
			for(int i=0;i<children.size();i++)
			{
				
				if(i+1 < children.size()){
					Node n1 = children.get(i);
					Node n2 = children.get(i+1);
					System.out.println("hdhadjshdasj"+n1.getScore());
					System.out.println(n2.getScore());
					if(n1.getScore()<n2.getScore())
					{
						this.children.set(i,n2);
						this.children.set(i+1,n1);
						System.out.println("hdhadjshdasj"+n1.getScore());
					}
				}
			}
		}
	}
	public void addChildren(Node child) {
		children.add(child);
		if (isLeaf) {
			isLeaf = false;
		}
	}
	public List<Node> getChildList(){
		return children;
	}
	public Node getchild() {	
		//incremente index after get the child
		for(int i=0;i<children.size();i++){
			Node currentChildren = children.get(i);
			if(!currentChildren.isVisited())
				return currentChildren;
		}	
		return null;
	}
	public Node getParent(){
		return parent;
	}
	
	public boolean isVisited () {
		return visited;
	}
	
	public void setVisited () {
		visited = true;
		children = null;
	}
	
	public Node nextChildToVisit () {
		for (Node node : children) {
			if (!node.isVisited()) {
				return node;
			}
		}
		return null;
	}
	
	public boolean isAllChildrenBeenVisited () {
		if (nextChildToVisit() == null) {
			return true;
		}
		return false;
	}
	
	public GameState getGameState (){
		return value;
	}
	
	public void print() {
		System.out.println(value);
	}
	public int getMax(){
		int max = 1;
		return max;
	}
	public int getMin(){
		int min = 100;
		return min;
	}
	public int getScore(){
		return score;
	}
	public void setScore(int score)
	{
		this.score = score;
	}
}

