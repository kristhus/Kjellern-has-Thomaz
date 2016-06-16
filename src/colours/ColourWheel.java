package colours;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import graphics.MainFrame;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import particleEngine.ColorChooser;

public class ColourWheel extends JPanel implements MouseListener, MouseMotionListener {

	// Centre positions of image
	public int centreX = 40; 
	public int centreY = 40;
	
	public int mouseX = 200;
	public int mouseY = 190;
	
	private JLabel wheel;
	private JSlider slipnSlide;
	private ColorChooser callback;
	
	private int r = 255;
	private int g = 0;
	private int b = 0;
	
	private boolean mouseDown;
	
	public ColourWheel(ColorChooser caller) {
		callback = caller;
		readImage();
	}
	
	private void readImage() {
		ImageIcon wheelIcon = MainFrame.getReader().readGif("/colour/colourWheel.png");
		//wheelIcon = MainFrame.getReader().resizeIcon(wheelIcon, 90, 90);
		wheel = new JLabel();
		wheel.setIcon(wheelIcon);
		wheel.addMouseListener(this);
		wheel.addMouseMotionListener(this);
		add(wheel);
		centreY = centreX = wheelIcon.getIconHeight()/2;
		
		slipnSlide = new JSlider();
		slipnSlide.setMaximum(255);
		slipnSlide.setMinimum(0);
		slipnSlide.setValue((r+g+b)/3);
		slipnSlide.addChangeListener(new SlideListener());
		add(slipnSlide);
	}
	
	public void calculateColour(int mouseX, int mouseY) {
		
		float angle = (float) Math.toDegrees(Math.atan2(mouseY-centreY, mouseX - centreX));
		angle-= 90;
		if(angle < 0) angle+=360;
		System.out.println("Angle: " + angle);
		
		int modifier = (int) (angle%60) * 255/60;
		
		if(angle >0 && angle <= 60) {
			r = 255;
			g = (int) ((int) modifier);
			b = 0;
		}
		else if(angle > 60 && angle <= 120){
			r = (int) (255- modifier);
			g = 255;
			b = 0;
		}
		else if(angle > 120 && angle <= 180) {
			r = 0;
			g = 255;
			b = (int) (modifier);
		}
		
		else if(angle > 180 && angle <= 240) {
			r = 0;
			g = (int) (255 - modifier);
			b = 255;
		}
		else if(angle > 240 && angle <= 300) {
			r = (int) (modifier);
			g = 0;
			b = 255;
		}
		else if(angle > 300 && angle <= 360) {
			r = 255;
			g = 0;
			b = 255 - modifier;
		}
		callback.setColour(new Color(r, g, b));
		callback.getrSlider().setValue(r);
		callback.getgSlider().setValue(g);
		callback.getbSlider().setValue(b);
		slipnSlide.setValue((r+g+b)/3);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

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
		mouseDown = true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mouseX = arg0.getX();
		mouseY = arg0.getY();
		calculateColour(arg0.getX(), arg0.getY());
		mouseDown = false;
	}
	
	public void draw(Graphics g) {  
		paintComponents(g); //TODO: Find out wtf is wrong, and replace it with repaint()
	}
	
	@Override
	public void paintComponents(Graphics g) {
		g = getGraphics();
		Graphics2D g2d = (Graphics2D) g;
		if(g == null)
			return;
		super.paintComponents(g);
//	    g.setColor(new Color(r, this.g, b));  
//	    g.fillRect(150,50, 40, 40); 
		g2d.setStroke(new BasicStroke(2));
		g2d.drawOval(mouseX, mouseY, 15, 15);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mouseX = arg0.getX();
		mouseY = arg0.getY();
		calculateColour(arg0.getX(), arg0.getY());
		//getParent().repaint(); // Flickers when enabled
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public class SlideListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("r: " + r + " g: " + g + " b: " + b);
			System.out.println("Brightness: " + (r+g+b)/3);
			if(mouseDown) return;
			
			int val = ((JSlider)arg0.getSource()).getValue();
			int oldVal = (r+g+b)/3;
			int change = (val-oldVal);
			if (val == 255) r = g = b = 255;
			else if (val == 0) r = g = b = 0;
			else {
				if(change > 0) {
					r += (255-r)*change/(255-val);
					g += (255-g)*change/(255-val);
					b += (255-b)*change/(255-val);
				}else{
					r += (r)*change/(val);
					g += (g)*change/(val);
					b += (b)*change/(val);
				}
			}
				
				
			
			callback.setColour(new Color(r, g, b));
			callback.getrSlider().setValue(r);
			callback.getgSlider().setValue(g);
			callback.getbSlider().setValue(b);
			
//			int rMod = (255-r)/(255-val);
//			int gMod = (255-g)/(255-val);
//			int bMod = (255-g)/(255-val);
//			modifiedColor = new Color(rMod, gMod, bMod);
		}
		
	}
}
