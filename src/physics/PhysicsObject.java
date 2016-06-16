package physics;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Vector;

import moveable.Collidable;
import constants.EnvironmentConstants;

public abstract class PhysicsObject implements Collidable{ //TODO: Try to implement some type of shape/abstract
	
	//Should be implemented by a ApplicationObject / GameObject etc.
	
	private double density;
	private double weight = 0.01;
	public double x;
	private double y;
	private double velocityX;
	private double velocityY;
	private double dvX;
	private double dvY;
	private double width;
	private double height;
	private Color color;
	
	private double bounciness = 1.0; // This may or may not be permanent, as bounce is an interaction including 
									//  thermodynamics, potential, kinetic energy and several other factors, 
								   //   which takes way to long to do in a sensible amount of time
								  //	Bounciness 1, means that all energy is conserved in the colliding object, 0 the oppposite.
								 //		With bounciness < 1, no energy is converted, but entropies its ass outta there

	
	private boolean gravity; // Is this object affected by gravity
	private boolean isStatic; // Unmovable (e.g. glued to the world)
	
	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	private ArrayList<Force> forces;//A list with the forces influencing this object
	
	public PhysicsObject() {
		dvX = 0;
		dvY = EnvironmentConstants.GRAVITY/10;
	}
	
	public double getKineticEnergy() {
		return 0.5*weight*Math.pow(Math.abs(velocityX)+Math.abs(velocityY), 2);
	}
	public double getPotentialEnergy(double distance) {
		return weight*EnvironmentConstants.GRAVITY*distance;
	}
	public double getMomentum() {
		return weight*(Math.abs(velocityX)+Math.abs(velocityY));
	}
	
	
	// TODO: FIKS HER, kjører disse med feil referanse på variabler for ob.v2 (se på velocityX og weight, lokale referanser..)
	public double getMomentumCollisionX(PhysicsObject obj) { 
		double u1 = velocityX; double m1 = weight; double m2 = obj.getWeight(); double u2 = obj.getVelocityX();
		double vxAfter = (u1*(m1-m2)+2*m2*u2)/(m1+m2);
		return vxAfter;
	}
	public double getMomentumCollisionY(PhysicsObject obj) { 
		double u1 = velocityY; double m1 = weight; double m2 = obj.getWeight(); double u2 = obj.getVelocityY();
		double vyAfter = (u1*(m1-m2)+2*m2*u2)/(m1+m2);
		return vyAfter;
	}
	
	/**	Calculates changes to both objects when they collide
	 *	
	 * @param obj The object to collide with
	 */
	public Rectangle.Float getBoundsFloat() {
		return new Rectangle.Float((float)getX(),(float) getY(), (float)getWidth(), (float)getHeight());
	}
	public Rectangle getBounds() {
		return new Rectangle((int)getX(), (int)getY(), (int) getWidth(),(int) getHeight());
	}
	public ArrayList<Point.Float> getPointsInBounds() {
		ArrayList<Point.Float> pib = new ArrayList<Point.Float>();
		Rectangle.Float bounds = getBoundsFloat();
		Point.Float topL = new Point.Float(bounds.x, bounds.y);
		Point.Float topR = new Point.Float(bounds.x + bounds.width, bounds.y);
		Point.Float botL = new Point.Float(bounds.x, bounds.y + bounds.height);
		Point.Float botR = new Point.Float(bounds.x + bounds.width, bounds.y + bounds.height);
		pib.add(topL); pib.add(topR); pib.add(botL); pib.add(botR);
		return pib;
	}
	
	public void updatePosition(double dt) {// dependant on speed
		x += velocityX;//*dt;
		y += velocityY;//*dt;
	}
	public void updateSpeed(double dt) { // dependant on acceleration
		velocityX += dvX;//*dt;
		velocityY += dvY;//*dt;
	}
	public void updateAcceleration(double dt) { // dependant on Forces
		
	}
	
	public void elasticCollision(PhysicsObject obj) {
		
//		if(obj.getVelocityX() == obj.getVelocityY() && obj.getVelocityX() == 0) {
//			// Formula for moving object colliding with target initially at rest
//			// Initially moving: v1 = u1 * (m1-m2)/(m1+m2) 
//			double vX1 = velocityX* (weight-obj.getWeight() / (weight+obj.getWeight()));
//			double vY1 = velocityY* (weight-obj.getWeight() / (weight+obj.getWeight()));
//			// Target at rest:   u2 = 2*m1*v1/(m1+m2)
//			double vX2 = 2*getWeight()*velocityX/(getWeight() + obj.getWeight());
//			double vY2 = 2*getWeight()*velocityY/(getWeight() + obj.getWeight());
//		}
//		else {
			double vX1 = velocityX* (weight-obj.getWeight() / (weight+obj.getWeight())) + obj.getVelocityX()*(2*obj.getWeight())/(getWeight()+obj.getWeight());
			double vY1 = velocityY* (weight-obj.getWeight() / (weight+obj.getWeight())) + obj.getVelocityY()*(2*obj.getWeight())/(getWeight()+obj.getWeight());;
			// Target at rest:   u2 = 2*m1*v1/(m1+m2)
			double vX2 = 2*getWeight()*velocityX/(getWeight() + obj.getWeight()) + (obj.getWeight()-getWeight())/(getWeight() + obj.getWeight());
			double vY2 = 2*getWeight()*velocityY/(getWeight() + obj.getWeight()) + (obj.getWeight()-getWeight())/(getWeight() + obj.getWeight());;
//		}
		
		setVelocityX(vX1); 
		setVelocityY(vY1); 
		
		obj.setVelocityX(vX2 * (getBounciness() + obj.getBounciness())/2);
		obj.setVelocityY(vY2 * (getBounciness() + obj.getBounciness())/2);
		
	}
	
	
	public void collide(PhysicsObject obj, double yModifier) { //TODO: Should have a shape of some sorts, and this method should be remade in regards to that
		//TODO: velocity is the vector to be used to figure out if 
		
		/*
		if(velocityX == 0 && velocityY == 0) {
			System.out.println("NO SPEED");
			float vX = (float) getMomentumCollisionX(obj);
			float vY = (float) getMomentumCollisionY(obj);
			System.out.println("vX: " + vX);
			System.out.println("vY: " + vY);
			System.out.println("================================");
			return new Point.Float(vX, vY);
		}
		*/
		Point.Float thisPoint = new Point.Float((float) (getX()), (float) (getY()));
		for(Point.Float p : getPointsInBounds()) {
			if(distFromPointToRect(obj.getBoundsFloat(), p) < distFromPointToRect(obj.getBoundsFloat(), thisPoint)) {
				thisPoint = p;
			}
		}
		Point.Float thisTowards = getNextPosition(thisPoint.getX(), thisPoint.getY(), getVelocityX(), getVelocityY() + yModifier);
		Point.Float A = new Point.Float((float) obj.getX(), (float) obj.getY());
		Point.Float B = new Point.Float((float)(obj.getX() + obj.getWidth()), (float) obj.getY());
		Point.Float C = new Point.Float((float) obj.getX(), (float)(obj.getY() + obj.getHeight()));
		Point.Float D = new Point.Float((float)(obj.getX() + obj.getWidth()), (float)(obj.getY() + obj.getHeight()));
		ArrayList<Point.Float> edges = new ArrayList<Point.Float>();
		edges.add(A);edges.add(B);edges.add(C);edges.add(D);
		Double minDist = thisPoint.distance(A);
		Point.Float edgeA = A;
		for(Point.Float p : edges) {
			if(thisPoint.distance(p) < minDist) {
				edgeA = p;
			}
		}
		edges.remove(edgeA);
		Point.Float edgeB = edges.get(0);
		LineInPlane thisLine = new LineInPlane(thisPoint, thisTowards);
		for(Point.Float p : edges) {
			LineInPlane pLine = new LineInPlane(thisPoint, p);
			if(thisLine.intersectsLine(pLine)) {
				if(p.distance(thisPoint) < edgeB.distance(thisPoint))
					edgeB = p;
			}
		}
		if(edgeA.x+edgeA.y > edgeB.x+edgeB.y) {
			Point.Float tmp = edgeA;
			edgeA = edgeB;
			edgeB = tmp;
		}
		LineInPlane objectLine = new LineInPlane(edgeA, edgeB);	
		LineInPlane intersectionLine = new LineInPlane(thisPoint, thisTowards);	
		Point intersection = findIntersectedPoint(objectLine, intersectionLine);
		
		
//		System.out.println("this:    " + thisPoint);
//		System.out.println("EdgeA:   "  + edgeA);
//		System.out.println("EdgeB:   "  + edgeB);
//		System.out.println("thisT:   " + thisTowards);
//		System.out.println("inter:   " + intersection);
//		System.out.println("vx   :   " + getVelocityX());
//		System.out.println("vy   :   " + getVelocityY());
		double b = thisPoint.distance(intersection); // thisToIntersection
		//double a = Math.min(thisPoint.distance(edgeA),thisPoint.distance(edgeB)); //thisToA //??????
		double a = thisPoint.distance(edgeA);
		double c = edgeA.distance(intersection); //aToIntersection

		double alpha = Math.acos((Math.pow(b,  2) + Math.pow(c,  2) - Math.pow(a,  2))/(2*b*c));
		double theta = Math.PI-2*alpha; //Using rads
		
		
		// This part is wrong, but i'm sick and tired of collision :v
		double tmpSpeedX = getVelocityX();
		double tmpSpeedY = getVelocityY();
		if(velocityY > 0) {
			if(alpha < theta) 							// Only for collision where y must be reversed
				tmpSpeedX = getVelocityX()*-1;
			else
				tmpSpeedY = getVelocityY()*-1;
		}else {
			if(alpha > theta) 							// Only for collision where y must be reversed
				tmpSpeedX = getVelocityX()*-1;
			else
				tmpSpeedY = getVelocityY()*-1;
		}
		//Point.Float speed = new Point.Float((float) tmpSpeedX, (float) tmpSpeedY);
		
		//setVelocityX(tmpSpeedX);
		//setVelocityY(tmpSpeedY);
		
		//return speed;
		double setX = getMomentumCollisionX(obj);
		double setY = getMomentumCollisionY(obj);
		obj.setVelocityX(obj.getMomentumCollisionX(this));
		obj.setVelocityY(obj.getMomentumCollisionY(this));
//		System.out.println("setX: " + setX);
//		System.out.println("setY: " + setY);
		setVelocityX(-setX);
		setVelocityY(-setY);
//		System.out.println("vx2   :   " + getVelocityX());
//		System.out.println("vy2   :   " + getVelocityY());
//		System.out.println("ob.vx2:   " + obj.getVelocityX());
//		System.out.println("ob.vy2:   " + obj.getVelocityY());
//		System.out.println("===================================");
		/*
		System.out.println("this:    " + thisPoint);
		System.out.println("EdgeA:   "  + edgeA);
		System.out.println("EdgeB:   "  + edgeB);
		System.out.println("A: " + a + "    :    " + "B: " + b + "    :    " + "C: " + c);
		System.out.println("thisT:   " + thisTowards);
		System.out.println("inter:   " + intersection);
		System.out.println("alpha: " + alpha);
		System.out.println("theta: " + theta);
		System.out.println("vx2   :   " + getVelocityX());
		System.out.println("vy2   :   " + getVelocityY());
		System.out.println("===================================");
		*/
	}
	
	
	public Point findIntersectedPoint(LineInPlane l1, LineInPlane l2){ //Thanks to Tom at Community.Oracle
		if(!l1.intersectsLine(l2))
			return null;
	     double px = l1.getX1(),
	             py = l1.getY1(),
	             rx = l1.getX2()-px,
	             ry = l1.getY2()-py;
	      double qx = l2.getX1(),
	             qy = l2.getY1(),
	             sx = l2.getX2()-qx,
	             sy = l2.getY2()-qy;
	      double determ = sx*ry - sy*rx;
	    	  double z = (sx*(qy-py)+sy*(px-qx))/determ;
	          if (z==0 ||  z==1) return null;  // intersection at end point!
	          return new Point((int)(px+z*rx), (int)(py+z*ry));
	}
	public double getBuoyancyForce(double fluidDensity) {
		return (weight-fluidDensity*width*height)*EnvironmentConstants.GRAVITY;
	}
	public double calculateDistance(Point p1, Point p2) {
		return p1.distance(p2);
	}
	public double calculateDistance(int x1, int y1, int x2, int y2){
		return calculateDistance(new Point(x1, y1), new Point(x2, y2));
	}
	public double getUniversalGravitation(PhysicsObject obj, Point p1, Point p2){
		return EnvironmentConstants.GRAVITATIONAL_CONSTANT*weight*obj.getWeight()/Math.pow(calculateDistance(p1, p2),2);
	}
	
	// GETTERS AND SETTERS
	
	public void addForce(Force force){
		forces.add(force);
	}
	public void removeForce(Force force){
		forces.remove(force);
	}
	public void clearForces(){
		forces.clear();
		forces = new ArrayList<Force>();
	}
	public double getDensity() {
		return density;
	}
	public void setDensity(double density) {
		this.density = density;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getVelocityX() {
		return velocityX;
	}
	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}
	public double getVelocityY() {
		return velocityY;
	}
	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public ArrayList<Force> getForces() {
		return forces;
	}
	public void setForces(ArrayList<Force> forces) {
		this.forces = forces;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @param speedModifier If run on variable intervals (update(dt~) etc.) speedModifier = 1 will not effect calculation
	 * @return
	 */
	
	private double distFromPointToRect(Rectangle.Float rect, Point.Float p) {
		double dx = Math.max(rect.getMinX()- p.x, p.x - rect.getMaxX());
		double dy = Math.max(rect.getMinY() - p.y, p.y - rect.getMaxY());
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	private Point getNextPosition(){ // getnext speed THEN get next position
		return new Point((int)(getX()+velocityX), (int) (getY()+velocityY));
	}
	
	public Point2D.Float getNextPosition(double x, double y, double speedX, double speedY) {
		//TODO: Create new class; FloatPoint which extends Point
		Point2D.Float p = new Point2D.Float(
		          (float)(x+speedX), (float) (y+speedY));
		return p;
	}
	
	public double getNextVelocityY() {
		/*
		double buoyancyY = getBuoyancyForce(EnvironmentConstants.AIR_DENSITY)/100;
		if(getVelocityY() > 0) {
			buoyancyY = Math.abs(buoyancyY)*-1;
		}
		System.out.println(buoyancyY);
		*/
		return getVelocityY()+EnvironmentConstants.GRAVITY/10 ;
	}
	
	public boolean willCollide(PhysicsObject obj, double speedModifier1, double speedModifier2){
		Point2D.Float p1 = getNextPosition(x, y, getVelocityX(), getVelocityY() + speedModifier1);
		Point2D.Float p2 = getNextPosition(obj.getX(), obj.getY(),obj.getVelocityX(), obj.getVelocityY() + speedModifier2);
		Rectangle2D.Float r1 = new Rectangle2D.Float();
		r1.setRect(p1.getX(), p1.getY(), width, height);
		Rectangle2D.Float r2 = new Rectangle2D.Float();
		r2.setRect(p2.getX(), p2.getY(), obj.getWidth(), obj.getHeight());
		return r1.intersects(r2);
	}
	
	public boolean goesOutOfBounds(Rectangle bounds) {
		// TODO Auto-generated method stub
		int boundsX = bounds.x;
		int boundsY = bounds.y;
		int boundsX2 = bounds.x+bounds.width;
		int boundsY2 = bounds.y+bounds.height;
		boolean oob = false;
		if(boundsX > getX()) {
			//OUT ON LEFT
			setVelocityX(getVelocityX()*-1);
			oob = true;
		}
		if(boundsX2 < getX() + getWidth()) {
			//OUT RIGHT
			setVelocityX(getVelocityX()*-1);
			oob = true;
		}
		if(boundsY > getY()) {
			//OUTSIDE UP
			setVelocityY(getVelocityY()*-1);
			oob = true;
		}
		if(boundsY2 < getY() + getHeight()){
			//OUT DOWN
			setVelocityY(getVelocityY()*-1);
			oob = true;
		}
		return oob;
		
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color colour) {
		this.color = colour;
	}

	public boolean isGravity() {
		return gravity;
	}

	public void setGravity(boolean gravity) {
		this.gravity = gravity;
	}

	public double getBounciness() {
		return bounciness;
	}

	public void setBounciness(double bounciness) {
		this.bounciness = bounciness;
	}
	
	
	
}
