package particleEngine;

import graphics.MainFrame;
import graphics.ParticleCanvas;
import graphics.RightPanel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import physics.PhysicsObject;
import constants.EnvironmentConstants;



public class ParticleCluster {

	private int particleLimit;
	private int particlesPerSecond;
	private ArrayList<Particle> particles;
	private float particlesPerUpdate;
	private double lifetime;
	
	private double deltaTimeDecimal;
	
	private ParticleCanvas caller;
	

	
	public ParticleCluster(int particleLimit, int particlesPerSecond, ParticleCanvas caller) {
		particles = new ArrayList<Particle>();
		this.particleLimit = particleLimit;
		this.particlesPerSecond = particlesPerSecond;
		this.caller = caller;
		particlesPerUpdate =  (float) particlesPerSecond /MainFrame.FPS;
		lifetime = (particleLimit/particlesPerSecond)*100;  //Consider making this a parameter, so particle lifetime can be decided (should be 1000, error somewhere else!)
	}
	
	public Particle seeded() {
		int red = (int)(Math.random()*255); 
		int green = (int)(Math.random()*255); 
		int blue = (int)(Math.random()*255); 
		Color c = new Color(red, green, blue);
		int dim = 1 + (int)(Math.random()*10);
		return new Particle(dim, dim, null, c, caller.mouseX, caller.mouseY, true);
	}

	public void update(double dt) {
		int cycles = 1;
		deltaTimeDecimal += particlesPerUpdate-(int) particlesPerUpdate;
		while( particles.size() < particleLimit && caller.mouseDown && cycles <= particlesPerUpdate+deltaTimeDecimal){// && particlesPerSecond < deltaSecond) { 
			Particle p;
			if(MainFrame.getRightPanel().getColorChooser().isSeedOn()){
				p = seeded();				
			}else {
				p = new Particle(2, 2, null, MainFrame.getRightPanel().getColorChooser().getColor(), caller.mouseX, caller.mouseY, true, Particle.FLAME);
			}
			particles.add(p); 
			cycles ++;
		}
		
		// FIX THIS PART    Add initialSpeed for drag and release effect???
		// FIX: REMOVE THE AMOUNT OF PARTICLES TO BE ADDED DURING THIS UPDATE
		// Iterate from the last element, (last added) maybe
		ArrayList<Particle> tmpParticles = new ArrayList<Particle>();
		boolean sublist = false;
		int sublistIndex = 0;
		for(Particle p : particles) {
			//TODO: IF OUT OF BOUNDS DO SOMETHING
//			if (p.getBounds().get){
//				
//			}
			p.update(dt);
			if(p.life < lifetime && particles.size() < particleLimit) {
				tmpParticles.add(p);
			}else{
				sublist = true;
			}
		}
		if(sublist && deltaTimeDecimal+particlesPerUpdate < particles.size()) {
			particles = new ArrayList<Particle>(particles.subList((int) (deltaTimeDecimal+particlesPerUpdate), particles.size())); //Correctly removes particles
		}else{
			particles = tmpParticles;
		}
		
		for(Particle p : particles) {
			// Move this code beneath if(p.life < lifetime && particles.size() < particleLimit)
			for(PhysicsObject b : caller.getCollisionBoxes()) {
				if(p.willCollide(b, p.getNextVelocityY(), 0)) {
					try{
						p.collide(b, p.getNextVelocityY());
						System.out.println("________________");
					}catch(NullPointerException e){
						e.printStackTrace();
						
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
	
	
	public ArrayList<Particle> getParticles() {
		return particles;
	}

}