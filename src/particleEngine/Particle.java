package particleEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Random;

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
	
	private double initAngleMin;
	private double initAngleMax;
	private ArrayList<Point> dir;
	
	public Color color;

	private ImageIcon sprite;
	
	//Themes
	public static final int FLAME = 1;
	public static final int OCEAN = 2;
	
	private int currentTheme;
	
		public Particle(int width, int height, ImageIcon sprite, Color c, int x, int y, boolean randomDirection) {
//			currentTheme = FLAME;
//			setThemeColor(FLAME);
//			setBounciness(0);
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
				double tmpdirx = 0;
				double tmpdiry = 0;
				double velocityLength = 0;
				while(true) {
					tmpdirx = randomDecimal(velocity);
					tmpdiry = randomDecimal(velocity);
					velocityLength = Math.sqrt(Math.pow(tmpdirx, 2) + Math.pow(tmpdiry, 2));
					if(velocityLength < Math.abs(velocity)) {
						break;
					}
				}
				
				setVelocityX(tmpdirx);
				setVelocityY(tmpdiry);
//					System.out.println(tmpdirx);
//					System.out.println(tmpdiry);
//					System.out.println(getVelocityX());
//					System.out.println(getVelocityY());
//					System.out.println(velocityLength);
//					System.out.println(max);
//					System.exit(0);
//				}
				
				setGravity(true);
			}else {
				//float rad = Math.toRadians(2);
				setVelocityX(velocity);
				setVelocityY(velocity);
				setGravity(true);
			}
			
		}
		
		/**
		 * Returns a double in the range of [-max, max]
		 * @param max Highest allowed value
		 * @return
		 */
		public double randomDecimal(double max) {
			double tmp = (Math.random()*max); 
			
			if((int)(Math.random()*2) == 1) {
				tmp*=-1;
			}
			return tmp;
		}
		
		public Particle(int width, int height, ImageIcon sprite, Color c, int x, int y, double minAngle, double maxAngle, double speed, ArrayList<Point> dir) {
			this(width, height, sprite, c, x, y, false);
			initAngleMin = minAngle;
			initAngleMax = maxAngle;
			this.dir = dir;
			Random rand = new Random();
			double randomDec1 = (double) (rand.nextDouble()*(1.0-0.6) + 0.6);
//			randomDec1 = 1;
			double angle = (double) (rand.nextDouble() * (maxAngle - minAngle) + minAngle);
//			if(angle<0) angle+=2*Math.PI;
//			if(angle>2*Math.PI) angle-=2*Math.PI;
//			double angle = maxAngle;
//			angle = Math.toRadians(angle);
			
			double vx = Math.abs(dir.get(0).x-dir.get(1).x);
			double vy = Math.abs(dir.get(0).y-dir.get(1).y);
			double length = Math.sqrt(  Math.pow(vx, 2) + Math.pow(vy, 2)     );
			double mod = randomDec1*speed/length;
//			mod = 0.05;
			
			// DEss lengre avstandsvektoren til sprayen blir, dess st�rre un�yaktighet blir det
			//Dersom vx eller vy g�r mot radius, er farten korrekt
			double tmpVx = ((Math.cos(angle) - (vx-(int)vx)*Math.sin(angle)));
			double tmpVy = ((Math.sin(angle) - (vy-(int)vy)*Math.cos(angle)));
			
			// Scale velocities towards speed
			double speedVect = Math.sqrt(speed*speed);
			double constant = speedVect/(Math.pow(tmpVx,2) + (Math.pow(tmpVy,2)));
			setVelocityX(tmpVx*constant*randomDec1);
			setVelocityY(tmpVy*constant*randomDec1);
		}
		
		/** Initialize particle with starting point and direction according to a line spray (Directional Spray)
		 * 
		 * @param width
		 * @param height
		 * @param sprite image
		 * @param c Color
		 * @param x start of line x
		 * @param y start of line y
		 * @param speed length of line modified
		 * @param dir start and end points
		 */
		public Particle(int width, int height, ImageIcon sprite, Color c, int x, int y, double speed, ArrayList<Point> dir) {
			this(width, height, sprite, c, x, y, false);
			
			
			Random rand = new Random();
			//int rY = rand.nextInt(Math.max(dir.get(0).y, dir.get(1).y) - Math.min(dir.get(0).y, dir.get(1).y) +1) + Math.min(dir.get(0).y, dir.get(1).y);
			
			double X0 = dir.get(1).x - dir.get(0).x; // Need to move the x towards 0
			double Y0 = dir.get(1).y - dir.get(0).y;
			if(X0 == 0 && Y0 == 0) {
				// Just a placeholder to skip the next steps
			}
			else if(X0 == 0) {
				int rY = rand.nextInt(Math.max(dir.get(0).y, dir.get(1).y) - Math.min(dir.get(0).y, dir.get(1).y) +1) + Math.min(dir.get(0).y, dir.get(1).y);
				setY(rY);
			}else if(Y0 == 0) {
				int rX = rand.nextInt(Math.max(dir.get(0).x, dir.get(1).x) - Math.min(dir.get(0).x, dir.get(1).x) +1) + Math.min(dir.get(0).x, dir.get(1).x);
				setX(rX);
			}
			else if(Math.abs(X0) < Math.abs(Y0)) { // f(x)
				int rX = rand.nextInt(Math.max(dir.get(0).x, dir.get(1).x) - Math.min(dir.get(0).x, dir.get(1).x) +1) + Math.min(dir.get(0).x, dir.get(1).x);
				double rY = (X0 - (dir.get(1).x - rX))*((dir.get(1).y - dir.get(0).y) / (dir.get(1).x - dir.get(0).x) ) + dir.get(0).y; // f(x) = ax + b, a = p1-p2
				setX(rX);
				setY(rY);
			}else { // f(y)
				int rY = rand.nextInt(Math.max(dir.get(0).y, dir.get(1).y) - Math.min(dir.get(0).y, dir.get(1).y) +1) + Math.min(dir.get(0).y, dir.get(1).y);
				double rX = (Y0 - (dir.get(1).y - rY))*((dir.get(1).x - dir.get(0).x) / (dir.get(1).y - dir.get(0).y) ) + dir.get(0).x; // f(x) = ax + b, a = p1-p2
				setX(rX);
				setY(rY);
			}
			
//			int dirX = rX+(dir.get(1).x - x);
//			int dirY = rY+(dir.get(1).y - y);
			
			double vx = Math.abs(dir.get(0).x-dir.get(1).x);
			double vy = Math.abs(dir.get(0).y-dir.get(1).y);
			double length = Math.sqrt(  Math.pow(vx, 2) + Math.pow(vy, 2)     );
			
			double angle = Math.toDegrees(Math.atan2(dir.get(1).y-dir.get(0).y, dir.get(1).x - dir.get(0).x));
			double tmpVx = Math.cos(angle)*vx + vy*Math.sin(angle);
			double tmpVy = -Math.sin(angle)*vx + Math.cos(angle)*vy;
			
			// Scale velocities towards speed
			double speedVect = Math.sqrt(speed*speed);
			double constant = speedVect/(Math.pow(tmpVx,2) + (Math.pow(tmpVy,2)));
			
			setVelocityX(tmpVx*constant);
			setVelocityY(tmpVy*constant);
			
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
			if(isGravity() && getVelocityY() <= EnvironmentConstants.TERMINAL_VELOCITY) {
				updateSpeed(dt);
			}
			life+= dt;
			
			if(opacity >= deltaOpacity) {
				opacity -= deltaOpacity;
			}
			color = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
			if(!isStatic()) updatePosition(dt);
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
			return true;
		}


}