package ParticleEngine;

import interfaces.Drawable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JSlider;



public class ColorChooser extends JPanel implements Drawable {

	private Rectangle colorRect;
	
	private JSlider rSlider;
	private JSlider gSlider;
	private JSlider bSlider;

	public ColorChooser() {
			
		rSlider = new JSlider();
		gSlider = new JSlider();
		bSlider = new JSlider();
		
		rSlider.setMaximum(255);
		gSlider.setMaximum(255);
		bSlider.setMaximum(255);
		
		add(rSlider);
		add(gSlider);
		add(bSlider);
	}
	
	
	private class SliderListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
		}
		
	}


	@Override
	public void draw(Graphics g) {
		 super.paintComponent(g);
	     g.drawRect(230,80,50,50);  
	     g.setColor(new Color(rSlider.getValue(), gSlider.getValue(), bSlider.getValue()));  
	     g.fillRect(230,80,50,50);  
	}

}
