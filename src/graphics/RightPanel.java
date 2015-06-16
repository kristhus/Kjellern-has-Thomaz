package graphics;

import interfaces.Drawable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ParticleEngine.ColorChooser;

public class RightPanel extends JPanel{

	private boolean drawableComponents;
	
	public RightPanel() {
		super();
		setBackground(Color.white);
	}
	
	public void decide(String actionCommand) {
		removeAll();
		switch(actionCommand) {
		case "gif":
			ImageIcon gif = MainFrame.getReader().readGif("/gifs/ajax-loader.gif");
			JLabel gifLabel = new JLabel();
			gifLabel.setIcon(gif);
			add(gifLabel);
			drawableComponents = false;
			break;
		case "particles":
			drawableComponents = true;
			add(new ColorChooser());
			
			break;
		}
		repaint();
	}
	
	
	public void draw(Graphics g) {
		if(drawableComponents) {
			System.out.println("HEjdd");
			for(Component c : getComponents()) {
				((Drawable) c).draw(g);
			}
		}
	}
	
}
