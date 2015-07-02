package graphics;

import interfaces.Drawable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import listeners.CanvasMouseListener;
import listeners.KeyBoardListener;
import moveable.GameObject;
import moveable.MoveCanvas;
import moveable.Player;
import data.Updater;
import dragDrop.DragAndDrop;
import dragDrop.DragCanvas;

public class RightPanel extends JInternalFrame{

	private boolean drawableComponents;
	
	private ColorChooser colorChooser;
	
	
	
	private InternalPanel internalPane;
	private CanvasMouseListener mouseListener;
	
	
	public RightPanel() {
		super();
		setBackground(Color.white);
		setBounds(200,0,1000,700);
		setClosable(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setMaximizable(true);
		mouseListener = new CanvasMouseListener();
		internalPane = new InternalPanel();
		internalPane.setBackground(Color.red);
		internalPane.setVisible(true);
		BorderLayout bl = new BorderLayout();
		setLayout(bl);
		GridLayout gl = new GridLayout();
		setContentPane(internalPane);
		mouseListener = new CanvasMouseListener();
		internalPane.addKeyListener(MainFrame.getKeyBoardListener());
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
//			Player player = new Player();
			MoveCanvas mc = new MoveCanvas();
			internalPane.add(mc);
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
	

	public class InternalPanel extends JPanel implements Drawable{
		
		
		@Override
		public void paintComponents(Graphics g) {
			
			
		}
		
		
		@Override
		public void draw(Graphics g) {
			// TODO Auto-generated method stub
			for(Component c : getComponents()) {
				if(c instanceof Drawable){
					((Drawable) c).draw(getGraphics());  // I want the children to have acces to the entire graphics of this un
				}
			}
			if(MainFrame.DEV_MODE) {
				int fontSize = 14;
				Graphics2D g2d = (Graphics2D) g.create();
			    g2d.setColor(new Color(100,90,100, 255));
			    g2d.fillRect( MainFrame.getMainPanel().getWidth()-55, 38, 57, 50);
			    g2d.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
			    g2d.setColor(Color.red);
			    g2d.drawString("FPS: " + Updater.actualFPS, MainFrame.getMainPanel().getWidth()-50,50);
			//	g2d.drawString("X: " + mouseX, 20, 70);
			//	g2d.drawString("Y: " + mouseY, 20, 95);
				g2d.dispose();
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

	public CanvasMouseListener getMouseListener() {
		return mouseListener;
	}
	
}
