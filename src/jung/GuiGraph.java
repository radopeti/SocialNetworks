package jung;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import edu.uci.ics.jung.algorithms.cluster.EdgeBetweennessClusterer;
import edu.uci.ics.jung.graph.SparseMultigraph;
import graph.CapGraph;
import util.GraphLoader;

public class GuiGraph extends SparseMultigraph<Node<Integer>, Edge<Integer>>{
	
	private graph.Graph capGraph;
	private Node<Integer> selected;
	private List<Node<Integer>> vertices;
	private List<Edge<Integer>> edges;
	
	public GuiGraph(){
		capGraph = new CapGraph();
		GraphLoader.loadGraph(capGraph, "data/facebook_1000.txt");
		GraphLoader.loadGraph(this, "data/facebook_1000.txt");
	}

	public Node<Integer> getSelected() {
		return selected;
	}

	public void setSelected(Node<Integer> selected) {
		this.selected = selected;
	}
	
	public void getEgoNet(){
		if (selected != null){
			System.out.println("wurkin");
			   HashMap<Integer, HashSet<Integer>> egoNet = capGraph.getEgonet(selected.getValue()).exportGraph();
			   ArrayList<Node<Integer>> markedVertices = new ArrayList<>();
			   ArrayList<Edge<Integer>> markedEdges = new ArrayList<>();
			   
		       for (Map.Entry<Integer, HashSet<Integer>> entry : egoNet.entrySet()){
		    	   int v = entry.getKey();
		    	   
		    	   Node<Integer> n1 = new Node<>(v);
	    		   markedVertices.add(n1);
		    	   for (int e : entry.getValue()){
		    		   Node<Integer> n2 = new Node<>(e);
		    		   Edge<Integer> edge = this.findEdge(n1, n2);
		    		   edge.setMarked(true);
		    		   markedEdges.add(edge);
		    		   markedVertices.add(n2);
		    	   }
		       }
		       
		       for (Node<Integer> node : markedVertices){
					//System.out.println("marked -: " + node.toString());
					node.setMarked(false);
				}
		       
		       edges = new ArrayList<>(this.getEdges());
		       edges.retainAll(markedEdges);
		       vertices = new ArrayList<>(this.getVertices());
		       vertices.retainAll(markedVertices);
		       for (Node<Integer> node : vertices){
		    	   //System.out.println("marking " + node);
		    	   node.setMarked(true);
		       }
		}
	}
	
	public void findCommunnities(){
		ArrayList<Node<Integer>> markedVertices = new ArrayList<>();
		ArrayList<Edge<Integer>> markedEdges = new ArrayList<>();
		
		EdgeBetweennessClusterer<Node<Integer>, Edge<Integer>> betweenness = new EdgeBetweennessClusterer<>(10);
		System.out.println(betweenness.transform(this) + " " + betweenness.getEdgesRemoved());
		for (Edge<Integer> edge : betweenness.getEdgesRemoved()){
			edge.setMarked(true);
			markedEdges.add(edge);
		}
		
		edges = new ArrayList<>(this.getEdges());
	    edges.retainAll(markedEdges);
	}
	
	public void unmarkGraph(){
		if (vertices != null){
			for (Node<Integer> node : vertices){
				   System.out.println("unmarking " + node);
				   node.setMarked(false);
			   }
		}
	   
	    if (edges != null){
		    for (Edge<Integer> edge : edges){
			   System.out.println("unmarking " + edge);
			   edge.setMarked(false);
		   }
	   }
	   
	}
}
