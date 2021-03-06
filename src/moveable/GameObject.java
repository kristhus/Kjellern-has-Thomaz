package moveable;

import graphics.MainFrame;
import interfaces.Drawable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;




public abstract class GameObject extends JPanel implements Collidable,  Drawable   {

	
	private Shape bounds;
	private int height;
	private int width;
	private int posX;
	private int posY;
	private int posXNext;
	private int posYNext;
	private int dX;
	private int dY;
	private JComponent ancestor;
	
	public GameObject(int height, int width, int posX, int posY, int dX, int dY){
		
		super();
		this.height = height;
		this.width = width;
		this.posX = posX;
		this.posY = posY;
		this.dX = dX;
		this.dY = dY;
		
	}
	

	@Override
	public boolean collided(Shape shape) {
		return false;
	}
	
	

	public void update(double dt){		
		// deltaTime
		boolean keyLeftPressed, keyRightPressed, keyDownPressed, keyUpPressed;
		keyLeftPressed = MainFrame.getKeyBoardListener().isKeyLeftPressed();
		keyRightPressed = MainFrame.getKeyBoardListener().isKeyRightPressed();
		keyDownPressed = MainFrame.getKeyBoardListener().isKeyDownPressed();
		keyUpPressed = MainFrame.getKeyBoardListener().isKeyUpPressed();
		
		System.out.println("Bæææææ");
		posXNext = posX;
		posYNext = posY;
			if(keyLeftPressed){ 
				posXNext -= dX;
				if(insideBounds(posXNext, posYNext, width, height)){
					posX -= dX;
				}
				else if(!insideBounds(posXNext, posYNext, width, height) && posX - dX <= 0 ){
					posX = 0;
				}
			}
			if(keyRightPressed){
				posXNext += dX;
				if(insideBounds(posXNext, posYNext, width, height)){
					posX += dX;
				}
				else if(!insideBounds(posXNext, posYNext, width, height) && posX + dX >= ancestor.getWidth()){
					posX = ancestor.getWidth() - width;
				}
			}
			if(keyDownPressed){
				posYNext += dY;
				if(insideBounds(posXNext, posYNext, width, height)){
					posY += dY;
				}
				else if((!insideBounds(posXNext, posYNext, width, height) && posY + dY >= ancestor.getHeight())){
					posY = ancestor.getHeight() - height; 
				}
			}
			if(keyUpPressed){
				posYNext -= dY;
				if(insideBounds(posXNext, posYNext, width, height)){
					posY -= dY;
				}
				else if((!insideBounds(posXNext, posYNext, width, height) && posY - dY <= 0 )){
					posY = 0;
				}
		
			}
			
	}

	
	public boolean insideBounds(double x, double y, double w, double h){
		return((x >= 0 && x + w <= ancestor.getWidth()) && (y >= 0 && y + h <= ancestor.getHeight()));
		
		
	}

	public Rectangle getBounds() {
		return (Rectangle) bounds;
	}

	public void setBounds(Shape bounds) {
		this.bounds = bounds;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getPosX() {
		return posX;
	}


	public void setPosX(int posX) {
		this.posX = posX;
	}


	public int getPosY() {
		return posY;
	}


	public void setPosY(int posY) {
		this.posY = posY;
	}


	public int getPosXNext() {
		return posXNext;
	}


	public void setPosXNext(int posXNext) {
		this.posXNext = posXNext;
	}


	public int getPosYNext() {
		return posYNext;
	}


	public void setPosYNext(int posYNext) {
		this.posYNext = posYNext;
	}


	public int getdX() {
		return dX;
	}


	public void setdX(int dX) {
		this.dX = dX;
	}


	public int getdY() {
		return dY;
	}


	public void setdY(int dY) {
		this.dY = dY;
	}

	public void setAncestor(JComponent j){
		
		ancestor = j;
		
	}
	
	
	
	
}
