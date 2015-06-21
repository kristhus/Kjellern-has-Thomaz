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

import listeners.KeyBoardListener;

public class DragCanvas extends JPanel implements MouseMotionListener, MouseListener, Drawable{
	
	private int mouseX;
	private int mouseY;
	
	private DragAndDrop box;
	
	private ArrayList<DragAndDrop> selectedComponents; //TODO: Multiselection?
	private ArrayList<DragAndDrop> availableComponents;
	
	public DragCanvas() {
		setBackground(Color.black);
		setVisible(true);
		DragAndDrop tmp = new DragAndDrop(DragAndDrop.AQUATIC);
		tmp.setVisible(true);
		tmp.setPreferredSize(new Dimension(70,70));
		
		JTextField tf = new JTextField("DragMe");
		tf.setEnabled(false);
		tmp.add(tf);
		add(tmp);
		
		DragAndDrop tmp2 = new DragAndDrop(DragAndDrop.DEFAULT);
		tmp2.setVisible(true);
		tmp2.setPreferredSize(new Dimension(70,70));
		tf = new JTextField("Box");
		tf.setEnabled(false);
		tmp2.add(tf);
		add(tmp2);
		
		
		availableComponents = new ArrayList<DragAndDrop>();
		availableComponents.add(tmp);
		availableComponents.add(tmp2);
		
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		for(DragAndDrop dad : availableComponents) {
			if(dad.intersects(arg0.getPoint())){
				System.out.println("Clicked box");
				dad.setSelected(true);
			}else {
				dad.setSelected(false);
			}
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
		for(DragAndDrop dad : availableComponents) {
		if(dad.intersects(arg0.getPoint())){
			dad.setSelected(true);
		}else {
			dad.setSelected(false);
		}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//box.setSelected(false);
		for(DragAndDrop dad : availableComponents) {
			if(dad.intersects(arg0.getPoint())){
				dad.setHover(true);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mouseX = arg0.getX();
		mouseY = arg0.getY();
		for(DragAndDrop dad : availableComponents) {
			if(dad.isSelected())
				for(int i = 0; i < availableComponents.size();i++)  {
					if(!availableComponents.get(i).intersects(arg0.getPoint()))
						dad.setLocation(mouseX-dad.getWidth()/2, mouseY-dad.getHeight()/2);
				}
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		for(DragAndDrop dad : availableComponents) {
		if(dad.intersects(arg0.getPoint())){
			dad.setHover(true);
		}else
			dad.setHover(false);
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(double dt) {
		// TODO Auto-generated method stub
		
	}
	
}
