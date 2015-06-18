package moveable;

import interfaces.Drawable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;



public class Player extends JPanel implements Collidable, Drawable   {

	
	private Shape bounds;
	private int height;
	private int width;
	private int posX;
	private int posY;
	private int dX;
	private int dY;
	private int velocityX;
	private int velocityY;
	private boolean keyDown;
	private boolean keyLeftPressed; 
	private boolean keyUpPressed;
	private boolean keyRightPressed;
	private boolean keyDownPressed;
	
	public Player(int height, int width, int posX, int posY, int velocityX, int velocityY ){
		
		super();
		this.height = height;
		this.width = width;
		this.posX = posX;
		this.posY = posY;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		
	}
	

	@Override
	public boolean collided(Rectangle rect) {
		return false;
	}
	
	
	public void draw(Graphics g){
		g=getGraphics();
		g.drawRect(posX, posY, width, height);
		g.fillRect(posX, posY, width, height);
		g.setColor(Color.BLACK);
		
	}
	

	public void update(long dt){		
		// deltaTime
		if(keyLeftPressed || keyRightPressed || keyDownPressed || keyUpPressed) {
			posX += dX;
			posY += dY;
		}
		
		
		
	}


	
	
	
	
}
