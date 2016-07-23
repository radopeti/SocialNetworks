package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represent a simple vertex in a graph.
 * @author Peter Rado
 * 
 */

public class Node {
	//the value of the vertex
	private int value;
	//store the actucal distance, only used by some algorithms
	//like Dijkstra
	//default value is 0
	private int distance;
	//helper properties to distribute the flow when calculating the betweenness
	//of an edge
	private double inFlow;
	private double outFlow;
	//the edges of this node
	private List<Edge> edges;
	
	/**
	 * Constructor
	 * @param value the value of the vertex
	 */
	public Node(int value){
		this.value = value;
		edges = new ArrayList<Edge>();
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public double getInFlow() {
		return inFlow;
	}

	public void setInFlow(double inFlow) {
		this.inFlow = inFlow;
	}

	public double getOutFlow() {
		return outFlow;
	}

	public void setOutFlow(double outFlow) {
		this.outFlow = outFlow;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void addEdge(int end) {
		Node neighbor = new Node(end);
		Edge edge = new Edge(this, neighbor);
		edges.add(edge);
	}

	@Override
	public String toString() {
		return "Node [value=" + value + ", distance=" + distance + ", inFlow=" + inFlow + ", outFlow=" + outFlow + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (value != other.value)
			return false;
		return true;
	}

	
	
	
	
}
