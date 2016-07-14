/**
 * @author UCSD MOOC development team
 * 
 * Utility class to add vertices and edges to a graph
 *
 */
package util;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import jung.Edge;
import jung.Node;

public class GraphLoader {
    /**
     * Loads graph with data from a file.
     * The file should consist of lines with 2 integers each, corresponding
     * to a "from" vertex and a "to" vertex.
     */ 
    public static void loadGraph(graph.Graph g, String filename) {
        Set<Integer> seen = new HashSet<Integer>();
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // Iterate over the lines in the file, adding new
        // vertices as they are found and connecting them with edges.
        while (sc.hasNextInt()) {
            int v1 = sc.nextInt();
            int v2 = sc.nextInt();
            if (!seen.contains(v1)) {
                g.addVertex(v1);
                seen.add(v1);
            }
            if (!seen.contains(v2)) {
                g.addVertex(v2);
                seen.add(v2);
            }
            g.addEdge(v1, v2);
        }
        
        sc.close();
    }
    
    public static void loadGraph(Graph<Node<Integer>, Edge<Integer>> g, String filename) {
        Set<Integer> seen = new HashSet<Integer>();
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // Iterate over the lines in the file, adding new
        // vertices as they are found and connecting them with edges.
        int edgeCounter = 1;
        while (sc.hasNextInt()) {
            int v1 = sc.nextInt();
            int v2 = sc.nextInt();
            Node<Integer> n1 = new Node<>(v1);
            Node<Integer> n2 = new Node<>(v2);
            if (!seen.contains(v1)) {
                g.addVertex(n1);
                seen.add(v1);
            }
            if (!seen.contains(v2)) {
                g.addVertex(n2);
                seen.add(v2);
            }
            
            Edge<Integer> edge = new Edge<Integer>(n1, n2);
            Edge<Integer> reverse = new Edge<Integer>(n2, n1);
            
            if (g.containsEdge(reverse)){
            	g.removeEdge(reverse);
            	g.addEdge(edge, n1, n2, EdgeType.UNDIRECTED);
            }else{
            	g.addEdge(edge, n1, n2, EdgeType.DIRECTED);
            }
            
        }
        
        sc.close();
    }
    
    public static void loadGraph2(Graph<Node<Integer>, Edge<Integer>> g, String filename) {
        Set<Integer> seen = new HashSet<Integer>();
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // Iterate over the lines in the file, adding new
        // vertices as they are found and connecting them with edges.
        int edgeCounter = 1;
        while (sc.hasNextInt()) {
            int v1 = sc.nextInt();
            int v2 = sc.nextInt();
            Node<Integer> n1 = new Node<>(v1);
            Node<Integer> n2 = new Node<>(v2);
            if (!seen.contains(v1)) {
                g.addVertex(n1);
                seen.add(v1);
            }
            if (!seen.contains(v2)) {
                g.addVertex(n2);
                seen.add(v2);
            }
            
            Edge<Integer> edge = new Edge<Integer>(n1, n2);
            Edge<Integer> reverse = new Edge<Integer>(n2, n1);
            
            if (g.containsEdge(reverse)){
            	g.removeEdge(reverse);
            	g.addEdge(edge, n1, n2, EdgeType.UNDIRECTED);
            }else{
            	g.addEdge(edge, n1, n2, EdgeType.UNDIRECTED);
            }
            
        }
        
        sc.close();
    }
}
