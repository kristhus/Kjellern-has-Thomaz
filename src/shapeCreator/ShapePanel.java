package shapeCreator;

import graphics.ListItem;
import graphics.ListItemGroup;
import graphics.MainFrame;
import interfaces.Drawable;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class ShapePanel extends JPanel implements Drawable{
	
	private ListItemGroup group;
	private GridLayout gl;
	
	public ShapePanel() {
		setBackground(Color.white);
		GridLayout gl = new GridLayout();
		group = new ListItemGroup();
		add(group);
		ListItem tmp = new ListItem(new Rectangle(), "Rectangle", "rect", this);
		group.addC(tmp);
		RoundRectangle2D shape=new RoundRectangle2D.Float(0,0,30,10,10,10);
		tmp = new ListItem(shape, "Rounded", "rounded", this);
		group.addC(tmp);
		Ellipse2D.Float circle = new Ellipse2D.Float();
		tmp =new ListItem(circle, "Circle", "circle", this);
		group.addC(tmp);
		Line2D.Float line = new Line2D.Float();
		tmp = new ListItem(line, "Line", "line", this);
		group.addC(tmp, gl);
		revalidate();
		
		addMouseMotionListener(MainFrame.mouseListener);
	}

	public ListItemGroup getGroup() {
		return group;
	}
	
	public void update(double dt) {
		// TODO Auto-generated method stub
		
	}

	public void draw(Graphics g) {
		repaint();
		// TODO Auto-generated method stub
	}

}