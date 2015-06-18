package ParticleEngine;

import graphics.MainFrame;
import graphics.ParticleCanvas;
import graphics.RightPanel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import constants.EnvironmentConstants;



public class ParticleCluster {

	private int particleLimit;
	private int particlesPerSecond;
	private long deltaSecond;
	private ArrayList<Particle> particles;
	private float particlesPerUpdate;
	private float lifetime;
	
	private ParticleCanvas caller;
	

	
	public ParticleCluster(int particleLimit, int particlesPerSecond, ParticleCanvas caller) {
		particles = new ArrayList<Particle>();
		this.particleLimit = particleLimit;
		this.particlesPerSecond = particlesPerSecond;
		this.caller = caller;
		particlesPerUpdate =  (float) particlesPerSecond /MainFrame.FPS;
		lifetime = (particleLimit/particlesPerSecond)*1000;
		System.out.println(lifetime);

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
		int cycles = 1;

		while( particles.size() < particleLimit && caller.mouseDown && cycles <= particlesPerUpdate){// && particlesPerSecond < deltaSecond) { SWAP WHILE with it to remove burst
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
		ArrayList<Particle> tmpParticles = new ArrayList<Particle>();
		for(Particle p : particles) {
			p.update(dt);
			if(p.life < lifetime-dt) {
				tmpParticles.add(p);
			}else {
				p = null;
			}
		}
		particles = tmpParticles;
		
	}
	
	
	public ArrayList<Particle> getParticles() {
		return particles;
	}

}