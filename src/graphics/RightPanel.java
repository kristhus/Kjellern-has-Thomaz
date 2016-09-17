package graphics;

import handlers.CanvasMouseHandler;
import handlers.KeyBoardListener;
import interfaces.Drawable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import letterTone.PreProcessedPanel;
import moveable.GameObject;
import moveable.MoveCanvas;
import moveable.Player;
import dragDrop.DragAndDrop;
import dragDrop.DragCanvas;

public class RightPanel extends JInternalFrame{

	private boolean drawableComponents;
	
	private ColorChooser colorChooser;
	
	
	
	private InternalPanel internalPane;
	private CanvasMouseHandler mouseListener;
	
	
	public RightPanel() {
		super();
		setBackground(Color.white);
		setBounds(200,0,1000,700);
		setClosable(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setMaximizable(true);
		mouseListener = new CanvasMouseHandler();
		internalPane = new InternalPanel();
		internalPane.setBackground(Color.white);
		internalPane.setVisible(true);
		BorderLayout bl = new BorderLayout();
		setLayout(bl);
		GridLayout gl = new GridLayout();
		setContentPane(internalPane);
		mouseListener = new CanvasMouseHandler();
		internalPane.addKeyListener(MainFrame.keyHandler);
		addKeyListener(MainFrame.keyHandler);
		setResizable(true);
		decide("particles");
		revalidate();
		setVisible(true);
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
			colorChooser = new ColorChooser();// Choose colours
			internalPane.add(colorChooser);
			Toolbox tb = new Toolbox(); // A submenu of various stuff which particleCanvas may use
			internalPane.add(tb);
			ParticleCanvas pc = new ParticleCanvas(tb);
			SpringLayout spl = new SpringLayout();
			internalPane.setLayout(spl);
			internalPane.add(pc);
			((SpringLayout) internalPane.getLayout()).putConstraint(SpringLayout.NORTH, colorChooser, 5, SpringLayout.NORTH, internalPane);//
			((SpringLayout) internalPane.getLayout()).putConstraint(SpringLayout.WEST, colorChooser, 5, SpringLayout.WEST, internalPane);
			((SpringLayout) internalPane.getLayout()).putConstraint(SpringLayout.NORTH, pc, 5, SpringLayout.SOUTH, colorChooser);//
			((SpringLayout) internalPane.getLayout()).putConstraint(SpringLayout.WEST, pc, 5, SpringLayout.WEST, internalPane);
			((SpringLayout) internalPane.getLayout()).putConstraint(SpringLayout.WEST, tb, 5, SpringLayout.EAST, colorChooser);//
			((SpringLayout) internalPane.getLayout()).putConstraint(SpringLayout.NORTH, tb, 0, SpringLayout.NORTH, colorChooser);
			((SpringLayout) internalPane.getLayout()).putConstraint(SpringLayout.SOUTH, tb, 5, SpringLayout.NORTH, pc);
			break;
		case "Drag&Drop": 
			internalPane.add(new DragCanvas(), BorderLayout.CENTER);
			drawableComponents = true;
			break;
		case "":
			System.err.println("When creating a listitem, an action command must be added as well!");
			break;
		case "gradient":
			internalPane.add(new GradientCreator());
			drawableComponents = true;
			break;
		case "move":
//			Player player = new Player();
			MoveCanvas mc = new MoveCanvas();
			internalPane.add(mc);
			drawableComponents = true;
			break;
		case "create":
			internalPane.add(new CreatorCanvas());
			drawableComponents = true;
			break;
		case "letterTone":
			System.out.println("Test");
			internalPane.add(new PreProcessedPanel());
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
	

	public class InternalPanel extends JPanel implements Drawable{
		
		@Override
		public void draw(Graphics g) {
			// TODO Auto-generated method stub
			for(Component c : getComponents()) {
				if(c instanceof Drawable){
					((Drawable) c).draw(getGraphics());  // I want the children to have acces to the entire graphics of this un
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

	public CanvasMouseHandler getMouseListener() {
		return mouseListener;
	}
	
}
