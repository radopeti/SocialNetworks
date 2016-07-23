package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node {
	private int value;
	private int distance;
	private double inFlow;
	private double outFlow;
	private List<Edge> edges;
	
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
