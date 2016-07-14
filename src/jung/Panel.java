package jung;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Panel extends JPanel{
	
	private JButton egoButton;
	private JButton SCCButton;
	private JButton clearButton;
	private AlgorithmListener listener;
	
	public Panel(){
		this.setLayout(new GridBagLayout());
		this.setSize(new Dimension(200, 100));
		egoButton = new JButton("EgoNet");
		SCCButton = new JButton("SCCs");
		clearButton = new JButton("Clear");
		egoButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.showEgoNet();
			}
			
		});
		
		clearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				listener.clear();
			}
		});
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weighty = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		this.add(egoButton, gc);
		gc.gridy++;
		gc.weighty = 10;
		this.add(SCCButton, gc);
		gc.gridy++;
		gc.weighty = 10;
		this.add(clearButton, gc);
	}
	
	public void setListener(AlgorithmListener listener){
		this.listener = listener;
	}
}
