package moveable;

import java.awt.Shape;

public interface Collidable {

	
	public boolean collided(Shape shape);
	
	public boolean outOfBounds(int x, int y);
	
	
}
