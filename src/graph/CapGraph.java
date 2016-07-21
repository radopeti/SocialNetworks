/**
 * 
 */
package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import util.GraphLoader;

/**
 * @author Peter Rado
 * 
 * For the warm up assignment, you must implement your Graph in a class
 * named CapGraph.  Here is the stub file.
 *
 */
public class CapGraph implements Graph {
	
	private HashMap<Integer, HashSet<Integer>> adjList;
	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	 */
	public CapGraph() {
		adjList = new HashMap<>();
	}
	
	@Override
	public void addVertex(int num) {
		// TODO Auto-generated method stub
		if (adjList.containsKey(num)) {
			System.err.println("The graph already contains " + num);
			return;
		}
		adjList.put(num, new HashSet<Integer>());
	}

	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub
		if (!adjList.containsKey(from)){
			System.err.println("Can't add edge " + to + " to " + from);
			return;
		}
		adjList.get(from).add(to);
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 * Egonet returns a graph which represent (in a social network)
	 * all friend of the center, and the connection between them.
	 */
	@Override
	public Graph getEgonet(int center) {
		Graph egoGraph = new CapGraph();
		egoGraph.addVertex(center);
		HashSet<Integer> neighbors = adjList.get(center);
		for (int n : neighbors){
			egoGraph.addVertex(n);
			egoGraph.addEdge(center, n);
			if (isNeighbors(n, center)) egoGraph.addEdge(n, center);
			
			Set<Integer> neighborsOfN = new HashSet<>(adjList.get(n));
			neighborsOfN.retainAll(neighbors);
			for (int m : neighborsOfN){
				//egoGraph.addEdge(n, m);
				egoGraph.addEdge(m, n);
			}
		}
		return egoGraph;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 * Works best on directed graph
	 * It returns a list of strongly connected subgraphs,
	 * where is a path between all pairs of u & v nodes
	 * with both direction.
	 */
	@Override
	public List<Graph> getSCCs() {
		List<Graph> SCCs = new ArrayList<>();
		//DFS
		List<Integer> vertices = new LinkedList<Integer>(adjList.keySet());
		//Collections.sort(vertices);
		List<Integer> finished = dfs(adjList, vertices);
		//Transpose
		HashMap<Integer, HashSet<Integer>> transposed = this.transpose();
		//DFS on the transposed graph
		dfs(transposed, finished, SCCs);
		for (Graph g : SCCs) ((CapGraph)g).printGraph();
		return SCCs;
	}
	
	/**
	 * 
	 * @param adjList
	 * @param vertices
	 * @return
	 */
	private List<Integer> dfs(HashMap<Integer, HashSet<Integer>> adjList, List<Integer> vertices){
		List<Integer> finished = new LinkedList<Integer>();
		Set<Integer> visited = new HashSet<Integer>();
		
		while (!vertices.isEmpty()){
			int v = vertices.remove(0);
			//System.out.println("v removed from vertices: " + v);
			if (!visited.contains(v)){
				dfsVisit(adjList, v, visited, finished);
			}
		}
		return finished;
	}
	
	private List<Integer> dfs(HashMap<Integer, HashSet<Integer>> adjList, List<Integer> vertices, List<Graph> SCCs){
		List<Integer> finished = new LinkedList<Integer>();
		Set<Integer> visited = new HashSet<Integer>();
		
		while (!vertices.isEmpty()){
			int v = vertices.remove(0);
			//System.out.println("v removed from vertices: " + v);
			CapGraph scc = new CapGraph();
			if (!visited.contains(v)){
				dfsVisit(adjList, v, visited, finished);
				int i = finished.remove(0);
				scc.addVertex(i);
				while(!finished.isEmpty()){
					i = finished.remove(0);
					scc.addEdge(v, i);
				}
				SCCs.add(scc);
			}
		}
		return finished;
	}
	
	private void dfsVisit(HashMap<Integer, HashSet<Integer>> adjList, int v, Set<Integer> visited, List<Integer> finished){
		visited.add(v);
		//System.out.println("added to visited " + v);
		for (int n : adjList.get(v)){
			if (!visited.contains(n))
				dfsVisit(adjList, n, visited, finished);
		}
		//System.out.println("pushing: " + v);
		((LinkedList<Integer>) finished).push(v);
	}
	
	
	
	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		return adjList;
	}
	
	public void printGraph(){
		for (int key : adjList.keySet()){
			System.out.print(key + " -> ");
			for (int value : adjList.get(key)){
				System.out.print(value + " ");
			}
			System.out.println();
		}
	}
	
	private boolean isNeighbors(int node1, int node2){
		return adjList.get(node1).contains(node2);
	}
	
	private HashMap<Integer, HashSet<Integer>> transpose(){
		HashMap<Integer, HashSet<Integer>> transposed = new HashMap<>();
		for (int i : adjList.keySet()){
			for (int j : adjList.get(i)){
				if (!transposed.containsKey(i)){
					transposed.put(i, new HashSet<Integer>());
				}
				
				if (transposed.containsKey(j)){
					transposed.get(j).add(i);
				}
				else if (!transposed.containsKey(j)){
					HashSet<Integer> set = new HashSet<Integer>();
					set.add(i);
					transposed.put(j, set);
				}
			}
		}
		/*System.out.println("Tranposing the graph...");
		for (int key : transposed.keySet()){
			System.out.print(key + " -> ");
			for (int value : transposed.get(key)){
				System.out.print(value + " ");
			}
			System.out.println();
		}*/
		return transposed;
	}
	
	private Set<Integer> getVertices(){
		return adjList.keySet();
	}
	
	public static void main(String... strings){
		Graph g = new CapGraph();
		GraphLoader.loadGraph(g, "data/smallest_data.txt");
		
		((CapGraph)g).printGraph();
		System.out.println();
		((CapGraph)g.getEgonet(23)).printGraph();;
	}
}
