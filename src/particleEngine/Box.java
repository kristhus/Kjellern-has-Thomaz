package particleEngine;

import java.awt.Color;
import java.awt.Rectangle;

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
		setVelocityY(0);
	}
	
	@Override
	public boolean collided(Rectangle rect) {
		// TODO Auto-generated method stub
		return false;
	}

}
