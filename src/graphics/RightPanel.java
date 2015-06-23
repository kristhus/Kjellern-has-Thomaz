package graphics;

import interfaces.Drawable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JWindow;
import javax.swing.SpringLayout;

import shapeCreator.CreatorCanvas;
import particleEngine.ColorChooser;
import listeners.KeyBoardListener;
import moveable.Player;
import dragDrop.DragAndDrop;
import dragDrop.DragCanvas;

public class RightPanel extends JInternalFrame{

	private boolean drawableComponents;
	
	private ColorChooser colorChooser;
	
	
	
	private InternalPanel internalPane;
	
	public RightPanel() {
		super();
		setBackground(Color.white);
		
		setBounds(200,0,1000,700);
		setVisible(true);
		setClosable(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setMaximizable(true);
		
		internalPane = new InternalPanel();
		internalPane.setBackground(Color.red);
		internalPane.setVisible(true);
		BorderLayout bl = new BorderLayout();
		setLayout(bl);
		SpringLayout spl = new SpringLayout();
		internalPane.setLayout(spl);
		//temp.setBounds(0, 0, 200, 200);
		add(internalPane);
		
		internalPane.addKeyListener(MainFrame.getKeyBoardListener());
		
	}
	
	public void decide(String actionCommand) {
		internalPane.removeAll();
		BorderLayout bl = new BorderLayout();
		internalPane.setLayout(bl);
		internalPane.requestFocus();
		switch(actionCommand) {
		case "gif":
			ImageIcon gif = MainFrame.getReader().readGif("/gifs/ajax-loader.gif");
			JLabel gifLabel = new JLabel();
			gifLabel.setIcon(gif);
			internalPane.add(gifLabel, BorderLayout.CENTER);
			drawableComponents = false;
			break;
		case "particles":
			drawableComponents = true;
			colorChooser = new ColorChooser();
			internalPane.add(colorChooser);
			ParticleCanvas pc = new ParticleCanvas();
			SpringLayout spl = new SpringLayout();
			internalPane.setLayout(spl);
			internalPane.add(pc);
			((SpringLayout) internalPane.getLayout()).putConstraint(SpringLayout.NORTH, colorChooser, 5, SpringLayout.NORTH, internalPane);
			((SpringLayout) internalPane.getLayout()).putConstraint(SpringLayout.WEST, colorChooser, 5, SpringLayout.WEST, internalPane);
			((SpringLayout) internalPane.getLayout()).putConstraint(SpringLayout.NORTH, pc, 5, SpringLayout.SOUTH, colorChooser);
			((SpringLayout) internalPane.getLayout()).putConstraint(SpringLayout.WEST, pc, 5, SpringLayout.WEST, internalPane);
			break;
		case "Drag&Drop": 
			internalPane.add(new DragCanvas(), BorderLayout.CENTER);
			drawableComponents = true;
			break;
		case "":
			System.err.println("When creating a listitem, an action command must be added as well!");
			break;
		
		case "move":
			Player player = new Player(10,10,0,0,5,5);
			internalPane.add(player);
			drawableComponents = true;
			break;
		case "create":
			internalPane.add(new CreatorCanvas());
			drawableComponents = true;
			break;
			
		}
		repaint();
	}

	public void draw(Graphics g) {
		internalPane.draw(g);
	}
	public void update(double dt) {
			internalPane.update(dt);
	}

	public ColorChooser getColorChooser() {
		// TODO Auto-generated method stub
		return colorChooser;
	}
	

	private class InternalPanel extends JPanel implements Drawable{
		
		@Override
		public void draw(Graphics g) {
			// TODO Auto-generated method stub
			for(Component c : getComponents()) {
				if(c instanceof Drawable){
					((Drawable) c).draw(g);
				}
			}
		}
		public void update(double dt) {
			for(Component c : getComponents()) {
				if(c instanceof Drawable) {
					((Drawable)c).update (dt);
				}
			}
		}
		
	}

}
