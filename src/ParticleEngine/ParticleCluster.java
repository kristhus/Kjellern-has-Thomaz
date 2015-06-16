package ParticleEngine;

import java.awt.Graphics;
import java.util.ArrayList;



public class ParticleCluster {

	private int particleLimit;
	private int particlesPerSecond;
	private float deltaSecond;
	public ArrayList<Particle> particles;

	public ParticleCluster(int particleLimit, int particlesPerSecond) {
		particles = new ArrayList<Particle>();
		this.particleLimit = particleLimit;
		this.particlesPerSecond = particlesPerSecond;
	}

	public void draw(Graphics g) {
		//For each particle in the cluster, call its drawmethod
		for(Particle p : particles) {
			p.draw(g);
		}
	}

	public void update(long dt) {
		deltaSecond += dt;
		if( particles.size() < particleLimit && particlesPerSecond < deltaSecond) {
	//		particles.add(new Particle()); //Use slider or a seed
		}
		for(Particle p : particles) {
			p.update(dt);
		}
	}

}