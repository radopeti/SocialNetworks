package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gui.models.OperationsModel;

public class ButtonPanel extends JPanel{
	
	
	private JButton showBtn;
	private JButton clearBtn;
	private JScrollPane options;
	private ButtonPanelListener btnPanelListener;
	
	public ButtonPanel(){
		showBtn = new JButton("Show");
		clearBtn = new JButton("Clear");
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		this.setBorder(BorderFactory.createTitledBorder("Menu"));
		this.setMinimumSize(new Dimension(250, 0));
		this.setPreferredSize(new Dimension(250, 0));
		
		JList<String> list = new JList<>(new OperationsModel());	
		list.setLayoutOrientation(JList.VERTICAL);
		options = new JScrollPane(list);
		
		options.setMinimumSize(new Dimension(200, 80));
		options.setPreferredSize(new Dimension(200, 80));
		options.setBorder(BorderFactory.createTitledBorder("Options"));
		
		layout.setHorizontalGroup(
				   layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				      .addGroup(layout.createSequentialGroup()
				    		  .addComponent(options))
				      .addGroup(layout.createSequentialGroup()
				    		  .addComponent(clearBtn)
				    		  .addComponent(showBtn))
				);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(options))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(clearBtn)
							.addComponent(showBtn))
				);
		
		showBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selected = list.getSelectedValue();
				if (btnPanelListener != null && selected != null){
					if (selected.equals("EgoNet")){
						System.out.println("showing egonet");
						btnPanelListener.showEgoNet();
					}else if (selected.equals("SCC")){
						System.out.println("showing scc-s");
						btnPanelListener.showSCCs();
					}
				}
				
			}
		});
		
		clearBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (btnPanelListener != null){
					btnPanelListener.clear();
				}
			}
		});
		
		
	}

	public ButtonPanelListener getBtButtonPanelListener() {
		return btnPanelListener;
	}

	public void setBtButtonPanelListener(ButtonPanelListener btnPanelListener) {
		this.btnPanelListener = btnPanelListener;
	}
}
