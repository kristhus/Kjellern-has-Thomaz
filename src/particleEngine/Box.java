package particleEngine;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;

import events.CustomMouseEvent;
import graphics.MainFrame;
import listeners.CustomMouseListener;
import physics.PhysicsObject;

public class Box extends PhysicsObject {


	public Color color;
	
	
	public Box(int x, int y, int width, int height) {
		color = Color.cyan;
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setVelocityX(0);
//		setVelocityY(-5);
		setWeight(10);
	}
	public Box(Rectangle bounds) {
		this((int) bounds.getX(),(int) bounds.getY(),(int) bounds.getWidth(),(int) bounds.getHeight());
	}
	
	
	@Override
	public boolean collided(Shape shape) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void update(double dt) {
//		updateSpeed(dt);
		updatePosition(dt);
		// Check mousePosition  compared to this object, and if it intersects, clickedor w/e
	}

	public String toString() {
		return ""+getBoundsFloat();
	}
	@Override
	public boolean outOfBounds(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
	public void setColor(Color c) {
		color = c;
	}




	
	
	
}
