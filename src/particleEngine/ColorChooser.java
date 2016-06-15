package particleEngine;

import interfaces.Drawable;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import colours.ColourWheel;



public class ColorChooser extends JPanel implements Drawable, ItemListener {

	private JSlider rSlider;
	private JSlider gSlider;
	private JSlider bSlider;
	
	private JCheckBox seedsOn;

	private JFrame advancedColourFrame;
	private JPanel selectedColourSquare;
	private Color selectedColour;
	private ColourWheel colourWheel;
	
	private boolean mouseDown;
	private boolean seedOn;
	
	public boolean isSeedOn() {
		return seedOn;
	}


	public ColorChooser() {
			
		rSlider = new JSlider();
		gSlider = new JSlider();
		bSlider = new JSlider();
		rSlider.addChangeListener(new SliderStateChange());
		gSlider.addChangeListener(new SliderStateChange());
		bSlider.addChangeListener(new SliderStateChange());
		
		seedsOn = new JCheckBox();
		seedsOn.addItemListener(this);
		
		rSlider.setMaximum(255);
		gSlider.setMaximum(255);
		bSlider.setMaximum(255);
		
		selectedColourSquare = new JPanel();
		selectedColourSquare.addMouseListener(new SelectColourListener(this));
		selectedColourSquare.setPreferredSize(new Dimension(25, 25));
		selectedColourSquare.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.black, new Color(100,100,100)));
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = c.weightx = 1.0;
		c.gridy= 0;
		c.gridx = 0;
		
		add(rSlider, c);
		c.gridx++;
		add(seedsOn, c);
		c.gridx = 0;
		c.gridy++;
		add(gSlider, c);
		c.gridy++;
		add(bSlider, c);
		c.gridx = 1;
		c.gridy = 1;
		//c.weightx = c.weighty = 1.0;
		add(selectedColourSquare,c);
		setPreferredSize(new Dimension(300, 100));
		setBackground(Color.white);
		setVisible(true);
		selectedColour = new Color(255,255,255);
	}
	@Override
	public void paintComponents(Graphics g) {
		g = getGraphics();
		if(g == null)
			return;
		super.paintComponents(g);
	    g.setColor(new Color(rSlider.getValue(), gSlider.getValue(), bSlider.getValue()));  
	    g.fillRect(rSlider.getX()+ rSlider.getWidth() +5,getHeight()/2-20,40,40); 
	}
	
	
	public void draw(Graphics g) {  
		selectedColourSquare.setBackground(selectedColour);
		if(colourWheel != null)
			colourWheel.draw(g);
		//paintComponents(g); //TODO: Find out wtf is wrong, and replace it with repaint()
	}

	public void update(double dt) {
		//System.out.println(rSlider.getValue());
		//selectedColour = new Color(rSlider.getValue(), gSlider.getValue(), bSlider.getValue());
	}
	
	
	public class SliderStateChange implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent arg0) {
			// TODO Auto-generated method stub
			selectedColour = new Color(rSlider.getValue(), gSlider.getValue(), bSlider.getValue());
		}
		
	}


	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getStateChange() == ItemEvent.SELECTED) {
			seedOn = true;
		} else {
			seedOn = false;
		}
	}
	
	public Color getColor() {
		return new Color(rSlider.getValue(), gSlider.getValue(), bSlider.getValue());
	}


	public JSlider getrSlider() {
		return rSlider;
	}


	public void setrSlider(JSlider rSlider) {
		this.rSlider = rSlider;
	}


	public JSlider getgSlider() {
		return gSlider;
	}


	public void setgSlider(JSlider gSlider) {
		this.gSlider = gSlider;
	}


	public JSlider getbSlider() {
		return bSlider;
	}


	public void setbSlider(JSlider bSlider) {
		this.bSlider = bSlider;
	}

	public class SelectColourListener implements MouseListener {
		private ColorChooser cc;
		public SelectColourListener(ColorChooser caller) {
			cc = caller;
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if(advancedColourFrame != null) advancedColourFrame.dispose();
			advancedColourFrame = new JFrame();
			advancedColourFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			colourWheel = new ColourWheel(cc);
			advancedColourFrame.add(colourWheel);
			System.out.println("Created frame");
			advancedColourFrame.setVisible(true);
			advancedColourFrame.pack();
			advancedColourFrame.revalidate();
			advancedColourFrame.repaint();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			selectedColourSquare.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, new Color(100,100,100)));
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			selectedColourSquare.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.black, new Color(100,100,100)));
		}
		
	}
	
	public void setColour(Color c) {
		selectedColour = c;
	}
	
}
