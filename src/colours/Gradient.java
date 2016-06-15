package colours;

import java.util.ArrayList;

public class Gradient {
	
	private ArrayList<GradientPoint> gradientPoints = new ArrayList<GradientPoint>();
	
	public void addPoint(GradientPoint p) {
		gradientPoints.add(p);
	}
	
	public void clear(){
		gradientPoints = new ArrayList<GradientPoint>();
	}
}
