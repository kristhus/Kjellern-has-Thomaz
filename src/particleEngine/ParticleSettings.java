package particleEngine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import physics.PhysicsObject;

public class ParticleSettings extends JFrame {

	private int screenWidth = 520;
	private int screenHeight = 500;
	private PhysicsObject obj;
	private ColorChooser cc;
	private SettingsPanel sp;
	
	private ArrayList<JTextField> fields = new ArrayList<JTextField>();
	
	
	public ParticleSettings(String headerStr, PhysicsObject obj) {
		this.obj = obj;
		sp = new SettingsPanel(headerStr, screenWidth, screenHeight);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		add(sp);
		int x = Toolkit.getDefaultToolkit().getScreenSize().width-520;
		setBounds(x,0,520,500);
		setTitle("Object Settings");
	}
	
	public void update(double dt) {
		cc.update(dt);
		obj.setColor(cc.getColor());
	}
	

	
	
	
	private class SettingsPanel extends JPanel {
		
		public SettingsPanel(String headerStr, int width, int height) {
			super();
			cc = new ColorChooser();
			cc.setColour(obj.getColor());
			System.out.println(obj.getColor());
			SpringLayout spl = new SpringLayout();
			add(cc);
			setLayout(spl);
			setBackground(Color.white);
			
			JCheckBox gravityBox = new JCheckBox();
			gravityBox.setActionCommand("gravity");
			gravityBox.setSelected(obj.isGravity());
			gravityBox.addActionListener(new ActionListenerSettings());
			JLabel gravityLabel = new JLabel("Gravity");
			add(gravityBox);
			add(gravityLabel);
			
			JCheckBox staticBox = new JCheckBox();
			staticBox.setSelected(obj.isStatic());
			staticBox.setActionCommand("static");
			staticBox.addActionListener(new ActionListenerSettings());
			JLabel staticLabel = new JLabel("Static");
			add(staticBox);
			add(staticLabel);
			
			JLabel weightLabel = new JLabel("Weight");
			JTextField weightField = new JTextField();
			weightField.setText(obj.getWeight() + "");
			weightField.setName("weight");
			fields.add(weightField);
			JButton updateFields = new JButton("Update Fields");
			updateFields.setActionCommand("update");
			updateFields.addActionListener(new ActionListenerSettings());
			add(weightLabel);
			add(weightField);
			add(updateFields);
			
			JTextField widthField = new JTextField(obj.getWidth() + "");
			widthField.setName("width");
			fields.add(widthField);
			JLabel widthLabel = new JLabel("Width");
			add(widthLabel);
			add(widthField);
			
			JTextField heightField = new JTextField(obj.getHeight() + "");
			heightField.setName("height");
			fields.add(heightField);
			JLabel heightLabel = new JLabel("Height");
			add(heightLabel);
			add(heightField);
			
			
			//Gravity label
			spl.putConstraint(SpringLayout.NORTH, gravityLabel, 10, SpringLayout.NORTH, this);
			spl.putConstraint(SpringLayout.WEST, gravityLabel, 10, SpringLayout.EAST, cc);
			// Gravity checkbox
			spl.putConstraint(SpringLayout.NORTH, gravityBox, 10, SpringLayout.NORTH, this);
			spl.putConstraint(SpringLayout.WEST, gravityBox, 10, SpringLayout.EAST, gravityLabel);
			// Static Label
			spl.putConstraint(SpringLayout.NORTH, staticLabel, 10, SpringLayout.SOUTH, gravityLabel);
			spl.putConstraint(SpringLayout.WEST, staticLabel, 10, SpringLayout.EAST, cc);
			// Static checkbox
			spl.putConstraint(SpringLayout.NORTH, staticBox, 10, SpringLayout.SOUTH, gravityBox);
			spl.putConstraint(SpringLayout.WEST, staticBox, 10, SpringLayout.EAST, staticLabel);
			// Width Label
			spl.putConstraint(SpringLayout.NORTH, weightLabel, 20, SpringLayout.SOUTH, staticLabel);
			spl.putConstraint(SpringLayout.WEST, weightLabel, 10, SpringLayout.EAST, cc);
			// Weight field
			spl.putConstraint(SpringLayout.BASELINE, weightField, 0, SpringLayout.BASELINE, weightLabel);
			spl.putConstraint(SpringLayout.WEST, weightField, 10, SpringLayout.EAST, weightLabel);
			// Width Label
			spl.putConstraint(SpringLayout.NORTH, widthLabel, 20, SpringLayout.SOUTH, weightLabel);
			spl.putConstraint(SpringLayout.WEST, widthLabel, 10, SpringLayout.EAST, cc);
			// Weight field
			spl.putConstraint(SpringLayout.BASELINE, widthField, 0, SpringLayout.BASELINE, widthLabel);
			spl.putConstraint(SpringLayout.HORIZONTAL_CENTER, widthField, 0, SpringLayout.HORIZONTAL_CENTER, weightField);
			// Height Label
			spl.putConstraint(SpringLayout.NORTH, heightLabel, 20, SpringLayout.SOUTH, widthLabel);
			spl.putConstraint(SpringLayout.WEST, heightLabel, 10, SpringLayout.EAST, cc);
			// Height field
			spl.putConstraint(SpringLayout.BASELINE, heightField, 0, SpringLayout.BASELINE, heightLabel);
			spl.putConstraint(SpringLayout.HORIZONTAL_CENTER, heightField, 0, SpringLayout.HORIZONTAL_CENTER, widthField);
			
			
			// Update Button
			spl.putConstraint(SpringLayout.EAST, updateFields, 10, SpringLayout.EAST, this);
			spl.putConstraint(SpringLayout.SOUTH, updateFields, 10, SpringLayout.SOUTH, this);
			
		}
		
	}
	
	private class ActionListenerSettings implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (arg0.getSource() instanceof JCheckBox) {
				JCheckBox box = (JCheckBox) arg0.getSource();
				switch(box.getActionCommand()) {
				case "gravity":
					obj.setGravity(box.isSelected());
					break;
				case "static":
					obj.setStatic(box.isSelected());
					obj.setVelocityX(0); // Not so sure about these ones
					obj.setVelocityY(0); //
					break;
				}
			}
			else if(arg0.getSource() instanceof JButton){
				if(((JButton)arg0.getSource()).getActionCommand() == "update") {
					for(JTextField field : fields) {
						switch(field.getName()) {
						case "weight":
							obj.setWeight(Double.parseDouble(field.getText()));
							break;
						case "width":
							obj.setWidth(Double.parseDouble(field.getText()));
						case "height":
							obj.setHeight(Double.parseDouble(field.getText()));
						}
					}
				}
			}
		}
		
	}
	
}
