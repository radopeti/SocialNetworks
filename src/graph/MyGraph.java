package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import util.GraphLoader;

/**
 * MyGraph implements the Graph interface
 * The class is designed to implement the Girvan-Newman algorihm, to
 * find communities in a social network.
 * It use an adjacency list, to represent the graph with <Integer, Node> pairs.
 * @author Peter Rado
 *
 */

public class MyGraph implements Graph {
	
	//adjacency list
	private Map<Integer, Node> adjList;
	
	/**
	 * Constructor
	 */
	public MyGraph(){
		adjList = new HashMap<>();
	}
	
	/**
	 * Add a vertex to the graph
	 */
	@Override
	public void addVertex(int num) {
		// TODO Auto-generated method stub
		if (!adjList.containsKey(num)) {
			Node node = new Node(num);
			adjList.put(num, node);
		}
	}

	/**
	 * add an edge to the graph
	 */
	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub
		if (adjList.containsKey(from) && adjList.containsKey(to)){
			Node start = adjList.get(from);
			Node end = adjList.get(to);
			Edge edge = new Edge(start, end);
			adjList.get(from).addEdge(edge);
		}
	}

	/**
	 * Return a Graph which represent a person's egonet:
	 * the person's connections and the relationship between them
	 */
	@Override
	public Graph getEgonet(int center) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Return a list a Strongly Connected Components:
	 * subgraphs, where every u, v vertex has a path with
	 * both directions.
	 */
	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//TODO: implement this one, no matter what
	public Map<Integer, List<Integer>> findAllShortestPath(int start, int end){
		Comparator<Node> comparator = new Comparator<Node>() {

			@Override
			public int compare(Node node1, Node node2) {
				if (node1.getDistance() > node2.getDistance()) return 1;
				else if (node1.getDistance() < node2.getDistance()) return -1;
				else return 0;
			}
		};
		
		HashSet<Node> visited = new HashSet<>();
		PriorityQueue<Node> queue = new PriorityQueue<>(comparator);
		HashMap<Integer, List<Integer>> paths = new HashMap<>();
		initDistances();
		Node startNode = adjList.get(start);
		Node endNode = adjList.get(end);
		startNode.setDistance(0);
		System.out.println(startNode);
		queue.add(startNode);
		boolean found = false;
		
		
		while(!queue.isEmpty()){
			Node curr = queue.remove();
			System.out.println("dequeue: " + curr);
			if (!visited.contains(curr)){
				visited.add(curr);
				List<Edge> edges = adjList.get(curr.getValue()).getEdges();
				for (Edge edge : edges){
					Node n = edge.getEnd();
					double distance = curr.getDistance() + 1;
					if (distance <= n.getDistance()){
						n.setDistance(distance);
						queue.add(n);
						System.out.println("set dist: " + n);
						if (!paths.containsKey(curr.getValue())){
							//System.out.println("create a new list and add: " + n.getValue());
							List<Integer> list = new ArrayList<>();
							list.add(n.getValue());
							paths.put(curr.getValue(), list);
						}else{
							paths.get(curr.getValue()).add(n.getValue());
							//System.out.println("put val: " + n.getValue());
						}
					}
				}
			}
		}
		
		for (Map.Entry<Integer, List<Integer>> entry : paths.entrySet()){
			String output = "";
			output += entry.getKey() + "->";
			for (int n : entry.getValue()){
				output += n + ", ";
			}
			System.out.println(output);
		}
		
		if (found){
			return paths;
		}else{
			return new HashMap<Integer, List<Integer>>();
		}
	}
	
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Breadth first search algorithm, find the shortest path
	 * between two vertices
	 * @param start The start vertex
	 * @param end The end vertex
	 * @return the shortest path between start and end
	 */
	public List<Integer> bfs(int start, int end){
		HashSet<Node> visited = new HashSet<>();
		LinkedList<Node> queue = new LinkedList<>();
		HashMap<Node, Node> parentMap = new HashMap<>();
		Node startNode = adjList.get(start);
		Node endNode = adjList.get(end);
		visited.add(startNode);
		queue.add(startNode);
		boolean found = false;
		
		while(!queue.isEmpty()){
			Node curr = queue.remove();
			System.out.println("removing " + curr);
			if (curr.equals(endNode)) {
				System.out.println("found!");
				found = true;
				break;
			}
			
			for (Edge edge : curr.getEdges()){
				Node n = adjList.get(edge.getEnd().getValue());
				if (!visited.contains(n)){
					System.out.println("visiting " + n);
					visited.add(n);
					queue.addLast(n);
					parentMap.put(n, curr);
				}
			}
		}
		
		if (found){
			return buildPath(parentMap, endNode, startNode);
		}else{
			return new LinkedList<Integer>();
		}
		
	}
	
	public List<Integer> dijkstra(int start, int end){
		
		Comparator<Node> comparator = new Comparator<Node>() {

			@Override
			public int compare(Node node1, Node node2) {
				if (node1.getDistance() < node2.getDistance()) return 1;
				else if (node1.getDistance() > node2.getDistance()) return -1;
				else return 0;
			}
		};
		
		HashSet<Node> visited = new HashSet<>();
		PriorityQueue<Node> queue = new PriorityQueue<>(comparator);
		HashMap<Node, Node> parentMap = new HashMap<>();
		initDistances();
		Node startNode = adjList.get(start);
		Node endNode = adjList.get(end);
		startNode.setDistance(0);
		System.out.println(startNode);
		queue.add(startNode);
		boolean found = false;
		
		
		while(!queue.isEmpty()){
			Node curr = queue.remove();
			System.out.println("currs dist: " + curr.getDistance());
			if (curr.equals(endNode)) {
				found = true;
				break;
			}
			
			if (!visited.contains(curr)){
				visited.add(curr);
				List<Edge> edges = adjList.get(curr.getValue()).getEdges();
				for (Edge edge : edges){
					Node n = edge.getEnd();
					double distance = curr.getDistance() + 1;
					System.out.println(distance);
					if (distance <= n.getDistance()){
						n.setDistance(distance);
						queue.add(n);
						parentMap.put(n, curr);
					}
				}
			}
		}	
		
		if (found){
			return buildPath(parentMap, endNode, startNode);
		}else{
			return new LinkedList<Integer>();
		}
	}
	
	@Override
	public String toString() {
		String graphByText = "";
		for (Entry<Integer, Node> entry : adjList.entrySet()){
			graphByText += entry.getKey() + "-> ";
			for (Edge edge : entry.getValue().getEdges()){
				graphByText += edge.getEnd().getValue() + ", ";
			}
			graphByText += "\n";
		}
		return graphByText;
	}
	
	/**
	 * Helper method to reconstruct a path from the end to start. 
	 * @param parentMap The parent map which build by a search algorithm
	 * @param end end node
	 * @param start start node
	 * @return The path found by a search algorithm.
	 */
	private List<Integer> buildPath(HashMap<Node, Node> parentMap, Node end, Node start){
		LinkedList<Integer> path = new LinkedList<>();
		System.out.println(end);
		Node curr = end;
		while (!curr.equals(start)){
			path.addFirst(curr.getValue());
			curr = parentMap.get(curr);
		}
		path.addFirst(curr.getValue());
		return path;
	}
	
	private void initDistances(){
		for (int vertex : adjList.keySet()){
			adjList.get(vertex).setDistance(Double.POSITIVE_INFINITY);
		}
	}
	
	public static void main(String... strings){
		MyGraph g = new MyGraph();
		GraphLoader.loadGraph(g, "data/find_all_shortest_2.txt");
		System.out.println(g.toString());;
		g.findAllShortestPath(4, 1);
	}
	
}
