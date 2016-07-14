package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainFrame extends JFrame{
	
	private GraphPanel graphPanel;
	private ButtonPanel buttonPanel;
	
	public MainFrame(String label){
		super(label);
		graphPanel = new GraphPanel();
		buttonPanel = new ButtonPanel();
		buttonPanel.setBtButtonPanelListener(graphPanel);
		this.setSize(1200, 800);
		this.setLayout(new BorderLayout());
		
		this.add(graphPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.EAST);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
