package jung;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.GraphMouseListener;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import graph.CapGraph;
import util.GraphLoader;

public class JungTest{
	
	static graph.Graph cg;
	static Graph<Node<Integer>, Edge<Integer>> g;
	static ArrayList<Node<Integer>> markedVertices = new ArrayList<>();
	static Node<Integer> selected;
	
	public static void main(String...strings){
		cg = new CapGraph();
		GraphLoader.loadGraph(cg, "data/facebook_1000.txt");
		g = new SparseMultigraph<>();
		GraphLoader.loadGraph(g, "data/facebook_1000.txt");
		
		Panel p = new Panel();
		System.out.println(g.toString());
		 // The Layout<V, E> is parameterized by the vertex and edge types
		 Layout<Node<Integer>, Edge<Integer>> layout = new ISOMLayout<Node<Integer>, Edge<Integer>>(g);
		 layout.setSize(new Dimension(1800,1200)); // sets the initial size of the space
		 
		 // The BasicVisualizationServer<V,E> is parameterized by the edge types
		 VisualizationViewer<Node<Integer>, Edge<Integer>> vv = new VisualizationViewer<Node<Integer>, Edge<Integer>>(layout);
		 vv.setPreferredSize(new Dimension(800,600)); //Sets the viewing area size
		
		 vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());
		 
		 	Transformer<Node<Integer>,Paint> vertexColor = new Transformer<Node<Integer>,Paint>() {

				@Override
				public Paint transform(Node<Integer> node) {
					if (node.isMarked()) return Color.ORANGE;
					return Color.BLUE;
				}
	        };
	        
	        Transformer<Edge<Integer>, Paint> edgeColor = new Transformer<Edge<Integer>, Paint>() {
	        	
				public Paint transform(Edge<Integer> edge) {
					if (edge.isMarked()) return Color.RED;
					return Color.BLACK;
				}
			};
			vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);
		    vv.getRenderContext().setEdgeDrawPaintTransformer(edgeColor);
	       
		    vv.addGraphMouseListener(new GraphMouseListener<Node<Integer>>() {
		    	
				@Override
				public void graphReleased(Node<Integer> node, MouseEvent me) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void graphPressed(Node<Integer> node, MouseEvent me) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void graphClicked(Node<Integer> node, MouseEvent me) {
					// TODO Auto-generated method stub
					if (selected == null){
						selected = node;
						node.setMarked(!node.isMarked());
						clearMarkedVertices();
					}else{
						selected.setMarked(false);
						selected = null;
						selected = node;
						
						node.setMarked(!node.isMarked());
					}
					vv.repaint();
				}
			});
			
		    
	    
	     p.setListener(new AlgorithmListener() {
			
			@Override
			public void showEgoNet() {
				getEgoNet();
			}

			@Override
			public void clear() {
				clearMarkedVertices();
				vv.repaint();
			}
		});
		 
	    
		 DefaultModalGraphMouse<Object, Object> gm = new DefaultModalGraphMouse<Object, Object>();
		 gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
		 vv.setGraphMouse(gm);
		 JFrame frame = new JFrame("Simple Graph View");
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setSize(1200, 800);
		 frame.add(vv, BorderLayout.CENTER);
		 frame.add(p, BorderLayout.EAST);
		 frame.pack();
		 frame.setVisible(true);
		 
		 
	}
	
	public static void getEgoNet(){
		//clearMarkedVertices();
		if (selected != null){
			   HashMap<Integer, HashSet<Integer>> egoNet =  cg.getEgonet(selected.getValue()).exportGraph();
		       
			   markedVertices.clear();
		       for (Map.Entry<Integer, HashSet<Integer>> entry : egoNet.entrySet()){
		    	   int v = entry.getKey();
		    	   
		    	   Node<Integer> n1 = new Node<>(v);
	    		   markedVertices.add(n1);
		    	   for (int e : entry.getValue()){
		    		   Node<Integer> n2 = new Node<>(e);
		    		   Edge<Integer> edge = g.findEdge(n1, n2);
		    		   edge.setMarked(true);
		    		   markedVertices.add(n2);
		    	   }
		       }
		       
		       
		       ArrayList<Node<Integer>> vertices = new ArrayList<>(g.getVertices());
		       vertices.retainAll(markedVertices);
		       for (Node<Integer> node : vertices){
		    	   System.out.println("marking " +node);
		    	   node.setMarked(true);
		       }
		}
		
	}
	
	public static void clearMarkedVertices(){
		ArrayList<Node<Integer>> vert = new ArrayList<>(g.getVertices());
		for (Node<Integer> node : vert){
			System.out.println(node);
	    	   node.setMarked(false);
	    }
		ArrayList<Edge<Integer>> edges = new ArrayList<>(g.getEdges());
		for (Edge<Integer> edge : edges){
			edge.setMarked(false);
		}
	}
	
}
