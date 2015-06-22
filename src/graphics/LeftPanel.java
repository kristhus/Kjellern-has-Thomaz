package graphics;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class LeftPanel extends JInternalFrame {

	private JPanel canvas;
	
	public LeftPanel() {
		super();
		canvas = new JPanel();
		canvas.setBackground(Color.white);
		GridBagLayout gbl = new GridBagLayout(); 
		canvas.setLayout(gbl);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		canvas.add(new ListItem("", "GifLoader", "gif", false), c);
		c.gridy ++;
		canvas.add(new ListItem("", "Particles", "particles", false), c);
		c.gridy ++;
		c.gridy ++;
		canvas.add(new ListItem("", "Click & Drag", "Drag&Drop", false), c);
		c.gridy++;
		canvas.add(new ListItem("", "Movable", "move", false), c);
		c.gridy++;
		canvas.add(new ListItem("", "Shape Creator", "create", false), c);
		
		setBounds(0, 0, 200, 700);
		setVisible(true);
		setClosable(false);
		add(canvas);
		
	}
	
	
}
