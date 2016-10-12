package puzzle_lab2.tree;

import java.util.ArrayList;
import java.util.List;
import puzzle_lab2.model.Ply;

public class Node {
	
	private Node parent;
	private List<Node> childrens;
	private String value;
	private boolean visited;
	private boolean isLeaf;
	
	public Node (String ply, Node parent) {
		childrens = new ArrayList<Node>();
		this.parent = parent;
		value = ply;
		visited = false;
		isLeaf = true;
	}
	
	public void addChildren(Node child) {
		childrens.add(child);
		if (isLeaf) {
			isLeaf = false;
		}
	}
	
	public Node getchild(String value) {
		for (Node node : childrens) {
			if (node.toString().equals(value)) {
				return node;
			}
		}
		return null;
	}
	
	public boolean isVisited () {
		return visited;
	}
	
	public void backtrack () {
		visited = true;
	}
	
	public void goForward (String value){
		for (Node node : childrens) {
			if (this.value.equals(value)) {
				break;
			}
		}
	}
	
	public String toString (){
		return value;
	}
	
	public void print() {
		System.out.println(value);
	}

}
