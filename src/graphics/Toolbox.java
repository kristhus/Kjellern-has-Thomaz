package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.AbstractButton;

public class Toolbox extends JPanel implements ActionListener {

	private JToggleButton directionalOutput;
	private JToggleButton sprayCan;
	private JButton physicsSettings;

	
	private JPanel mainPanel;
	
	private boolean isDirOutput = false;
	
	public Toolbox() {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.white);
		//setPreferredSize(new Dimension(300, 100));
		GridBagLayout gbl = new GridBagLayout();
		mainPanel.setLayout(gbl);
		GridBagConstraints c = new GridBagConstraints();
		
		sprayCan = new JToggleButton("Particle spray");
		sprayCan.setToolTipText("Release particles on the canvas");
		sprayCan.setSelected(true);
		sprayCan.setActionCommand("spray");
		sprayCan.addActionListener(this);
		
		directionalOutput = new JToggleButton("Dir. Output");
		directionalOutput.setToolTipText("Outputs particals in chosen direction, with an output speed");
		directionalOutput.setActionCommand("dirOut");
		directionalOutput.addActionListener(this);
		
		physicsSettings = new JButton("Settings");
		physicsSettings.setToolTipText("Change settings regarding physics constants, such as gravitational force etc., to emulate different environments");
		physicsSettings.setActionCommand("settings");
		physicsSettings.addActionListener(this);
		
		mainPanel.add(sprayCan, c);
		c.gridx++;
		mainPanel.add(directionalOutput, c);
		c.gridx++;
		mainPanel.add(physicsSettings, c);
		
		add(mainPanel);
		setBackground(Color.white);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		switch ( ((AbstractButton) arg0.getSource()).getActionCommand()) {
		case "dirOut":
			if(directionalOutput.isSelected()) {
				isDirOutput = true;
				// Some var = true
				//Draw something on cursor. 
				// When phys screen is pressed, create a spray on mouse cursor in some dir
				// If click and drag on screen, create object with vector, implying direction of spray 
				// After click released within, untoggle button?
			}else
				isDirOutput = false;
			break;
		case "settings":
			
			break;
		}
	}

	public boolean dirOutputIsDown() {
		return isDirOutput;
	}
	
	public void setDirOutputIsSelected(boolean b) {
		isDirOutput = false;
		directionalOutput.setSelected(false);
	}
	public boolean isSprayCan() {
		return sprayCan.isSelected();
	}
	
}
