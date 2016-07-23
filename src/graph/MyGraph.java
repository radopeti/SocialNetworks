package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import util.GraphLoader;

public class MyGraph implements Graph {
	
	private Map<Integer, Node> adjList;
	
	public MyGraph(){
		adjList = new HashMap<>();
	}
	
	@Override
	public void addVertex(int num) {
		// TODO Auto-generated method stub
		if (!adjList.containsKey(num)) {
			Node node = new Node(num);
			adjList.put(num, node);
		}
	}

	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub
		if (adjList.containsKey(from)){
			adjList.get(from).addEdge(to);
		}
	}

	@Override
	public Graph getEgonet(int center) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//TODO: implement this one, no matter what
	public Map<Integer, Integer> findAllShortestPath(){
		return null;
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

	public static void main(String... strings){
		MyGraph g = new MyGraph();
		GraphLoader.loadGraph(g, "data/smallest_data.txt");
		System.out.println(g.toString());;
		
		for (int num : g.bfs(18, 50)){
			System.out.println(num);
		}
	}
	
}
