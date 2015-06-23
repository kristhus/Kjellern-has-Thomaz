package physics;

public class Force {

	private double forceX;
	private double forceY;
	
	public Force(double forceX, double forceY) {
		this.forceX = forceX;
		this.forceY = forceY;
	}
	public double getForceX() {
		return forceX;
	}
	public void setForceX(double forceX) {
		this.forceX = forceX;
	}
	public double getForceY() {
		return forceY;
	}
	public void setForceY(double forceY) {
		this.forceY = forceY;
	}
}
