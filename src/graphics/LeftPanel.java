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
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.NORTHWEST;
		canvas.add(new ListItem(null, "GifLoader", "gif"), c);
		c.gridy ++;
		canvas.add(new ListItem(null, "Particles", "particles"), c);
		c.gridy ++;
		c.gridy ++;
		canvas.add(new ListItem(null, "Click & Drag", "Drag&Drop"), c);
		c.gridy++;
		canvas.add(new ListItem(null, "Movable", "move"), c);
		
		setBounds(0, 0, 150, 500);
		setVisible(true);
		setClosable(false);
		add(canvas);
		
	}
	
	
}
