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
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import constants.EnvironmentConstants;
import data.ConcurrentArrayList;
import particleEngine.Box;
import particleEngine.ColorChooser;
import particleEngine.Particle;
import particleEngine.ParticleCluster;
import particleEngine.PhysicsObjectSettings;
import physics.PhysicsObject;

public class ParticleCanvas extends JPanel implements Drawable{

	private ParticleCluster cluster;
	private ArrayList<ParticleCluster> clusters;
	
	private ArrayList<DirectionalSprays> directionalSprays;
	
	private ArrayList<Box> collisionBoxes;
	
	private Box tmpBox;
	
	private Toolbox tb;
	
	private PhysicsObjectSettings settings;
	
	private int initMouseX;
	private int initMouseY;
	
//	private boolean mouseOverDirectionalSpray;
//	private boolean mouseOverObject;
	
	private boolean movePhysicsObject;
	
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
		addKeyListener(MainFrame.keyHandler);
	}
	
	public ParticleCanvas(Toolbox tb) {
		this();
		this.tb = tb;
		directionalSprays = new ArrayList<DirectionalSprays>();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		// ENABLE ON A NEED ONLY BASIS, as this shit wrecks fps
		if(MainFrame.keyHandler.zDown) {
			g2d.scale(4.0, 4.0);
		}else g2d.scale(1, 1);
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
			g2d.setColor(ColorChooser.invert(getBackground()));
			g2d.drawOval(MainFrame.mouseListener.mousePosition.x-15/2, MainFrame.mouseListener.mousePosition.y-15/2, 15, 15);
		}
		
		//Temporary line
		if(tb.dirOutputIsDown() && dragDirectionalSpray) {
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(ColorChooser.invert(getBackground()));
			g2d.drawLine(initMouseX, initMouseY, MainFrame.mouseListener.mousePosition.x, MainFrame.mouseListener.mousePosition.y);
		}
		//Directional sprays
		for(DirectionalSprays spray : directionalSprays) {
			g2d.setStroke(new BasicStroke(1));
			g2d.setColor(ColorChooser.invert(getBackground()));
			if(!MainFrame.keyHandler.hDown) {
				
				RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			    g2d.setRenderingHints(rh);
			    
				g2d.drawOval( (int)(spray.posX-spray.radius/2), (int)(spray.posY-spray.radius/2),(int)(spray.radius),(int)( spray.radius));
				g2d.drawLine((int)(spray.posX), (int)(spray.posY), (int)(spray.endX), (int)(spray.endY));
			}
			
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
		//setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		if(MainFrame.mouseListener.leftMouseDown) requestFocus();
		if(MainFrame.keyHandler.bDown) setBackground(Color.white);
			else setBackground(Color.black);
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		for(int i = 0; i < collisionBoxes.size(); i++) {
			Box b = collisionBoxes.get(i);
			boolean isHovering = b.getBounds().contains(MainFrame.mouseListener.mousePosition);
			if(isHovering && !MainFrame.keyHandler.ctrlDown) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				if(MainFrame.mouseListener.leftMouseDown && !MainFrame.keyHandler.ctrlDown){
					cluster.removeAll();
					haltCluster = true;
					// OPEN A NEW WINDOW
					if(settings != null)
						settings.dispose();
					settings = new PhysicsObjectSettings("Box settings", b);
				} 
				else{
					haltCluster = false;
				}
			}
			else{
				if(!MainFrame.mouseListener.leftMouseDown)
					haltCluster = false;
			}
			
			if(isHovering && MainFrame.keyHandler.ctrlDown || (movePhysicsObject && b.isMouseMove())){
				setCursor(new Cursor(Cursor.MOVE_CURSOR));
				if(MainFrame.mouseListener.leftMouseDown) {
					b.setMouseMove(true);
					movePhysicsObject = true;
					b.setX((int) (MainFrame.mouseListener.mousePosition.x-b.getWidth()/2));
					b.setY((int) (MainFrame.mouseListener.mousePosition.y - b.getHeight()/2));
				}else{movePhysicsObject = false;b.setMouseMove(false);}
			}
			

			b.goesOutOfBounds(new Rectangle(0,0, getWidth(), getHeight())); // WHY IS THIS HERE?
			for(int j = 1; j < collisionBoxes.size(); j++) {
				Box p = collisionBoxes.get(j);
				//p.goesOutOfBounds(p.getBounds());
				if(p.willCollide(b, p.getNextVelocityY(), b.getNextVelocityY())) {
					try{
						p.elasticCollision(b);
//						p.collide(b, p.getNextVelocityY());
					}catch(NullPointerException e){
//						 e.printStackTrace();
//						System.exit(0);
						//TODO; Fiks na j�vla erroren
					}finally {
						//System.exit(0);
					}
				}
			}
		}
		
		// Check for mouseinput regarding the directional sprays
		ArrayList<DirectionalSprays> tmpSpray = (ArrayList<DirectionalSprays>) directionalSprays.clone();
		for(DirectionalSprays spray : tmpSpray) {
			
			//Shit is bugged as fuck yo
//			if(spray.goesOutOfBounds(getBounds()) ) {
//				directionalSprays.remove(spray);
//			}
			
			int mx = MainFrame.mouseListener.mousePosition.x;
			int my = MainFrame.mouseListener.mousePosition.y;
			double l = Math.sqrt(Math.pow(mx-spray.posX, 2) + Math.pow(my-spray.posY, 2));
			if(spray.radius/2 > l && !MainFrame.keyHandler.hDown) { // This means you are currently hovering a sprays area
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				if(MainFrame.mouseListener.leftMouseDown) {
					if(settings != null)
						settings.dispose();
					settings = new PhysicsObjectSettings("Directional Spray", spray);
				}
			}
		}
		
		
		if(!haltCluster){
			if(!tb.isSprayCan()) {
				ParticleCluster tmp = new ParticleCluster(cluster.getParticleLimit(), cluster.getParticlesPerSecond(), this);
				cluster = tmp;
			}
			else {
				cluster.setColor(MainFrame.getRightPanel().getColorChooser().getColor());
				cluster.update(dt);
			}
			for(DirectionalSprays ds : directionalSprays) {
				ds.update(dt);
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
					initMouseY, 25, 
					MainFrame.mouseListener.mousePosition.x,
					MainFrame.mouseListener.mousePosition.y,
					this);
			directionalSprays.add(spray);
			System.out.println(spray);
			tb.setDirOutputIsSelected(false);
			dragDirectionalSpray = false;
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
		if(settings != null)
			settings.update(dt);
	}
	
	
	public ArrayList<Box> getCollisionBoxes() {
		return collisionBoxes;
	}
	
	public class DirectionalSprays extends PhysicsObject {
		private double posX;
		private double posY;
		private double radius;
		
		private double endX; //Direction of spray
		private double endY;
		
		private double angle;
		private double spray = 5;
		
		private ParticleCluster spraynpray;
		
		public DirectionalSprays(int posX, int posY, int radius, int endX, int endY, ParticleCanvas pc) {
			setX(posX);
			setY(posY);
			this.radius = radius;
			this.endX = endX;
			this.endY = endY;
			setGravity(false);
			
			setColor(MainFrame.getRightPanel().getColorChooser().getColor());
			
			angle = Math.toDegrees(Math.atan2(endY-posY, endX - posX));

			angle = Math.toRadians(angle);
//			if(angle < 0) angle+= 2*Math.PI;
			ArrayList<Point> dir = new ArrayList<Point>();
			dir.add(new Point(posX, posY));
			dir.add(new Point(endX, endY));
			
			spraynpray = new ParticleCluster(10000, 100, pc, posX, posY, dir);
			spraynpray.setDirectional(angle-Math.toRadians(spray), angle + Math.toRadians(spray));
			double length = Math.sqrt(Math.pow(posX-endX,2) + Math.pow(posY-endY, 2));
			spraynpray.setSpeed(length/10);
			spraynpray.setColor(getColor());
			spraynpray.setLineSpray(true);
		}
		
		public void update(double dt) {
			updatePosition(dt);
			if(isGravity() && getVelocityY() <= EnvironmentConstants.TERMINAL_VELOCITY) 
				updateSpeed(dt);
			
			spraynpray.setColor(getColor());
//			spraynpray.setGravity(isGravity());
//			spraynpray.setStatic(isStatic());
			spraynpray.setMinAngle(angle-Math.toRadians(spray));
			spraynpray.setMaxAngle(angle+Math.toRadians(spray));
			spraynpray.setStartX((int) getX());
			spraynpray.setStartY((int) getY());
			
			spraynpray.update(dt);
			
		}
		
		@Override
		public void updatePosition(double dt) {// dependant on speed
			setX(getX() + getVelocityX()*30/MainFrame.updater.actualFPS);//*dt; // NOTE: I made the program in regards to 30 FPS, which is why it must be accounted for here, if fps != 30
			setY(getY() + getVelocityY()*30/MainFrame.updater.actualFPS);//*dt;
			endX+=getVelocityX()*30/MainFrame.updater.actualFPS;
			endY+=getVelocityY()*30/MainFrame.updater.actualFPS;
		}
		
		
		public ParticleCluster getSpraynpray(){
			return spraynpray;
		}
		
		public String toString() {
			return "PosX: " + posX + "\n" + "PosY: " + posY + "\n" + "Radius: " + radius + "\n" + "EndX " + endX + "\n" + "EndY " + endY + "\n" + "Angle: " + Math.toDegrees(angle);
		}

		@Override
		public boolean collided(Shape shape) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean outOfBounds(int x, int y) {
			// TODO Auto-generated method stub
			return false;
		}

		public double getSpray() {
			// TODO Auto-generated method stub
			return spray;
		}
		public void setSpray(double spray) {
			this.spray = spray;
		}
		
		@Override
		public void setX(double x) {
			super.setX(x);
			posX = x;
		}
		@Override
		public void setY(double y) {
			super.setY(y);
			posY = y;
		}
	}
	
}
