package ParticleEngine;

import interfaces.Drawable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;



public class ColorChooser extends JPanel implements Drawable, ItemListener {

	private JSlider rSlider;
	private JSlider gSlider;
	private JSlider bSlider;
	
	private JCheckBox seedsOn;


	private boolean mouseDown;
	private boolean seedOn;
	
	public boolean isSeedOn() {
		return seedOn;
	}


	public ColorChooser() {
			
		rSlider = new JSlider();
		gSlider = new JSlider();
		bSlider = new JSlider();
		seedsOn = new JCheckBox();
		seedsOn.addItemListener(this);
		
		rSlider.setMaximum(255);
		gSlider.setMaximum(255);
		bSlider.setMaximum(255);
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1;
		c.gridy= 0;
		c.gridx = 0;
		
		add(rSlider, c);
		c.gridx++;
		add(seedsOn, c);
		c.gridx = 0;
		c.gridy++;
		add(gSlider, c);
		c.gridy++;
		add(bSlider, c);
		
//		repaint();
		

	}
	
	
	private class SliderListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
		}
		
	}
	
	public void update(long dt) {
	}


	@Override
	public void draw(Graphics g) {  
		Graphics2D g2d = (Graphics2D) getGraphics().create();
	     g2d.setColor(new Color(rSlider.getValue(), gSlider.getValue(), bSlider.getValue()));  
	     g2d.fillRect(200,40,50,50);  
	}


	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getStateChange() == ItemEvent.SELECTED) {
			seedOn = true;
		}
	}
	
	public Color getColor() {
		return new Color(rSlider.getValue(), gSlider.getValue(), bSlider.getValue());
	}

}
