package ParticleEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
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
	public int deltaOpacity = 3; //(multiplum of 5, 3 or 17)
	private boolean gravity;
	public long life;
	private int deltaRed = 20;
	private int deltaGreen = 20;
	private int deltaBlue = 20;
	
	public Color color;

	private ImageIcon sprite;
	
	//Themes
	public static final int FLAME = 1;
	public static final int OCEAN = 2;
	
	private int currentTheme;
	
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
		public Particle(int width, int height, ImageIcon sprite, Color c, int x, int y, boolean randomDirection, int currentTheme) {
			this(width, height, sprite, c, x, y, randomDirection);
			this.currentTheme = currentTheme;
			setThemeColor(currentTheme);
		}
		
		private void setThemeColor(int theme) {
			switch(theme) {
			case FLAME :
				deltaRed = 0;
				deltaGreen = 0;
				color = new Color(255,255,255, opacity);
				break;
			case OCEAN :
				deltaGreen = 0;
				deltaBlue = 0;
				color = new Color(255,255,255, opacity);
				break;
			}
		}

		public void updateTheme() {
			int red = color.getRed();
			int green = color.getGreen();
			int blue = color.getBlue();
			switch(currentTheme) {
			case FLAME :
				if(blue > deltaBlue && deltaBlue != 0) {
					blue -= deltaBlue;
				}else if (blue < deltaBlue && deltaGreen == 0) {
					deltaGreen = deltaBlue;
					deltaBlue = 0;
					blue = 0;
				}
				else if(green > deltaGreen) {
					green -= deltaGreen;
				}else if(green < deltaGreen){
					deltaGreen = 0;
					green = 0;
				}
				color = new Color(red, green, blue, opacity);
				break;
			case OCEAN :
				if(red > deltaRed && deltaRed != 0) {
					red -= deltaRed;
				}else if(deltaGreen == 0 && red <= 0) {
					deltaGreen = deltaRed;
					deltaRed = 0;
					red = 0;
				}
				else if(green > deltaGreen && deltaGreen != 0) {
					green -= deltaGreen;
				}else {
					deltaGreen = 0;
					green = 0;
				}
				color = new Color(red, green, blue, opacity);
				break;
			}
		}
		
		public void update(long dt){ //Update with regards to gravity if such a thing exists
			updateTheme();
			if(gravity && deltaY <= EnvironmentConstants.TERMINAL_VELOCITY) {
				deltaY+=EnvironmentConstants.GRAVITY/10;
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
		
		public double euclidianDistance(Point caller) {
			Point particle = new Point();
			particle.x = (int) posX;
			particle.y = (int) posY;
			return particle.distance(caller);
		}

}