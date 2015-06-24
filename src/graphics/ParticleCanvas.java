package graphics;

import interfaces.Drawable;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import javax.swing.JPanel;

import data.ConcurrentArrayList;
import particleEngine.Box;
import particleEngine.Particle;
import particleEngine.ParticleCluster;
import physics.PhysicsObject;

public class ParticleCanvas extends JPanel implements Drawable{

	private ParticleCluster cluster;
	private ArrayList<Box> collisionBoxes;
	private Graphics sourceGraphics;
	private Box tmpBox;
	// ADD?: Interpolation and movement along line to smoothen mousemovement
	
	public ParticleCanvas() {
		
		
		cluster = new ParticleCluster(100000, 1000, this);
		collisionBoxes = new ArrayList<Box>();
		
		BorderLayout bl = new BorderLayout();
		setLayout(bl);
		setPreferredSize(new Dimension(1000, 800));
		setLocation(300,300);
		setBackground(Color.black);
		setVisible(true);
		addMouseMotionListener(MainFrame.mouseListener);
		addMouseListener(MainFrame.mouseListener);
		
		
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(tmpBox != null){
			//Draw tmpBox
			g.setColor(Color.yellow);
			g.fillRect((int)tmpBox.getX(),(int)tmpBox.getY(),(int) tmpBox.getWidth(),(int) tmpBox.getHeight());
		}
		ConcurrentArrayList<Particle> c = cluster.getParticles();
			Iterator<Particle> tmp = c.iterator();
		while(tmp.hasNext()){
			Particle p = tmp.next();
			g.setColor(p.color);
			g.fillRect((int) p.getX(), (int) p.getY(), (int)p.getWidth(), (int)p.getHeight());
   	 		}
   	 	// COLLIDABLES
   	 	for(Box b : collisionBoxes) {
   	 		g.setColor(b.color);
   	 		g.fillRect((int) b.getX(), (int) b.getY(), (int)b.getWidth(), (int)b.getHeight());
   	 	}
   	 	g.dispose();
	}
	
	public void draw(Graphics g) {
		// By overriding paintcomponent flickering is avoided (this is also the correct way to do it)
		sourceGraphics = g;
		repaint();
	}
	
	public void update(double dt) {
		cluster.update(dt);
		for(Box b : collisionBoxes)
			b.update(dt);
		
		for(int i = 0; i < collisionBoxes.size()-1; i++) {
			Box b = collisionBoxes.get(i);
			for(int j = 1; j < collisionBoxes.size(); j++) {
				Box p = collisionBoxes.get(j);
		if(p.willCollide(b, p.getNextVelocityY(), 0)) {
			try{
				p.collide(b, p.getNextVelocityY());
				System.out.println("________________");
			}catch(NullPointerException e){
//				e.printStackTrace();
				//TODO; Fiks na jævla erroren
			}finally {
				//System.exit(0);
			}
		}
		}
	}
		if(MainFrame.mouseListener.btnFromSource) {
			if(tmpBox == null)
				tmpBox = new Box(MainFrame.mouseListener.mousePosition.x, MainFrame.mouseListener.mousePosition.y, 30,30);
			MainFrame.mouseListener.takenAction = false;
//			tmpBox.setX( - (MainFrame.mouseListener.externalSource.getLocation().x - getLocation().x));
			int differenceX = getLocationOnScreen().x-MainFrame.mouseListener.externalSource.getLocationOnScreen().x;
			int differenceY = getLocationOnScreen().y-MainFrame.mouseListener.externalSource.getLocationOnScreen().y;
			System.out.println("X: " + differenceX + "___ y: " + differenceY);
			tmpBox.setX(MainFrame.mouseListener.mousePosition.x-differenceX);
			tmpBox.setY(MainFrame.mouseListener.mousePosition.y-differenceY);
//			System.out.println(tmpBox);
//			MainFrame.mouseListener.btnFromSource = false;
//			collisionBoxes.add(new Box(MainFrame.mouseListener.mousePosition.x, MainFrame.mouseListener.mousePosition.y, 30,30));
//			System.out.println(collisionBoxes);
			MainFrame.mouseListener.waitForRelease = true;
		}else if(tmpBox != null && !MainFrame.mouseListener.takenAction) {
			collisionBoxes.add(tmpBox);
			MainFrame.mouseListener.takenAction = true;
			tmpBox = null;
		}
		
	}
	
	
	public ArrayList<Box> getCollisionBoxes() {
		return collisionBoxes;
	}
	
}
