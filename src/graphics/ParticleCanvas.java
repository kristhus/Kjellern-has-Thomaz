package graphics;

import interfaces.Drawable;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import particleEngine.Box;
import particleEngine.Particle;
import particleEngine.ParticleCluster;
import physics.PhysicsObject;

public class ParticleCanvas extends JPanel implements Drawable, MouseMotionListener, MouseListener{

	private ParticleCluster cluster;
	public static int mouseX;
	public static int mouseY;
	public static boolean mouseDown;
	
	private ArrayList<Box> collisionBoxes;
	
	// ADD?: Interpolation and movement along line to smoothen mousemovement
	
	public ParticleCanvas() {
		
		
		cluster = new ParticleCluster(1000, 200, this);
		collisionBoxes = new ArrayList<Box>();
		
		BorderLayout bl = new BorderLayout();
		setLayout(bl);
		setPreferredSize(new Dimension(1000, 800));
		setLocation(300,300);
		setBackground(Color.black);
		setVisible(true);
		addMouseMotionListener(this);
		addMouseListener(this);
		
		collisionBoxes.add(new Box(300, 300, 100,100));
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ArrayList<Particle> c = cluster.getParticles();
   	 	for(Particle p : c) {
			g.setColor(p.color);
			g.fillRect((int) p.getX(), (int) p.getY(), (int)p.getWidth(), (int)p.getHeight());
   	 	}
   	 	for(Box b : collisionBoxes) {
   	 		g.setColor(b.color);
   	 		g.fillRect((int) b.getX(), (int) b.getY(), (int)b.getWidth(), (int)b.getHeight());
   	 	}
   	 	g.dispose();
	}
	
	public void draw(Graphics g) {
		// By overriding paintcomponent flickering is avoided (this is also the correct way to do it)
		repaint();
	}
	
	public void update(double dt) {
		cluster.update(dt);
		for(Box b : collisionBoxes)
			b.update(dt);
	}
	
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		mouseX = arg0.getX();
		mouseY = arg0.getY();
		
	}


	
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mouseX = arg0.getX();
		mouseY = arg0.getY();
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mouseDown = true;
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mouseDown = false;
	}
	
	public ArrayList<Box> getCollisionBoxes() {
		return collisionBoxes;
	}
	
}
