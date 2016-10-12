package puzzle_lab2.tree;

import java.util.ArrayList;
import java.util.List;
import puzzle_lab2.model.Ply;

public class Node {
	
	private Node parent;
	private List<Node> childrens;
	private int currentIndexChildren = 0;
	private List<Node> children;
	private String value;
	private boolean visited;
	private boolean isLeaf;
	
	public Node (String ply, Node parent) {
		children = new ArrayList<Node>();
		this.parent = parent;
		value = ply;
		visited = false;
		isLeaf = true;
	}
	
	public void addChildren(Node child) {
		children.add(child);
		if (isLeaf) {
			isLeaf = false;
		}
	}
	
	public Node getchild() {
		if(currentIndexChildren>=childrens.size())
			return null;
		//incremente index after get the child
		else if(!childrens.get(currentIndexChildren).isVisited()){
			return childrens.get(currentIndexChildren++);
		}
		else
			return getchild();
			
	}
	public Node getchild(String value) {
		for (Node node : children) {
			if (node.toString().equals(value)) {
				return node;
			}
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
	
	public String toString (){
		return value;
	}
	
	public void print() {
		System.out.println(value);
	}

}
