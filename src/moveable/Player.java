package moveable;

import graphics.MainFrame;
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
	
	public Player(int height, int width, int posX, int posY, int velocityX, int velocityY ){
		
		super();
		this.height = height;
		this.width = width;
		this.posX = posX;
		this.posY = posY;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		dX = velocityX;
		dY = velocityY;
		
	}
	

	@Override
	public boolean collided(Rectangle rect) {
		return false;
	}
	
	
	public void draw(Graphics g){
		g=getGraphics();
		paint(g);
		g.drawRect(posX, posY, width, height);
		g.fillRect(posX, posY, width, height);
		g.setColor(Color.BLACK);
		
	}
	

	public void update(double dt){		
		// deltaTime
		boolean keyLeftPressed, keyRightPressed, keyDownPressed, keyUpPressed;
		keyLeftPressed = MainFrame.getKeyBoardListener().isKeyLeftPressed();
		keyRightPressed = MainFrame.getKeyBoardListener().isKeyRightPressed();
		keyDownPressed = MainFrame.getKeyBoardListener().isKeyDownPressed();
		keyUpPressed = MainFrame.getKeyBoardListener().isKeyUpPressed();
		if(keyLeftPressed) {
			posX-=dX;
		}
		if(keyRightPressed)
			posX+=dX;
		if(keyDownPressed)
			posY+=dY;
		if(keyUpPressed)
			posY-=dY;
		
		
		
	}


	
	
	
	
}
