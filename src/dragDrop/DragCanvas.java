package dragDrop;

import interfaces.Drawable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class DragCanvas extends JPanel implements MouseMotionListener, MouseListener, Drawable{
	
	private int mouseX;
	private int mouseY;
	
	private DragAndDrop box;
	
	private ArrayList<DragAndDrop> selectedComponents;
	private ArrayList<DragAndDrop> availableComponents;
	
	public DragCanvas() {
		setBackground(Color.black);
		setVisible(true);
		box = new DragAndDrop(DragAndDrop.AQUATIC);
		box.setVisible(true);
		box.setPreferredSize(new Dimension(70,70));
		
		JTextField tf = new JTextField("DragMe");
		tf.setEnabled(false);
		box.add(tf);
		add(box);
		
		availableComponents = new ArrayList<DragAndDrop>();
		availableComponents.add(box);
		addMouseMotionListener(this);
		addMouseListener(this);
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(box.intersects(arg0.getPoint())){
			System.out.println("Clicked box");
			box.setSelected(true);
		}else {
			box.setSelected(false);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(box.intersects(arg0.getPoint())){
			box.setSelected(true);
		}else {
			box.setSelected(false);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//box.setSelected(false);
		if(box.intersects(arg0.getPoint())){
			box.setHover(true);
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mouseX = arg0.getX();
		mouseY = arg0.getY();
		if(box.isSelected())
			box.setLocation(mouseX-box.getWidth()/2, mouseY-box.getHeight()/2);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(box.intersects(arg0.getPoint())){
			box.setHover(true);
		}else
			box.setHover(false);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(long dt) {
		// TODO Auto-generated method stub
		
	}
	
}
