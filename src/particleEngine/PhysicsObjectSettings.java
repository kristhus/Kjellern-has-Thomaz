package particleEngine;

import graphics.ParticleCanvas;
import graphics.ParticleCanvas.DirectionalSprays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
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

public class PhysicsObjectSettings extends JFrame {

	private int screenWidth = 520;
	private int screenHeight = 500;
	private PhysicsObject obj;
	private ColorChooser cc;
	private SettingsPanel sp;
	
	private ArrayList<JTextField> fields = new ArrayList<JTextField>();
	
	
	public PhysicsObjectSettings(String headerStr, PhysicsObject obj) {
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
			
			JLabel bouncinessLabel = new JLabel("Bounce");
			JTextField bouncinessField = new JTextField(obj.getBounciness() + "");
			bouncinessField.setName("bounciness");
			fields.add(bouncinessField);
			add(bouncinessLabel);
			add(bouncinessField);
			
			DirectionalSettings ds = new DirectionalSettings();
			if(obj instanceof DirectionalSprays) {
				add(ds);
			}
			
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
			// Bounciness Label
			spl.putConstraint(SpringLayout.NORTH, bouncinessLabel, 20, SpringLayout.SOUTH, heightLabel);
			spl.putConstraint(SpringLayout.WEST, bouncinessLabel, 10, SpringLayout.EAST, cc);
			// Bounciness field
			spl.putConstraint(SpringLayout.BASELINE, bouncinessField, 0, SpringLayout.BASELINE, bouncinessLabel);
			spl.putConstraint(SpringLayout.HORIZONTAL_CENTER, bouncinessField, 0, SpringLayout.HORIZONTAL_CENTER, heightField);
			
			
			// Update Button
			spl.putConstraint(SpringLayout.WEST, updateFields, 10, SpringLayout.WEST, this);
			spl.putConstraint(SpringLayout.SOUTH, updateFields, -10, SpringLayout.SOUTH, this);
			
			if(obj instanceof DirectionalSprays) {
				spl.putConstraint(SpringLayout.NORTH, ds, 10, SpringLayout.SOUTH, cc);
				spl.putConstraint(SpringLayout.WEST, ds, 10, SpringLayout.WEST, this);
				ds.revalidate();
			}
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
				case "pGravity":
					((DirectionalSprays) obj).getSpraynpray().setGravity(box.isSelected());
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
							break;
						case "height":
							obj.setHeight(Double.parseDouble(field.getText()));
							break;
						case "bounciness":
							obj.setBounciness(Double.parseDouble(field.getText()));
							break;
						case "spray":
							((DirectionalSprays) obj).setSpray(Double.parseDouble(field.getText()));
							break;
						case "pps":
							((DirectionalSprays) obj).getSpraynpray().setParticlesPerSecond(Integer.parseInt(field.getText()));
							break;
						case "limit":
							((DirectionalSprays) obj).getSpraynpray().setParticleLimit(Integer.parseInt(field.getText()));
							
						}
					}
				}
			}
		}
		
	}
	
	public class DirectionalSettings extends JPanel {
		
		public DirectionalSettings(){
			if(!(obj instanceof DirectionalSprays)) {return; }
			
			DirectionalSprays tmp = (DirectionalSprays) obj;
			
			SpringLayout spl = new SpringLayout();
			setLayout(spl);
			
			JLabel subHeader = new JLabel("Particle Settings");
			subHeader.setFont(new Font("arial", Font.BOLD, 11));
			add(subHeader);
			
			JCheckBox gravityBox = new JCheckBox();
			gravityBox.setActionCommand("pGravity");
			gravityBox.setSelected(tmp.getSpraynpray().isGravity());
			gravityBox.addActionListener(new ActionListenerSettings());
			JLabel gravityLabel = new JLabel("Gravity");
			add(gravityBox);
			add(gravityLabel);
			
			JLabel angleLabel = new JLabel("Angle spray");
			JTextField angleField = new JTextField(tmp.getSpray() + "");
			angleField.setToolTipText("The amount of spray, given in degrees");
			angleField.setName("spray");
			fields.add(angleField);
			add(angleLabel);
			add(angleField);
			
			JLabel ppsLabel = new JLabel("P/s");
			JTextField ppsField = new JTextField(tmp.getSpraynpray().getParticlesPerSecond() + "");
			ppsField.setToolTipText("The amount of particles per second");
			ppsField.setName("pps");
			fields.add(ppsField);
			add(ppsLabel);
			add(ppsField);
			
			JLabel limitLabel = new JLabel("Limit");
			JTextField limitField = new JTextField(tmp.getSpraynpray().getParticleLimit() + "");
			limitField.setToolTipText("The amount of particles allowed in the cluster");
			limitField.setName("limit");
			fields.add(limitField);
			add(limitLabel);
			add(limitField);
			
			
			// Layout constraints
			
			//Header Label
			spl.putConstraint(SpringLayout.NORTH, subHeader, 5, SpringLayout.NORTH, this);
			spl.putConstraint(SpringLayout.WEST, subHeader, 10, SpringLayout.WEST, this);
			// Angle Label
			spl.putConstraint(SpringLayout.NORTH, angleLabel, 10, SpringLayout.SOUTH, subHeader);
			spl.putConstraint(SpringLayout.WEST, angleLabel, 10, SpringLayout.WEST, this);
			// Angle field
			spl.putConstraint(SpringLayout.BASELINE, angleField, 0, SpringLayout.BASELINE, angleLabel);
			spl.putConstraint(SpringLayout.WEST, angleField, 10, SpringLayout.EAST, angleLabel);
			// PPS Label
			spl.putConstraint(SpringLayout.NORTH, ppsLabel , 10, SpringLayout.SOUTH, angleLabel);
			spl.putConstraint(SpringLayout.WEST, ppsLabel, 10, SpringLayout.WEST, this);
			// PPS field
			spl.putConstraint(SpringLayout.VERTICAL_CENTER, ppsField, 0, SpringLayout.VERTICAL_CENTER, ppsLabel);
			spl.putConstraint(SpringLayout.HORIZONTAL_CENTER, ppsField, 0, SpringLayout.HORIZONTAL_CENTER, angleField);	
			// Limit Label
			spl.putConstraint(SpringLayout.NORTH, limitLabel , 10, SpringLayout.SOUTH, ppsLabel);
			spl.putConstraint(SpringLayout.WEST, limitLabel, 10, SpringLayout.WEST, this);
			// Limit field
			spl.putConstraint(SpringLayout.VERTICAL_CENTER, limitField, 0, SpringLayout.VERTICAL_CENTER, limitLabel);
			spl.putConstraint(SpringLayout.HORIZONTAL_CENTER, limitField, 0, SpringLayout.HORIZONTAL_CENTER, ppsField);	
			
			// Gravity Label
			spl.putConstraint(SpringLayout.NORTH, gravityLabel , 10, SpringLayout.SOUTH, limitLabel);
			spl.putConstraint(SpringLayout.WEST, gravityLabel, 10, SpringLayout.WEST, this);
			// Gravity field
			spl.putConstraint(SpringLayout.VERTICAL_CENTER, gravityBox, 0, SpringLayout.VERTICAL_CENTER, gravityLabel);
			spl.putConstraint(SpringLayout.HORIZONTAL_CENTER, gravityBox, 0, SpringLayout.HORIZONTAL_CENTER, limitField);			
			
			
			
			
			
			setVisible(true);
			setBackground(Color.white);
			setBorder(BorderFactory.createLineBorder(new Color(0,0,0,50)));
			setPreferredSize(new Dimension(150,200));
		}
		
	}
	
}
