package graphics;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class LeftPanel extends JPanel {

	
	public LeftPanel() {
		super();
		setBackground(Color.white);
		
		GridBagLayout gbl = new GridBagLayout(); 
		setLayout(gbl);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.NORTHWEST;
		add(new ListItem(null, "GifLoader", "gif"), c);
		c.gridy ++;
		add(new ListItem(null, "SkarszTest2", ""), c);
		c.gridy ++;
		add(new ListItem(null, "SkarszTest3", ""), c);
				c.gridy ++;
		add(new ListItem(null, "SkarszTest4", ""), c);
				c.gridy ++;
		add(new ListItem(null, "SkarszTest5", ""), c);
				c.gridy ++;
		add(new ListItem(null, "SkarszTest6", ""), c);
	}
	
	
}
