package moveable;

import java.awt.Graphics;

import interfaces.Drawable;

import javax.swing.JPanel;

public class MoveCanvas extends JPanel implements Drawable{

	
	private Player p;
	
	public MoveCanvas() {
		p = new Player(getBounds());
		setBounds(100,100,500,500);
		p.setBounds(p.getBounds());
		p.setAncestor(this);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g = getGraphics();
		super.paintComponent(g);
		g = getGraphics();
		p.draw(g);	
	}

	@Override
	public void update(long dt) {
		// TODO Auto-generated method stub
		p.update(dt);
	}
	
	
	
}
