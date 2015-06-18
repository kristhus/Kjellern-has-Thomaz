package ParticleEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import constants.EnvironmentConstants;

/**
Particle				-Rect / Img(?) 
MouseMotionListener ?
Select color			-JList (RGB) JSlider
Select particle density -JSlider
GRAVITY, a constant reachable by all subclasses (Constants.gravity)
TerminalVelocity -- || --

*/

public class Particle extends Rectangle{

	public double posX;
	public double posY;
	public int width;
	public int height;
	public double deltaX; //SpeedModifier
	public double deltaY; //
	public float velocity = 5; //MaxSpeed
	public int opacity = 255;
	public int deltaOpacity = 9; //(multiplum of 5, 3 or 17)
	private boolean gravity;
	public long life;
	
	public Color color;

	private ImageIcon sprite;
	
		public Particle(int width, int height, ImageIcon sprite, Color c, int x, int y, boolean randomDirection) {
			super(width, height);
			this.width = width;
			this.height = height;
			this.sprite = sprite;
			color = c;
			posX = x;
			posY = y;
			
			//random direction
			if(randomDirection) {
				double tmpdirx = (Math.random()*velocity); 
				double tmpdiry = (Math.random()*velocity); 
				deltaX = -(Math.random()*velocity)- Math.random(); 
				deltaY = -(Math.random()*velocity)- Math.random();
				if((int)(Math.random()*2) == 1) {
					deltaX= tmpdirx + Math.random();
				}
				if((int)(Math.random()*2) == 1) {
					deltaY= tmpdiry + Math.random();
				}
			}
			deltaX += Math.random();
			deltaY += Math.random();
			
			gravity = true;
			
		}

		
		public void update(long dt){ //Update with regards to gravity if such a thing exists
			if(gravity && deltaY <= EnvironmentConstants.TERMINAL_VELOCITY) {
				deltaY+=EnvironmentConstants.GRAVITY/20;
			}
			
			life+= dt;
			if(opacity >= deltaOpacity) {
				opacity -= deltaOpacity;
			}
			color = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
			posX+=deltaX;
			posY+=deltaY;
			setBounds((int) posX, (int) posY, width, height);
		}

		public long getLife() {
			return life;
		}

}