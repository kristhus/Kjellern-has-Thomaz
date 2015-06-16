package ParticleEngine;

import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
Particle				-Rect / Img(?) 
MouseMotionListener ?
Select color			-JList (RGB) JSlider
Select particle density -JSlider
GRAVITY, a constant reachable by all subclasses (Constants.gravity)
TerminalVelocity -- || --

*/

public class Particle extends Rectangle{

	private int posX;
	private int posY;
	private int width;
	private int height;
	private float deltaX; //SpeedModifier
	private float deltaY; //
	private float velocity; //Absolutespeed (Euclid of dX and dY)
	private int opacity;
	private int deltaOpacity;

	private ImageIcon sprite;
	
		public Particle(int width, int height, ImageIcon sprite) {
			super(width, height);
			this.width = width;
			this.height = height;
			this.sprite = sprite;
		}
		
		public void draw(Graphics g) {
			//Draw sprite if not null
			
		}
		
		public void update(long dt){ //Update with regards to gravity if such a thing exists
			posX+=deltaX;
			posY+=deltaY;
			setBounds(posX, posY, width, height);
		}


}