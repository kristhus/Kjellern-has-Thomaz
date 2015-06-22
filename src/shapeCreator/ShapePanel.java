package shapeCreator;

import graphics.ListItem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

public class ShapePanel extends JPanel {
	
	public ShapePanel() {
		setBackground(Color.white);
		GridBagLayout gbl = new GridBagLayout(); 
		setLayout(gbl);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 100;
		c.weightx = 1;
		c.gridwidth = 100;
		//c.weightx = 1;
		c.anchor = GridBagConstraints.EAST;
		add(new ListItem(new Rectangle(), "Rectangle", "rect"), c);
		c.gridy ++;
		RoundRectangle2D shape=new RoundRectangle2D.Float(0,0,30,10,10,10);
		add(new ListItem(shape, "Rounded", "rounded"), c);
		c.gridy ++;
		Ellipse2D.Float circle = new Ellipse2D.Float();
		add(new ListItem(circle, "Circle", "circle"), c);
		c.gridy++;
		Line2D.Float line = new Line2D.Float();
		add(new ListItem(line, "Line", "line"), c);
		
		
		setPreferredSize(new Dimension(150,600));
	}

}
