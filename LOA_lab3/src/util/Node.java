package util;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	private Node parent;
	private List<Node> children;
	private GameState value;
	private boolean visited;
	private boolean isLeaf;
	
	public Node (GameState gameState, Node parent) {
		children = new ArrayList<Node>();
		this.parent = parent;
		value = gameState;
		visited = false;
		isLeaf = true;
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

}

