package physics;

import graphics.MainFrame;

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
import data.Updater;

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
	
	private boolean mouseMove;
	
	private boolean colliding; // To fix update of speed when colliding with another object. (speed updates regardless)
	
	private ArrayList<PhysicsObject> attached = new ArrayList<PhysicsObject>();
	private PhysicsObject attachedTo;
	
	private double bounciness = 1.0; // This may or may not be permanent, as bounce is an interaction including 
									//  thermodynamics, potential, kinetic energy and several other factors, 
								   //   which takes way to long to do in a sensible amount of time
								  //	Bounciness 1, means that all energy is conserved in the colliding object, 0 the oppposite.
								 //		With bounciness < 1, no energy is converted, but entropies its ass outta there

	
	private boolean gravity = true; // Is this object affected by gravity
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
	
	
	// TODO: FIKS HER, kj�rer disse med feil referanse p� variabler for ob.v2 (se p� velocityX og weight, lokale referanser..)
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
		if(colliding) return;
		if(attachedTo != null) {
			x += attachedTo.getVelocityX()*30/MainFrame.updater.actualFPS;
			y += attachedTo.getVelocityY()*30/MainFrame.updater.actualFPS;
		}else {
			x += velocityX*(30/Updater.actualFPS) + Math.pow(30/Updater.actualFPS, 2)*getDvX()/2;
			y += velocityY*(30/Updater.actualFPS) + Math.pow(30/Updater.actualFPS, 2)*getDvY()/2;
//			x += velocityX*30/MainFrame.updater.actualFPS;//*dt; // NOTE: I made the program in regards to 30 FPS, which is why it must be accounted for here, if fps != 30
//			y += velocityY*30/MainFrame.updater.actualFPS;//*dt;
		}
	}
	public void updateSpeed(double dt) { 
		if(colliding) return;
		if(attachedTo != null) {
			velocityX += attachedTo.getDvX()*30/MainFrame.updater.actualFPS;//*dt;
			velocityY += attachedTo.getDvY()*30/MainFrame.updater.actualFPS;//*dt;
		}else {
			velocityX += dvX*30/MainFrame.updater.actualFPS;//*dt;
			velocityY += dvY*30/MainFrame.updater.actualFPS;//*dt;
		}
	}
	public void updateAcceleration(double dt) { // dependant on Forces
		
	}
	
	public void elasticCollision(PhysicsObject obj) {
		
		// A 'static' object isn't a physical phenomenon, but a simple representation of an object with an 'infinite' amount bigger mass
		// than the colliding object, neglecting the impact the nonstatic object will have on the static one. 
		// Setting a dummy weight big enough, will disregard this. (It is important it is high enough to remove any impact)
		double dummyWeight = weight;
		double objDummyWeight = obj.getWeight();
		if(isStatic()) 
			dummyWeight = 1000*weight; // Meh, this is accurate enough for now.
		else if(obj.isStatic())
			objDummyWeight = (obj.getWeight()*1000);
			
		
		double vX1 = velocityX* (dummyWeight-objDummyWeight / (dummyWeight+objDummyWeight)) + obj.getVelocityX()*(2*objDummyWeight)/(dummyWeight+objDummyWeight);
		double vY1 = velocityY* (dummyWeight-objDummyWeight / (dummyWeight+objDummyWeight)) + obj.getVelocityY()*(2*objDummyWeight)/(dummyWeight+objDummyWeight);;
		// Target at rest:   u2 = 2*m1*v1/(m1+m2)
		double vX2 = 2*dummyWeight*velocityX/(dummyWeight + objDummyWeight) - obj.getVelocityX()*(objDummyWeight-dummyWeight)/(dummyWeight + objDummyWeight);
		double vY2 = 2*dummyWeight*velocityY/(dummyWeight + objDummyWeight) - obj.getVelocityY()*(objDummyWeight-dummyWeight)/(dummyWeight + objDummyWeight);;
		
		// GOD F*KING DAMN IT , due to axis being fucking shit in programming, i have to reverse direction in certain situations:
		double angle = Math.toDegrees(Math.atan2(getY()-(obj.getY()+obj.getWidth()/2), getX() - (obj.getX()+obj.getWidth()/2)));
//		if(angle < 45.4 && angle > 44.6) {
//			System.out.println("Weight: " + dummyWeight +
//					"\nObjDummyWeight: " + objDummyWeight +
//					"\nvX1: " + velocityX + 
//					"\nvY1: " + velocityY +
//					"\nAngle: " + angle);
//		}
		if(angle <= 45 && angle >= -45){
			vY1 *= -1;
		}if(angle >45 && angle <= 135){
			vX1*=-1;
		}if((angle >=135 && angle < 180) || (angle <-135 && angle >=-180)){
			vY1*=-1;
		}if(angle <-45 && angle >=-135)
			vX1*=-1;
		
		double tpu = 1/MainFrame.updater.actualFPS; // Time per update (actual) for previous upd.
		
		
//		setX(  (intersection.x - x) + getVelocityX()*timeTakeni + getDvX()*Math.pow(timeTakeni,2)/2     );
//		setY(  (intersection.y - y) + getVelocityY()*timeTakeni + getDvY()*Math.pow(timeTakeni,2)/2     );
		
//		if(obj.isStatic()){
//			setX(intersection.x);
//			setY(intersection.y);
//		}
		
				if(!isStatic()) {
					setVelocityX(vX1 * (getBounciness() + obj.getBounciness())/2);
					setVelocityY(vY1 * (getBounciness() + obj.getBounciness())/2); 
				}
				if(!obj.isStatic()) {
					obj.setVelocityX(vX2 * (getBounciness() + obj.getBounciness())/2);
					obj.setVelocityY(vY2 * (getBounciness() + obj.getBounciness())/2);
				}
		
				Point intersection = pointOfIntersection(obj, getNextVelocityY());
				double di = Math.sqrt(Math.pow(intersection.x - getX(), 2) + Math.pow(intersection.y-getY(), 2));
				double timeTakeni = Math.max( Math.abs(2*di/(getVelocityX() + getNextVelocityX())), Math.abs(2*di/(getVelocityY() + getNextVelocityY())))/1000;
				
				double timeLeftThisUpdate = tpu-timeTakeni;
				
				
				x += (timeLeftThisUpdate/tpu)*velocityX*(30/Updater.actualFPS) + Math.pow(30/Updater.actualFPS, 2)*getDvX()/2;
				y += (timeLeftThisUpdate/tpu)*velocityY*(30/Updater.actualFPS) + Math.pow(30/Updater.actualFPS, 2)*getDvY()/2;
				
	}
	
	public Point pointOfIntersection(PhysicsObject obj, double yModifier) {
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
		return findIntersectedPoint(objectLine, intersectionLine);
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
//		if(!isStatic)
			this.velocityX = velocityX;
	}
	public double getVelocityY() {
		return velocityY;
	}
	public void setVelocityY(double velocityY) {
//		if(!isStatic)
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
		return getVelocityY()+getDvY() ;
	}
	public double getNextVelocityX() {
		return getVelocityX() + getDvX();
	}
	
	public boolean willCollide(PhysicsObject obj, double speedModifier1, double speedModifier2){
		colliding = false;
		Point2D.Float p1 = getNextPosition(x, y, getVelocityX(), getVelocityY());
		double p2y;
		if(obj.isStatic()){
			p2y = 0;
		}else
			p2y = obj.getVelocityY() + speedModifier2;
		Point2D.Float p2 = getNextPosition(obj.getX(), obj.getY(),obj.getVelocityX(), p2y);
		
		
		Rectangle2D.Float r1 = new Rectangle2D.Float();
		r1.setRect(p1.getX(), p1.getY(), width, height);
		Rectangle2D.Float r2 = new Rectangle2D.Float();
		r2.setRect(p2.getX(), p2.getY(), obj.getWidth(), obj.getHeight());
		
		float nx = p1.x;
		float ny = p1.y;
		
		Rectangle2D.Float rn = new Rectangle2D.Float();
		rn.setRect(x, y, width, height);
		
		for(double i = 0.1; i <= 1; i+=0.1) { // 0.1 is the step to lower margin of error. splits the path towards next point into ten points and checks.
			rn.x = (float) (x+((p1.x-x)*i));
			rn.y = (float) (y+((p1.y-y)*i));
			if(rn.intersects(r2)) {
				colliding = true;
				return colliding;
			}
		}
		return colliding;
		
//		if(r1.intersects(r2)) {
//			colliding = true;
//			return colliding;
//		}
//		return false;
		
//		return r1.intersects(r2);
	}
	
	public void kinematicDisplacement(double dt) { // d = vo � t + 0.5 � a � t^2
		setX(getVelocityX()+0.5*getDvX()*Math.pow(dt,2));
		setY(getVelocityY()+0.5*getDvY()*Math.pow(dt,2));
	}
	public void accelerationlessKinematicDisplacement(double dt) { // d = (v0 + vf)/ 2 � t
		setX(dt*(getVelocityX() + getNextVelocityY())/2);
		setY(dt*(getVelocityY() + getNextVelocityY())/2);
	}
	
	public boolean pointIntersect(float tmpX, float tmpY, Rectangle2D re) {
		if( (tmpX > re.getX() && tmpX < re.getX()+re.getWidth())  &&  (tmpY > re.getY() && tmpY < re.getY()+re.getHeight()))
			return true;
		return false;
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
	
	/**
	 * Takes in a PhysicsObject, and sets all fields equal to the object to clone.
	 * @param obj The physics object to be cloned
	 * @param keepAppearance Position, color and size remains unaffected
	 */
	public void cloneWith(PhysicsObject obj, boolean keepAppearance) {
		setDensity(obj.getDensity());
		setWeight(obj.getWeight());
		setVelocityX(obj.getVelocityX());
		setVelocityY(obj.getVelocityY());
		setDvX(obj.getDvX());
		setDvY(obj.getDvY());
		if(!keepAppearance) {
			setX(obj.getX());
			setY(obj.getY());
			setWidth(obj.getWidth());
			setHeight(obj.getHeight());
			setColor(obj.getColor());
		}
	}
	
	/**
	 * Pin this PhysicsObject to another object
	 * @param obj The Physics object to attach to this
	 */
	public void attach(PhysicsObject obj) {
		if(attached.contains(obj)) return; // C
		obj.cloneWith(this, true);
		obj.setAttachedTo(this);
		attached.add(obj);
	} 
	/**
	 * Deattach an object from the list of attachments
	 * @param obj
	 */
	public void release(PhysicsObject obj) {
		attached.remove(obj);
	}
	/**
	 * Deattach this object from its attachment
	 */
	public void release() {
		attachedTo = null;
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

	public double getDvX() {
		return dvX;
	}

	public void setDvX(double dvX) {
		this.dvX = dvX;
	}

	public double getDvY() {
		return dvY;
	}

	public void setDvY(double dvY) {
		this.dvY = dvY;
	}

	public ArrayList<PhysicsObject> getAttached() {
		return attached;
	}

	public void setAttached(ArrayList<PhysicsObject> attached) {
		this.attached = attached;
	}

	public PhysicsObject getAttachedTo() {
		return attachedTo;
	}

	public void setAttachedTo(PhysicsObject attachedTo) {
		this.attachedTo = attachedTo;
	}

	public boolean isMouseMove() {
		return mouseMove;
	}

	public void setMouseMove(boolean mouseMove) {
		this.mouseMove = mouseMove;
	}
	
	
	
}
