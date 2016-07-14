package gui.models;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class OperationsModel extends DefaultListModel<String> {
	
	public OperationsModel(){
		super();
		this.addElement("EgoNet");
		this.addElement("SCC");
	}
}
