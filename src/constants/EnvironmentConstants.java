package constants;

public class EnvironmentConstants {

	
	/*
	 * It must be possible to both set and get all constants in this class
	 */
	
	public static double GRAVITY = 2.81;			// Convert to pixels/s^2
	public static double TERMINAL_VELOCITY = 56;	// Convert to pixels/s
	public static double AIR_DENSITY = 1.225; 	// kg/m^3
	public static double GRAVITATIONAL_CONSTANT = 6.673*Math.pow(10,-11);
	
	
	public void revertDefault() {
		GRAVITY = 9.81;
		TERMINAL_VELOCITY = 56;
		AIR_DENSITY = 1.225;
		GRAVITATIONAL_CONSTANT = 6.673*Math.pow(10,-11);
	}
	
}
