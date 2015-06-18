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

import ParticleEngine.Particle;
import ParticleEngine.ParticleCluster;

public class ParticleCanvas extends JPanel implements Drawable, MouseMotionListener, MouseListener{

	private ParticleCluster cluster;
	public static int mouseX;
	public static int mouseY;
	public static boolean mouseDown;
	
	
	public ParticleCanvas() {
		
		
		cluster = new ParticleCluster(1000, 100, this);
		
		BorderLayout bl = new BorderLayout();
		setLayout(bl);
		setPreferredSize(new Dimension(1000, 1000));
		setBackground(Color.white);
		setVisible(true);
		validate();
		
		addMouseMotionListener(this);
		addMouseListener(this);
		
	}

	public void draw(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
	    	 ArrayList<Particle> c = cluster.getParticles();
	    	 for(Particle p : c) {
	 			g2d.setColor(p.color);
	 			g2d.fillRect(p.posX, p.posY, p.width, p.height);
	     }
	     g2d.dispose();
	}
	
	public void update(long dt) {
		cluster.update(dt);
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
	
}
