package graphics;

import interfaces.Drawable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ListItem extends JPanel implements MouseListener, Drawable{

	public String pathName;
	public String title;
	public JLabel label;
	public String actionCommand;
	
	private final int shapeX = 15;
	private final int shapeY = 15;
	private final int shapeWidth = 40;
	private final int shapeHeight = 40;
	
	private int relativeX;
	private int relativeY;
	
	private final boolean ignore;
	
	private Point mousePosition;
	
	private Shape shape;
	
	private Color c1;
	private Color c2;
	
	private JComponent caller;
	
	public ListItem (String pathName, String title, String actionCommand, boolean ignore, JComponent caller) {
		this.pathName = pathName;
		this.title = title;
		this.actionCommand = actionCommand;
		this.caller = caller;
		setBackground(Color.white);
		addMouseListener(this);
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		createLabel();
		setBorder(BorderFactory.createSoftBevelBorder(Color.OPAQUE, Color.BLACK, Color.gray));
		c1 = new Color(255,100,255, 150);
		c2 = new Color(150,200,255);
		this.ignore = ignore;
		revalidate();
		repaint();
	}
	
	public ListItem(Shape shape, String title, String actionCommand, JComponent caller) {
		this("", title, actionCommand, true, caller);
		this.shape = shape;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(new GradientPaint(new Point(0, 0), c1,
                new Point(getWidth(), getHeight()), c2, false));
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		if(shape==null)
			return;
		if(shape instanceof Rectangle2D) {
			g.setColor(Color.gray);
			g.drawRect(shapeX, shapeY, shapeWidth, shapeHeight);
			g.setColor(Color.blue);
			g.fillRect(shapeX, shapeY, shapeWidth, shapeHeight);
		}else if(shape instanceof RoundRectangle2D){
			((RoundRectangle2D) shape).setRoundRect(shapeX, shapeY, shapeWidth, shapeHeight, 10, 10);
			g2d.setColor(Color.gray);
			g2d.fill(shape);
		}else if(shape instanceof Ellipse2D){
			((Ellipse2D) shape).setFrame(shapeX, shapeY, shapeWidth, shapeHeight);
			g2d.setColor(Color.yellow);
			g2d.fill(shape);
		}else if(shape instanceof Line2D) {
			g2d.setColor(Color.green);
			((Line2D) shape).setLine(new Point.Float(shapeX, shapeY), new Point(shapeX+shapeWidth, shapeY+shapeHeight));
			g2d.draw(shape);
		}
		else if(shape instanceof Arc2D){
			
		}else if(shape instanceof Graphics2D){
			//TODO: Figure out what can be made of this(images or something stupid)
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		// Not going to be used i think
	}

	@Override
	public void update(double dt) {
		// TODO Auto-generated method stub
	}
	
	
	public void createLabel() {
		label = new JLabel("                " + title);
		Font font = new Font("Arial", Font.BOLD, 14); // Manage fonts and size through user prefs?
		label.setFont(font);
		GridBagConstraints c = new GridBagConstraints();
		c.ipadx = 100;
		c.ipady = 60;
		add(label, c);
		addMouseMotionListener(MainFrame.mouseListener);
	}
	
	
	public void mouseClicked(MouseEvent arg0) {
		// Perform action relevant to the item selected		
		if(!ignore) {
			if(MainFrame.getRightPanel().isClosed()) {
				MainFrame.getRightPanel().setVisible(true);
				MainFrame.getRightPanel().setBounds(150,0,600,600);
			}
			MainFrame.getRightPanel().decide(actionCommand);
			MainFrame.getMainPanel().revalidate();
		}
		else{
			
		}
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		setBackground(new Color(63, 152, 207));
		label.setForeground(Color.white);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		setBackground(Color.white);
		label.setForeground(Color.black);
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); 
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Create bevel effect
		mousePosition = arg0.getPoint();
		c2 = new Color(255,255,255, 150);
		c1 = new Color(150,200,255);
		setBorder(BorderFactory.createSoftBevelBorder(Color.OPAQUE, Color.gray, Color.black));
		if(MainFrame.mouseListener.btnFromSource) {
			MainFrame.mouseListener.mousePosition = new Point(caller.getLocation().x, caller.getLocation().y);
		}
		MainFrame.mouseListener.relativeToSource = true;
		MainFrame.mouseListener.externalSource = this;
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Remove bevel effect
		c1 = new Color(255,150,255, 150);
		c2 = new Color(150,200,255);
		setBorder(BorderFactory.createSoftBevelBorder(Color.OPAQUE, Color.BLACK, Color.gray));
		MainFrame.mouseListener.relativeToSource = false;
		MainFrame.mouseListener.btnFromSource = false;
		MainFrame.mouseListener.waitForRelease = false;
		MainFrame.mouseListener.externalSource = null;
	}
	
	public JLabel getLabel() {
		return label;
	}

}
