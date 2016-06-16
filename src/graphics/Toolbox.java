package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.AbstractButton;
import javax.swing.border.EmptyBorder;

import constants.EnvironmentConstants;

public class Toolbox extends JPanel implements ActionListener {

	private JToggleButton directionalOutput;
	private JToggleButton sprayCan;
	private JButton physicsSettings;

	private JCheckBox hide;
	
	private JPanel mainPanel;
	
	private ArrayList<JTextField> fields;
	
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
			createSettingsFrame();
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
	
	public void createSettingsFrame() {
		JFrame frame = new JFrame();
		fields = new ArrayList<JTextField>();
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setBorder(new EmptyBorder(20, 20, 20, 20));
		frame.add(panel);
		GridBagLayout gbl = new GridBagLayout();
		panel.setLayout(gbl);
		GridBagConstraints c = new GridBagConstraints();
		
		JTextField gravity = new JTextField(EnvironmentConstants.GRAVITY + "");
		gravity.setName("gravity");
		JLabel gravityLabel = new JLabel("Gravity");
		JTextField termVelocity = new JTextField(EnvironmentConstants.TERMINAL_VELOCITY + "");
		termVelocity.setName("tvel");
		JLabel termVelocityLabel = new JLabel("Terminal Velocity");
		JTextField airDensity = new JTextField(EnvironmentConstants.AIR_DENSITY + "");
		airDensity.setName("airDensity");
		JLabel airDensityLabel = new JLabel("Air density");
		JTextField gravConstant = new JTextField(EnvironmentConstants.GRAVITATIONAL_CONSTANT + "");
		gravConstant.setName("gravC");
		JLabel gravConstantLabel = new JLabel("Gravitational constant");
		
		fields.add(gravity);
		fields.add(termVelocity);
		fields.add(airDensity);
		fields.add(gravConstant);
		
		JButton updateValues = new JButton("Update");
		updateValues.addActionListener(new ButtonListener());
		
		c.gridx = 1;
		c.gridy = 0;
		panel.add(updateValues, c);
		c.gridy++;
		c.gridx = 0;
		panel.add(gravityLabel, c);
		c.gridx++;
		panel.add(gravity,c);
		c.gridx = 0;
		c.gridy++;
		panel.add(termVelocityLabel, c);
		c.gridx++;
		panel.add(termVelocity, c);
		c.gridx = 0;
		c.gridy++;
		panel.add(airDensityLabel, c);
		c.gridx++;
		panel.add(airDensity, c);
		c.gridx = 0;
		c.gridy++;
		panel.add(gravConstantLabel, c);
		c.gridx++;
		panel.add(gravConstant, c);
		
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
		frame.revalidate();
		frame.repaint();
	}
	
	public class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			for(JTextField f : fields) {
				switch(f.getName()) {
				case "gravity":
					EnvironmentConstants.GRAVITY = Double.parseDouble(f.getText());
					break;
				case "tvel":
					EnvironmentConstants.TERMINAL_VELOCITY = Double.parseDouble(f.getText());
					break;
				case "AirDensity":
					EnvironmentConstants.AIR_DENSITY = Double.parseDouble(f.getText());
					break;
				case "gravConstant":
					EnvironmentConstants.GRAVITATIONAL_CONSTANT = Double.parseDouble(f.getText());
					break;
				}
			}
		}
		
	}
	
}
