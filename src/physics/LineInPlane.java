package physics;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.awt.geom.Rectangle2D;

public class LineInPlane extends Line2D{

	public double x1;
	public double y1;
	public double x2;
	public double y2;
	
	public LineInPlane(Point p1, Point p2) {
		x1=p1.x; y1 = p1.y;
		x2=p2.x; y2 = p2.y;
	}
	
	public LineInPlane(Point.Float p1, Point.Float p2) {
		// TODO Auto-generated constructor stub
		x1=p1.x; y1 = p1.y;
		x2=p2.x; y2 = p2.y;
	}

	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point2D getP1() {
		// TODO Auto-generated method stub
		Point2D.Double p2d = new Point.Double(x1,y1);
		return p2d;
	}

	@Override
	public Point2D getP2() {
		// TODO Auto-generated method stub
		Point2D.Double p2d = new Point.Double(x2,y2);
		return null;
	}

	@Override
	public double getX1() {
		// TODO Auto-generated method stub
		return x1;
	}

	@Override
	public double getX2() {
		// TODO Auto-generated method stub
		return x2;
	}

	@Override
	public double getY1() {
		// TODO Auto-generated method stub
		return y1;
	}

	@Override
	public double getY2() {
		// TODO Auto-generated method stub
		return y2;
	}

	@Override
	public void setLine(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		x1 = arg0;y1 = arg0;x2 = arg2;y2=arg2;
	}

}
