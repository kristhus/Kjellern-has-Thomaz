package graphics;

import interfaces.Drawable;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
   	 		g.setColor(b.color);
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
		if(tb.dirOutputIsDown()) {
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(Color.white);
			g2d.drawOval(MainFrame.mouseListener.mousePosition.x, MainFrame.mouseListener.mousePosition.y, 15, 15);
		}
		for(DirectionalSprays spray : directionalSprays) {
			g2d.setStroke(new BasicStroke(1));
			g2d.setColor(Color.white);
			g2d.drawOval(spray.posX, spray.posY, spray.radius, spray.radius);
			
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
			cluster.update(dt);
			for(DirectionalSprays ds : directionalSprays) {
				ds.getSpraynpray().update(dt);
			}
			
			
			for(Box b : collisionBoxes)
				b.update(dt);
		}
		if(tb.dirOutputIsDown()) {
			if(MainFrame.mouseListener.leftMouseDown) {
				directionalSprays.add(new DirectionalSprays(
						MainFrame.mouseListener.mousePosition.x,
						MainFrame.mouseListener.mousePosition.y, 15, 
						MainFrame.mouseListener.mousePosition.x+15,
						MainFrame.mouseListener.mousePosition.y+15,
						this));
				tb.setDirOutputIsSelected(false);
			}
			//tb.setDirOutputIsSelected(false);
			//Graphics2D g2d = (Graphics2D) g;
			//g2d.setStroke(new BasicStroke(2));
			//g2d.setColor(Color.white);
			//g2d.drawOval(MainFrame.mouseListener.mousePosition.x, MainFrame.mouseListener.mousePosition.y, 15, 15);
		}
		
		if(tb.isSprayCan()) {
			for(int i = 0; i < collisionBoxes.size(); i++) {
				Box b = collisionBoxes.get(i);
				if(b.getBounds().contains(MainFrame.mouseListener.mousePosition)) {
					b.color = Color.green;
					if(MainFrame.mouseListener.leftMouseDown){
						b.color = Color.red;
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
					b.color = Color.cyan;
				}
				b.goesOutOfBounds(new Rectangle(0,0, getWidth(), getHeight()));
				for(int j = 1; j < collisionBoxes.size(); j++) {
					Box p = collisionBoxes.get(j);
					p.goesOutOfBounds(p.getBounds());
					if(p.willCollide(b, p.getNextVelocityY(), 0)) {
						try{
							p.collide(b, p.getNextVelocityY());
						}catch(NullPointerException e){
							//				e.printStackTrace();
							//TODO; Fiks na j�vla erroren
						}finally {
							//System.exit(0);
						}
					}
				}
				if(settings != null)
					settings.update(dt);
			}
		} else {cluster = new ParticleCluster(200000, 10000, this); }
		
		if(MainFrame.mouseListener.btnFromSource) {
			if(tmpBox == null)
				tmpBox = new Box(MainFrame.mouseListener.mousePosition.x, MainFrame.mouseListener.mousePosition.y, 30,30);
			MainFrame.mouseListener.takenAction = false;
//			tmpBox.setX( - (MainFrame.mouseListener.externalSource.getLocation().x - getLocation().x));
			int differenceX = getLocationOnScreen().x-MainFrame.mouseListener.externalSource.getLocationOnScreen().x;
			int differenceY = getLocationOnScreen().y-MainFrame.mouseListener.externalSource.getLocationOnScreen().y;
			tmpBox.setX(MainFrame.mouseListener.mousePosition.x-differenceX);
			tmpBox.setY(MainFrame.mouseListener.mousePosition.y-differenceY);
//			System.out.println(tmpBox);
//			MainFrame.mouseListener.btnFromSource = false;
//			collisionBoxes.add(new Box(MainFrame.mouseListener.mousePosition.x, MainFrame.mouseListener.mousePosition.y, 30,30));
//			System.out.println(collisionBoxes);
			MainFrame.mouseListener.waitForRelease = true;
		}else if(tmpBox != null && !MainFrame.mouseListener.takenAction) {
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
		
		private ParticleCluster spraynpray;
		
		public DirectionalSprays(int posX, int posY, int radius, int endX, int endY, ParticleCanvas pc) {
			this.posX = posX;
			this.posY = posY;
			this.radius = radius;
			this.endX = endX;
			this.endY = posY;
			
			spraynpray = new ParticleCluster(10000, 1000, pc, posX, posY);
		}
		
		public ParticleCluster getSpraynpray(){
			return spraynpray;
		}
		
	}
	
}
