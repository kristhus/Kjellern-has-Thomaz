package graphics;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class LeftPanel extends JInternalFrame {

	private ListItemGroup group;
	private GridLayout gl;
	
	public LeftPanel() {
		
		super();
		gl = new GridLayout();
		group = new ListItemGroup();
		add(group);
		
		group.addC(new ListItem("", "GifLoader", "gif", false, this));
		group.addC(new ListItem("", "Particles", "particles", false, this));
		group.addC(new ListItem("", "Click & Drag", "Drag&Drop", false, this));
		group.addC(new ListItem("", "Movable", "move", false, this));
		group.addC(new ListItem("", "Gradient", "gradient", false, this));
		group.addC(new ListItem("", "Shape Creator", "create", false, this), gl);
		
		setBounds(0, 0, 200, 700);
		setVisible(true);
		setClosable(false);
		
		show();
	}
	
	
}
