package puzzle_lab2.tree;

import java.util.ArrayList;
import java.util.List;
import puzzle_lab2.model.Ply;

public class Node {
	
	private Node parent;
	public int currentIndexChildren = 0;
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
		if(currentIndexChildren>=children.size())
			return null;
		//incremente index after get the child
		else if(children.get(currentIndexChildren).isVisited() == false){
			return children.get(currentIndexChildren);
		}
		else{
			currentIndexChildren++;
			return getchild();
		}
			
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
