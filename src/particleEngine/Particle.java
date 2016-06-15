package particleEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.ImageIcon;

import physics.PhysicsObject;
import constants.EnvironmentConstants;

/**
Particle				-Rect / Img(?) 
MouseMotionListener ?
Select color			-JList (RGB) JSlider
Select particle density -JSlider
GRAVITY, a constant reachable by all subclasses (Constants.gravity)
TerminalVelocity -- || --

*/

public class Particle extends PhysicsObject{

	public float velocity = 5; //MaxSpeed
	public int opacity = 255;
	public double deltaOpacity = 1; //(multiplum of 5, 3 or 17)
	private boolean gravity;
	public double life;
	private int deltaRed = 7;
	private int deltaGreen = 7;
	private int deltaBlue = 7;
	
	public Color color;

	private ImageIcon sprite;
	
	//Themes
	public static final int FLAME = 1;
	public static final int OCEAN = 2;
	
	private int currentTheme;
	
		public Particle(int width, int height, ImageIcon sprite, Color c, int x, int y, boolean randomDirection) {
			setWidth(width);
			setHeight(height);
			setDensity(0.005);
			setWeight(getDensity()*width*height);
			this.sprite = sprite;
			color = c;
			setX(x);
			setY(y);
			
			//random direction
			if(randomDirection) {
				double tmpdirx = (Math.random()*velocity); 
				double tmpdiry = (Math.random()*velocity); 
				setVelocityX(-(Math.random()*velocity)); 
				setVelocityY(-(Math.random()*velocity));
				if((int)(Math.random()*2) == 1) {
					setVelocityX(tmpdirx);
				}
				if((int)(Math.random()*2) == 1) {
					setVelocityY(tmpdiry);
				}
			}
			setVelocityX(getVelocityX() + Math.random());
			setVelocityY(getVelocityY() + Math.random());
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
		
		
		public void update(double dt){ //Update with regards to gravity if such a thing exists
			updateTheme();
			if(gravity && getVelocityY() <= EnvironmentConstants.TERMINAL_VELOCITY) {
				updateSpeed(dt);
			}
			life+= dt;
			
			if(opacity >= deltaOpacity) {
				opacity -= deltaOpacity;
			}
			color = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
			updatePosition(dt);
		}

		public double getLife() {
			return life;
		}
		
		public double euclidianDistance(Point caller) {
			Point particle = new Point();
			particle.x = (int) getX();
			particle.y = (int) getY();
			return particle.distance(caller);
		}
		
		@Override
		public boolean collided(Shape shape) {
			// TODO Auto-generated method stub
			return false;
		}
		
		public Rectangle.Float getBoundsFloat() {
			return new Rectangle.Float((float)getX(), (float)getY(), (float)getWidth(), (float)getHeight());
		}

		@Override
		public boolean outOfBounds(int x, int y) {
			// TODO Auto-generated method stub
			return false;
		}


}