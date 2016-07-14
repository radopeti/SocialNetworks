package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.GraphMouseListener;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import jung.Edge;
import jung.GuiGraph;
import jung.Node;

public class GraphPanel extends JPanel implements ButtonPanelListener{
	
	
	private GuiGraph guiGraph;
	private Layout<Node<Integer>, Edge<Integer>> layout;
	private VisualizationViewer<Node<Integer>, Edge<Integer>> vv;
	
	
	public GraphPanel(){
		this.setLayout(new BorderLayout());
		guiGraph = new GuiGraph();
		layout = new ISOMLayout<>(guiGraph);
		vv = new VisualizationViewer<>(layout);
		
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());
		vv.getRenderContext().setVertexFontTransformer(new Transformer<Node<Integer>, Font>() {
			
			@Override
			public Font transform(Node<Integer> arg0) {
				Font font = new Font(Font.SERIF, Font.BOLD, 10);
				return font;
			}
		});
		
		Transformer<Node<Integer>, Paint> vertexColor = new Transformer<Node<Integer>, Paint>() {

			@Override
			public Color transform(Node<Integer> node) {
				if (node.isMarked()){
					if (node.equals(guiGraph.getSelected())) return Color.GREEN;
					else return Color.YELLOW;
				}
				return Color.CYAN;
			}
		};
		
		Transformer<Edge<Integer>, Paint> edgeColor = new Transformer<Edge<Integer>, Paint>(){

			@Override
			public Paint transform(Edge<Integer> edge) {
				if (edge.isMarked()) return Color.RED;
				return Color.BLACK;
			}
			
		};
		
		vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);
		vv.getRenderContext().setEdgeDrawPaintTransformer(edgeColor);
		vv.getRenderContext().setEdgeShapeTransformer(
			    new EdgeShape.Line<Node<Integer>, Edge<Integer>>());
		vv.addGraphMouseListener(new GraphMouseListener<Node<Integer>>() {
			
			@Override
			public void graphReleased(Node<Integer> arg0, MouseEvent arg1) {
				
			}
			
			@Override
			public void graphPressed(Node<Integer> arg0, MouseEvent arg1) {
			}
			
			@Override
			public void graphClicked(Node<Integer> node, MouseEvent me) {
				if (guiGraph.getSelected() == null){
					guiGraph.setSelected(node);
					node.setMarked(true);
				}else{
					if (!node.equals(guiGraph.getSelected())){
						guiGraph.getSelected().setMarked(false);
						guiGraph.setSelected(null);
						guiGraph.setSelected(node);
						node.setMarked(true);
						System.out.println(node.toString());
					}
				}
				vv.repaint();
			}
		});
		
		DefaultModalGraphMouse<Object, Object> gm = new DefaultModalGraphMouse<Object, Object>();
		gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
		vv.setGraphMouse(gm);
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
		this.add(vv);
	}

	@Override
	public void showEgoNet() {
		guiGraph.getEgoNet();
		vv.repaint();
	}

	@Override
	public void showSCCs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		guiGraph.unmarkGraph();
		vv.repaint();
	}
	
	
}
