package particleEngine;

import graphics.MainFrame;
import graphics.ParticleCanvas;
import graphics.RightPanel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import physics.PhysicsObject;
import constants.EnvironmentConstants;
import data.ConcurrentArrayList;



public class ParticleCluster extends PhysicsObject {

	private int particleLimit;
	private int particlesPerSecond;
	private ConcurrentArrayList<Particle> particles;
	private float particlesPerUpdate;
	private double lifetime;
	
	private double deltaTimeDecimal;
	
	
	private ParticleCanvas caller;
	private boolean isStatic;
	private int startX;
	private int startY;
	
	private boolean directional;
	private double maxAngle;
	private double minAngle;
	private double speed;
	private Color color;
	
	private boolean lineSpray; // If this is true, the particles will be sprayed out from the directional line. ONLY TO BE USED WITH DIRECTIONAL SPRAY
							  // Good for making rain and dust
	
	private ArrayList<Point> dir;
	
	public ParticleCluster(int particleLimit, int particlesPerSecond, ParticleCanvas caller) {
		this.isStatic = isStatic;
		particles = new ConcurrentArrayList<Particle>();
		this.particleLimit = particleLimit;
		this.particlesPerSecond = particlesPerSecond;
		this.caller = caller;
		particlesPerUpdate =  (float) particlesPerSecond /MainFrame.FPS;
		lifetime = (particleLimit/particlesPerSecond)*100;  //Consider making this a parameter, so particle lifetime can be decided (should be 1000, error somewhere else!)
	}
	
	public ParticleCluster(int particleLimit, int particlesPerSecond, ParticleCanvas caller, int startX, int startY, ArrayList<Point> dir) {
		this(particleLimit, particlesPerSecond, caller);
		isStatic = true;
		this.startX = startX;
		this.startY = startY;
		this.dir = dir;
		
	} 
	
	
	public Particle seeded() {
		int red = (int)(Math.random()*255); 
		int green = (int)(Math.random()*255); 
		int blue = (int)(Math.random()*255); 
		Color c = new Color(red, green, blue);
		int dim = 1 + (int)(Math.random()*10);
		if(!isStatic)
			return new Particle(dim, dim, null, c,  MainFrame.mouseListener.mousePosition.x, MainFrame.mouseListener.mousePosition.y, true);
		else
			return new Particle(dim, dim, null, c,  startX, startY, false);

	}

	public void update(double dt) {
		int cycles = 1;
		deltaTimeDecimal += particlesPerUpdate-(int) particlesPerUpdate;
		while( particles.size() < particleLimit && (MainFrame.mouseListener.leftMouseDown || isStatic) && cycles <= particlesPerUpdate+deltaTimeDecimal){// && particlesPerSecond < deltaSecond) { 
			Particle p;
			if(MainFrame.getRightPanel().getColorChooser().isSeedOn()){
				p = seeded();				
			}else {
				if(!directional) { // Create particle with initial speed vector
					if(!isStatic)
						p = new Particle(1, 1, null, getColor(), MainFrame.mouseListener.mousePosition.x, MainFrame.mouseListener.mousePosition.y, true);
					else
						p = new Particle(1, 1, null, getColor(), startX, startY, true);
				}
				else {
					if(!isStatic)
						p = new Particle(1, 1, null, getColor(), MainFrame.mouseListener.mousePosition.x, MainFrame.mouseListener.mousePosition.y, minAngle, maxAngle, speed, dir);
					else {
						if(!lineSpray)
							p = new Particle(1, 1, null, getColor(), startX, startY, minAngle, maxAngle, speed, dir);
						else {
							p = new Particle(1, 1, null, getColor(), MainFrame.mouseListener.mousePosition.x, MainFrame.mouseListener.mousePosition.y, speed, dir);
						}
					}
				}
			}
			particles.add(p); 
			cycles ++;
		}
		
		// FIX THIS PART    Add initialSpeed for drag and release effect???
		// FIX: REMOVE THE AMOUNT OF PARTICLES TO BE ADDED DURING THIS UPDATE
		// Iterate from the last element, (last added) maybe
		ConcurrentArrayList<Particle> tmpParticles = new ConcurrentArrayList<Particle>();
		boolean sublist = false;
		Iterator<Particle> tmp = getParticles().iterator();
		boolean oob = false;
		while(tmp.hasNext()) {
			//TODO: IF OUT OF BOUNDS DO SOMETHING
			Particle p = tmp.next();
			oob = p.goesOutOfBounds(new Rectangle(0,0,caller.getWidth(), caller.getHeight()));
			
			p.setGravity(isGravity());
			p.setStatic(isStatic());
			p.update(dt);
			if(oob){ // TODO: Make user able to decide what to do when oob. As of now, they get deleted
				// Do nothing swagger
			}
			else if(p.life < lifetime && particles.size() < particleLimit) {
				tmpParticles.add(p);
			}else {
				sublist = true;
			}
		}
		if(sublist && deltaTimeDecimal+particlesPerUpdate < particles.size()) { // UUh, what is this?
			setParticles(new ConcurrentArrayList<Particle>(particles.subList((int) (deltaTimeDecimal+particlesPerUpdate), particles.size()))); //Correctly removes particles
		}else{
			setParticles(tmpParticles);
		}
		
		Iterator<Particle> tmp2 = getParticles().iterator();
		while(tmp2.hasNext()) { // This part iterates every particle, and checks every collidable for collision
			Particle p = tmp2.next();
			// Move this code beneath if(p.life < lifetime && particles.size() < particleLimit)
			for(PhysicsObject b : caller.getCollisionBoxes()) {
//				if(p.getBounds().intersects(b.getBounds())) {
				if(p.willCollide(b, p.getNextVelocityY(), b.getNextVelocityY())) {
					try{
							p.elasticCollision(b);
//							p.collide(b, p.getNextVelocityY());
					}catch(NullPointerException e){
//						e.printStackTrace();
						//TODO; Fiks na jævla erroren
					}finally {
						//System.exit(0);
					}
				}
			}
		}
		
		if(deltaTimeDecimal+particlesPerUpdate >=1) {
			deltaTimeDecimal=particlesPerUpdate;
		}
	}
	
	
	public ConcurrentArrayList<Particle> getParticles() { // Should probably synchronize all access to particles because poop
		return particles;
	}
	public void setParticles(ConcurrentArrayList<Particle> particles) {
		this.particles = particles;
	}

	public void removeAll() {
		particles = new ConcurrentArrayList<>();
	}
	
	public void setMinAngle(double d) {
		minAngle = d;
	}
	public void setMaxAngle(double d) {
		maxAngle = d;
	}
	public void setDirectional(double minAngle, double maxAngle) {
		directional = true;
		this.minAngle = minAngle;
		this.maxAngle = maxAngle;
	}

	public void setSpeed(double i) {
		directional = true;
		speed = i;
		
	}

	public int getParticleLimit() {
		return particleLimit;
	}

	public void setParticleLimit(int particleLimit) {
		this.particleLimit = particleLimit;
		lifetime = (particleLimit/particlesPerSecond)*100;  //Consider making this a parameter, so particle lifetime can be decided (should be 1000, error somewhere else!)
	}

	public int getParticlesPerSecond() {
		return particlesPerSecond;
	}

	public void setParticlesPerSecond(int particlesPerSecond) {
		this.particlesPerSecond = particlesPerSecond;
		particlesPerUpdate =  (float) particlesPerSecond /MainFrame.FPS;
		lifetime = (particleLimit/particlesPerSecond)*100;  //Consider making this a parameter, so particle lifetime can be decided (should be 1000, error somewhere else!)
	}

	public double getLifetime() {
		return lifetime;
	}

	public void setLifetime(double lifetime) {
		this.lifetime = lifetime;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
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

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}
	
	public void setLineSpray(boolean lineSpray) {
		this.lineSpray = lineSpray;
	}
	
}