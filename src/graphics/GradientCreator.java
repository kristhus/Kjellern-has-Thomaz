package graphics;

import interfaces.Drawable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import colours.ColourWheel;
import colours.Gradient;
import colours.GradientPoint;

public class GradientCreator extends JPanel implements Drawable, ItemListener, ChangeListener {

	private GradientPoint currentPoint;
	private Gradient gradient;
	
	private JSlider rSlider;
	private JSlider gSlider;
	private JSlider bSlider;
	
	private ColourWheel cWheel;
	
	private int centreY;
	
	public GradientCreator() {
		
		JPanel container = new JPanel();
		
		rSlider = new JSlider();
		gSlider = new JSlider();
		bSlider = new JSlider();
		
		rSlider.setMaximum(255);
		gSlider.setMaximum(255);
		bSlider.setMaximum(255);
		
		rSlider.addChangeListener(this);
		gSlider.addChangeListener(this);
		bSlider.addChangeListener(this);
		

		GridBagLayout gbl = new GridBagLayout();
		container.setLayout(gbl);
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1;
		c.gridy= 0;
		c.gridx = 0;
		
		container.add(rSlider, c);
		c.gridx++;
		c.gridx = 0;
		c.gridy++;
		container.add(gSlider, c);
		c.gridy++;
		container.add(bSlider, c);
		
		container.setPreferredSize(new Dimension(500, 100));
		container.setBackground(Color.white);
		container.setVisible(true);
		add(container);
		setBackground(Color.white);
		c.gridx++;
		c.gridy++;
		//cWheel = new ColourWheel(this);
		container.add(cWheel);
	}
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		cWheel.draw(g);
		paintComponents(g);
	}

	@Override
	public void update(double dt) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void paintComponents(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Color startColor = new Color(rSlider.getValue(), gSlider.getValue(),bSlider.getValue());
	    Color endColor = Color.blue;
	    
		
//		int startX = rSlider.getX()+ rSlider.getWidth() +5;
//		int startY = getHeight()/2-20;
//		int endX = startY+40;
//		int endY = startX+40;
		int startX = 10, startY = 20, endX = 30, endY = 40;
	    
	    GradientPaint gradient = new GradientPaint(startX, startY, startColor, endX, endY, endColor);
	    g2d.setPaint(gradient);

	    g2d.draw(new Rectangle(rSlider.getX()+ rSlider.getWidth() +5,getHeight()/2-20,40,40));
	    
	    g.fillRect(rSlider.getX()+ rSlider.getWidth() +5,getHeight()/2-20,40,40); 
	}

	public void colourWheel(int pointX, int pointY) {
		
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(rSlider)) {
			System.out.println("R: " + ((JSlider)e.getSource()).getValue());
		}
		else if(e.getSource().equals(gSlider)) {
			System.out.println("g: " + ((JSlider)e.getSource()).getValue());
		}
		else
			System.out.println("b: " + ((JSlider)e.getSource()).getValue());
	}

}
