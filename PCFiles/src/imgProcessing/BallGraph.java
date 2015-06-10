package imgProcessing;

import java.util.ArrayList;
import java.util.LinkedList;

public class BallGraph {
	private Node[] nodes;
	
	@SuppressWarnings("unchecked")
	public Graph(ArrayList<Integer[]> bolde) {
		int size = bolde.size();
		nodes = new Node[size];
		Integer[] temp;
		for(int i=0;i<size;i++){
			temp = bolde.get(i);
			nodes[i] = new Node(temp[0], temp[1]);
		}
		for(int i =0;i<size;i++){
			for(int j= 0;j<size;j++) {
				//x koordinat er ens og y er nabo
				if (nodes[i].getX() == nodes[j].getX() && nodes[i].getY() == nodes[j].getY()+2){
//					System.out.println("X: "+nodes[i].getX()+","+nodes[j].getX()+" Y: "+nodes[i].getY()+","+nodes[j].getY());
					nodes[i].addNeighbour(nodes[j]);
					nodes[j].addNeighbour(nodes[i]);
				}
				//y koordinat er ens og x er nabo
				else if(nodes[i].getY() == nodes[j].getY() && nodes[i].getX() == nodes[j].getX()+2) {
//					System.out.println("X: "+nodes[i].getX()+","+nodes[j].getX()+" Y: "+nodes[i].getY()+","+nodes[j].getY());
					nodes[i].addNeighbour(nodes[j]);
					nodes[j].addNeighbour(nodes[i]);	
				}
			}
		}
		
	}
	
	public ArrayList<String> filterBalls() {
		int size = nodes.length;
		ArrayList<String> balls = new ArrayList<String>();
		for(int i=0;i<size;i++){
			Node temp = nodes[i];
			if(!temp.isMarked()) {
				if(!temp.getNeighbours().isEmpty()) {
					int[] coords = {0,0,0};
					coords = markNeighbours(temp, coords);
//					System.out.println(coords[0]+" "+coords[1] +" "+coords[2]);
					if(coords[2] > 5) {
					balls.add(coords[0]/coords[2]+";;"+coords[1]/coords[2]);
					}
				}
			}
		}
		return balls;
	}
	
	private int[] markNeighbours(Node node, int[] coords) {
		if(!node.isMarked()) {
			node.mark();
			for(Node neighbour : node.getNeighbours()) {
				coords[0] += node.getX();
				coords[1] += node.getY();
				coords[2]++;
				markNeighbours(neighbour, coords);
			}
		}
		return coords;
	}
	
	private class Node {
		private int x, y;
		private boolean marked;
		private ArrayList<Node> neighbours;
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
			neighbours = new ArrayList<Node>();
			marked = false;
		}
		
		public int getX(){
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		public void addNeighbour(Node node){
			neighbours.add(node);
		}
		
		public ArrayList<Node> getNeighbours(){
			return neighbours;
		}
		
		public boolean isMarked() {
			return marked;
		}
		
		public void mark() {
			marked = true;
		}
	}
}
