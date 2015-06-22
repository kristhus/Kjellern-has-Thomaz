package moveable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;

import interfaces.Drawable;
import graphics.MainFrame;

public class Player extends Moveable implements Drawable{
 
	public Player(Shape bounds){
		super(20, 20, 50, 50, 5, 5);
		setBounds(bounds);
	}

	@Override
	public boolean outOfBounds(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(Graphics g){
//		super.paint(g);
		g.setColor(Color.BLACK);
		g.drawRect(getPosX(), getPosY(), getWidth(), getHeight());
		g.fillRect(getPosX(), getPosY(), getWidth(), getHeight());
		
	}
	
	
	
	
}
