package puzzle_lab2.tree;

import java.util.ArrayList;
import java.util.List;
import puzzle_lab2.model.Ply;

public class Node {
	
	private Node parent;
	private List<Node> children;
	private Ply value;
	private boolean visited;
	private boolean isLeaf;
	
	public Node (Ply ply, Node parent) {
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
	
	public Ply getPly (){
		return value;
	}
	
	public void print() {
		System.out.println(value);
	}

}
