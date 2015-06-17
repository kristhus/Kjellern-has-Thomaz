package ParticleEngine;

import graphics.MainFrame;
import graphics.ParticleCanvas;
import graphics.RightPanel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;



public class ParticleCluster {

	private int particleLimit;
	private int particlesPerSecond;
	private int deltaSecond;
	private ArrayList<Particle> particles;
	
	private ParticleCanvas caller;
	
	public ParticleCluster(int particleLimit, int particlesPerSecond, ParticleCanvas caller) {
		particles = new ArrayList<Particle>();
		this.particleLimit = particleLimit;
		this.particlesPerSecond = particlesPerSecond;
		this.caller = caller;

	}
	
	public Particle seeded() {
		int red = (int)(Math.random()*255); 
		int green = (int)(Math.random()*255); 
		int blue = (int)(Math.random()*255); 
		Color c = new Color(red, green, blue);
		int dim = 1 + (int)(Math.random()*10);
		return new Particle(dim, dim, null, c, caller.mouseX, caller.mouseY, true);
	}

	public void update(long dt) {
		float lifetime = (particleLimit/particlesPerSecond)*1000;
		deltaSecond += dt;
		if(deltaSecond > 400) 
			deltaSecond = 0;
		if( particles.size() < particleLimit && caller.mouseDown){// && particlesPerSecond < deltaSecond) {
			Particle p;
			if(MainFrame.getRightPanel().getColorChooser().isSeedOn()){
				p = seeded();				
			}else {
				p = new Particle(5, 5, null, MainFrame.getRightPanel().getColorChooser().getColor(), caller.mouseX, caller.mouseY, true);
			}
			particles.add(p); 
		}
		else if(particles.size() >= particleLimit) {
			particles.remove(0);
		}
		ArrayList<Particle> tmpParticles = new ArrayList<Particle>();
		for(Particle p : particles) {
			p.update(dt);
			if(p.life < lifetime) {
				tmpParticles.add(p);
			}
		}
		particles = tmpParticles;
	}
	
	
	public ArrayList<Particle> getParticles() {
		return particles;
	}

}