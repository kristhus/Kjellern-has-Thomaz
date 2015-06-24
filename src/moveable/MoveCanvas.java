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
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		p.draw(g);	
	}
	
	@Override
	public void draw(Graphics g) {
		repaint();
	}


	@Override
	public void update(double dt) {
		// TODO Auto-generated method stub
		p.update(dt);
	}
	
	
	
}
