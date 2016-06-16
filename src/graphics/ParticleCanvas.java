package graphics;

import interfaces.Drawable;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import data.ConcurrentArrayList;
import particleEngine.Box;
import particleEngine.Particle;
import particleEngine.ParticleCluster;
import particleEngine.ParticleSettings;

public class ParticleCanvas extends JPanel implements Drawable{

	private ParticleCluster cluster;
	private ArrayList<ParticleCluster> clusters;
	
	private ArrayList<DirectionalSprays> directionalSprays;
	
	private ArrayList<Box> collisionBoxes;
	
	private Box tmpBox;
	
	private Toolbox tb;
	
	private ParticleSettings settings;
	
	private int initMouseX;
	private int initMouseY;
	
	private boolean dragDirectionalSpray;
	
	// ADD?: Interpolation and movement along line to smoothen mousemovement
	
	private boolean haltCluster;
	
	public ParticleCanvas() {
		clusters = new ArrayList<ParticleCluster>();
		cluster = new ParticleCluster(200000, 10000, this );
		clusters.add(cluster);
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
	
	public ParticleCanvas(Toolbox tb) {
		this();
		this.tb = tb;
		directionalSprays = new ArrayList<ParticleCanvas.DirectionalSprays>();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if(tmpBox != null){
			//Draw tmpBox
			g.setColor(Color.yellow);
			g.fillRect((int)(tmpBox.getX()- tmpBox.getWidth()/2),(int)(tmpBox.getY()- tmpBox.getHeight()/2),(int) tmpBox.getWidth(),(int) tmpBox.getHeight());
		}
   	 	for(Box b : collisionBoxes) {
   	 		g.setColor(b.getColor());
   	 		g.fillRect((int) b.getX(), (int) b.getY(), (int)b.getWidth(), (int)b.getHeight());
   	 	} 
   	 	//Particle spray
   	 	if(tb.isSprayCan()) {
   	 		ConcurrentArrayList<Particle> c = cluster.getParticles();
   	 		Iterator<Particle> tmp = c.iterator();
   	 		while(tmp.hasNext()){
   	 			Particle p = tmp.next();
   	 			g.setColor(p.color);
   	 			g.fillRect((int) p.getX(), (int) p.getY(), (int)p.getWidth(), (int)p.getHeight());
   	 		}
   	 	}
   	 	// COLLIDABLES
		if(tb.dirOutputIsDown() && !MainFrame.mouseListener.leftMouseDown) {
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(Color.white);
			g2d.drawOval(MainFrame.mouseListener.mousePosition.x-15/2, MainFrame.mouseListener.mousePosition.y-15/2, 15, 15);
		}
		
		//Temporary line
		if(tb.dirOutputIsDown() && dragDirectionalSpray) {
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(Color.white);
			g2d.drawLine(initMouseX, initMouseY, MainFrame.mouseListener.mousePosition.x, MainFrame.mouseListener.mousePosition.y);
		}
		
		//Directional sprays
		for(DirectionalSprays spray : directionalSprays) {
			g2d.setStroke(new BasicStroke(1));
			g2d.setColor(Color.white);
			g2d.drawOval(spray.posX-spray.radius/2, spray.posY-spray.radius/2, spray.radius, spray.radius);
			g2d.drawLine(spray.posX, spray.posY, spray.endX, spray.endY);
			
			
			ConcurrentArrayList<Particle> c = spray.getSpraynpray().getParticles();
   	 		Iterator<Particle> tmp = c.iterator();
   	 		while(tmp.hasNext()){
   	 			Particle p = tmp.next();
   	 			g.setColor(p.color);
   	 			g.fillRect((int) p.getX(), (int) p.getY(), (int)p.getWidth(), (int)p.getHeight());
   	 		}
			
		}
		g2d.dispose();
   	 	g.dispose();
	}
	
	public void draw(Graphics g) {
		// By overriding paintcomponent flickering is avoided (this is also the correct way to do it)
		repaint();
	}
	
	public void update(double dt) {
		
		
		if(!haltCluster){
			if(!tb.isSprayCan()) {
				ParticleCluster tmp = new ParticleCluster(cluster.getParticleLimit(), cluster.getParticlesPerSecond(), this);
				cluster = tmp;
			}
			else {
				cluster.update(dt);
			}
			for(DirectionalSprays ds : directionalSprays) {
				ds.getSpraynpray().update(dt);
			}
			
			
			for(Box b : collisionBoxes)
				b.update(dt);
		}
		if(tb.dirOutputIsDown() && MainFrame.mouseListener.leftMouseDown && !dragDirectionalSpray) {
			initMouseX = MainFrame.mouseListener.mousePosition.x;
			initMouseY = MainFrame.mouseListener.mousePosition.y;
			dragDirectionalSpray = true;
			//tb.setDirOutputIsSelected(false);
			//Graphics2D g2d = (Graphics2D) g;
			//g2d.setStroke(new BasicStroke(2));
			//g2d.setColor(Color.white);
			//g2d.drawOval(MainFrame.mouseListener.mousePosition.x, MainFrame.mouseListener.mousePosition.y, 15, 15);
		}
		else if(dragDirectionalSpray && !MainFrame.mouseListener.leftMouseDown) {
			//Finalize
			DirectionalSprays spray = new DirectionalSprays(
					initMouseX,
					initMouseY, 15, 
					MainFrame.mouseListener.mousePosition.x,
					MainFrame.mouseListener.mousePosition.y,
					this);
			directionalSprays.add(spray);
			System.out.println(spray);
			tb.setDirOutputIsSelected(false);
			dragDirectionalSpray = false;
		}
		
			//setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			for(int i = 0; i < collisionBoxes.size(); i++) {
				Box b = collisionBoxes.get(i);
				if(b.getBounds().contains(MainFrame.mouseListener.mousePosition)) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
					if(MainFrame.mouseListener.leftMouseDown){
						cluster.removeAll();
						haltCluster = true;
						// OPEN A NEW WINDOW
						if(settings != null)
							settings.dispose();
						settings = new ParticleSettings("Box settings", b);
					} else{
						haltCluster = false;
					}
				}else{
					if(!MainFrame.mouseListener.leftMouseDown)
						haltCluster = false;
				}
				b.goesOutOfBounds(new Rectangle(0,0, getWidth(), getHeight()));
				for(int j = 1; j < collisionBoxes.size(); j++) {
					Box p = collisionBoxes.get(j);
					p.goesOutOfBounds(p.getBounds());
					if(p.willCollide(b, p.getNextVelocityY(), 0)) {
						try{
							
							p.collide(b, p.getNextVelocityY());
						}catch(NullPointerException e){
							// e.printStackTrace();
							//TODO; Fiks na j�vla erroren
						}finally {
							//System.exit(0);
						}
					}
				}
				if(settings != null)
					settings.update(dt);
			}
//		 else {cluster = new ParticleCluster(200000, 10000, this); }
		
		if(MainFrame.mouseListener.btnFromSource) {
			if(tmpBox == null)
				tmpBox = new Box(MainFrame.mouseListener.mousePosition.x, MainFrame.mouseListener.mousePosition.y, 30,30);
			MainFrame.mouseListener.takenAction = false;
			int differenceX = getLocationOnScreen().x-MainFrame.mouseListener.externalSource.getLocationOnScreen().x;
			int differenceY = getLocationOnScreen().y-MainFrame.mouseListener.externalSource.getLocationOnScreen().y;
			tmpBox.setX(MainFrame.mouseListener.mousePosition.x-differenceX);
			tmpBox.setY(MainFrame.mouseListener.mousePosition.y-differenceY);
			MainFrame.mouseListener.waitForRelease = true;
		}else if(tmpBox != null && !MainFrame.mouseListener.takenAction) { // Add new box to canvas
			tmpBox.setX((int) (tmpBox.getX()- tmpBox.getWidth()/2));
			tmpBox.setY((int)(tmpBox.getY()- tmpBox.getHeight()/2));
			collisionBoxes.add(tmpBox);
			MainFrame.mouseListener.takenAction = true;
			tmpBox = null;
		}
	}
	
	
	public ArrayList<Box> getCollisionBoxes() {
		return collisionBoxes;
	}
	
	private class DirectionalSprays {
		private int posX;
		private int posY;
		private int radius;
		
		private int endX; //Direction of spray
		private int endY;
		
		private double angle;
		
		private ParticleCluster spraynpray;
		
		public DirectionalSprays(int posX, int posY, int radius, int endX, int endY, ParticleCanvas pc) {
			this.posX = posX;
			this.posY = posY;
			this.radius = radius;
			this.endX = endX;
			this.endY = endY;
			
			
			
			angle = Math.toDegrees(Math.atan2(endY-posY, endX - posX));
			if(angle <= 0 && angle >= -90)
				angle = -90-angle;
			else if(angle < -90)
				angle = -90-angle;
			else if(angle >= 90) 
				angle = 270-angle;
			else if (angle <= 180) 
				angle = 270-angle;
			if(angle < 0){
		        angle += 360;
		    }
			angle = Math.toRadians(angle);
//			if(angle < 0) angle+= 2*Math.PI;
			ArrayList<Point> dir = new ArrayList<Point>();
			dir.add(new Point(posX, endX));
			dir.add(new Point(posY, endY));
			
			spraynpray = new ParticleCluster(10000, 100, pc, posX, posY, dir);
			spraynpray.setDirectional(angle-Math.toRadians(5), angle + Math.toRadians(5));
			double length = Math.sqrt(Math.pow(posX-endX,2) + Math.pow(posY-endY, 2));
			spraynpray.setSpeed(length/20);
		}
		
		public ParticleCluster getSpraynpray(){
			return spraynpray;
		}
		
		public String toString() {
			return "PosX: " + posX + "\n" + "PosY: " + posY + "\n" + "Radius: " + radius + "\n" + "EndX " + endX + "\n" + "EndY " + endY + "\n" + "Angle: " + Math.toDegrees(angle);
		}
	}
	
}
