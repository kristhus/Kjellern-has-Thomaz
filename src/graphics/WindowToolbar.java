package graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class WindowToolbar extends JPanel implements MouseMotionListener, MouseListener{

	private JLabel minimize;
	private JLabel maximize;
	private JLabel close;
	
	private JLabel appIcon;
	
	public int onscrX;
	public int onscrY;
	public int fromX;
	public int fromY;
	
	private Dimension prevSize;
	private Point prevLocation;
	
	private boolean goFullScreen;
	
	private JFrame mainFrame;
	
	public WindowToolbar(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		minimize = new JLabel();
		maximize = new JLabel();
		close = new JLabel();
		appIcon = new JLabel();
		
		minimize.setIcon(new ImageIcon(getClass().getResource("/tools/minimize.png")));
		maximize.setIcon(new ImageIcon(getClass().getResource("/tools/maximize.png")));
		close.setIcon(new ImageIcon(getClass().getResource("/tools/close.png")));
		appIcon.setIcon(new ImageIcon(getClass().getResource("/icon/appIcon[small].png")));
		add(appIcon);
		
		
		minimize.addMouseListener(this);
		minimize.addMouseMotionListener(this);
		minimize.setName("Minimize");
		maximize.addMouseListener(this);
		maximize.addMouseMotionListener(this);
		maximize.setName("Maximize");
		close.addMouseListener(this);
		close.addMouseMotionListener(this);
		close.setName("Close");
		JPanel tmp = new JPanel();
		tmp.add(minimize);
		tmp.setBackground(Color.white);
		add(tmp);
		JPanel tmp2 = new JPanel();
		tmp2.add(maximize);
		tmp2.setBackground(Color.white);
		add(tmp2);
		JPanel tmp3 = new JPanel();
		tmp3.add(close);
		tmp3.setBackground(Color.white);
		add(tmp3);
//		setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.black));
		SpringLayout spl = new SpringLayout();
		setLayout(spl);
		spl.putConstraint(spl.EAST, tmp3, 0, spl.EAST, this);
		spl.putConstraint(spl.EAST, tmp2, 0, spl.WEST, tmp3);
		spl.putConstraint(spl.EAST, tmp, 0, spl.WEST, tmp2);
		
		spl.putConstraint(spl.WEST, appIcon, 5, spl.WEST, this);
		spl.putConstraint(spl.NORTH, appIcon, 5,spl.NORTH,this);
		
		addMouseMotionListener(new ClickAndDrag());
		addMouseListener(new ClickAndDrag());
		setBackground(new Color(20,20,20,20));
		prevLocation = new Point(0,0);
		prevSize = mainFrame.getSize();
	}

	
	@Override
	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        int w = getWidth();
//        int h = getHeight();
//        Color color1 = new Color(255,255,255);
//        Color color2 = new Color(200,200,200);
//        GradientPaint gp = new GradientPaint(0, h/2, color1, 0, h, color2);
//        g2d.setPaint(gp);
//        g2d.fillRect(0, 0, w, h);

	}
	
	public void draw(Graphics g) {
//		g = getGraphics();
//		final int R = 240;
//        final int G = 240;
//        final int B = 240;
//		Paint p =
//                new GradientPaint(0.0f, 0.0f, new Color(R, G, B, 0),
//                    0.0f, getHeight(), new Color(R, G, B, 255), true);
//            Graphics2D g2d = (Graphics2D)g;
//            g2d.setPaint(p);
//            g2d.fillRect(0, 0, getWidth(), getHeight());
//            g2d.dispose();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}


	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JLabel source = (JLabel) arg0.getSource();
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JLabel source = (JLabel) arg0.getSource();
//		source.getParent().setBackground(Color.gray);
		switch(source.getName()) {
		case "Minimize":
			mainFrame.setState(JFrame.ICONIFIED);
			break;
		case "Maximize":
			maximize();
			break;
		case "Close":
			System.exit(0); // Run a are you sure prompt or something
		}
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JLabel source = (JLabel) arg0.getSource();
		if(((JLabel) arg0.getSource()).getName().equals("Close")) {
			source.getParent().setBackground(new Color(255,150,150));
		}
		else {
			source.getParent().setBackground(new Color(120,120,255));
		}
		repaint();
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JLabel source = (JLabel) arg0.getSource();
		source.getParent().setBackground(Color.white);
		repaint();
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JLabel source = (JLabel) arg0.getSource();
		if(((JLabel) arg0.getSource()).getName().equals("Close")) {
			source.getParent().setBackground(new Color(255,80,80));
		}else
			source.getParent().setBackground(Color.gray);
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JLabel source = (JLabel) arg0.getSource();
		source.getParent().setBackground(new Color(120,120,255));
	}
	
	public void maximize() {
		getToolkit();
		int scrW = Toolkit.getDefaultToolkit().getScreenSize().width;
		getToolkit();
		int scrH = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		
		if(mainFrame.getWidth() >= scrW) {
			System.out.println("BYTT TIL MINIMIERT");
			mainFrame.setBounds(prevLocation.x, prevLocation.y, prevSize.width, prevSize.height);
		}
		else {
			prevSize = mainFrame.getSize();
			prevLocation = getLocationOnScreen();
			mainFrame.setBounds(0,0,scrW,scrH);
		}
	}
	
	private class ClickAndDrag implements MouseMotionListener, MouseListener{

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			onscrX = arg0.getXOnScreen();
			onscrY = arg0.getYOnScreen();
			mainFrame.setLocation(new Point(onscrX-fromX, onscrY-fromY));
			prevLocation = getLocationOnScreen();
			if(onscrY <= 0)
				goFullScreen = true;
			else
				goFullScreen = false;
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.getClickCount()%2 == 0)
				maximize();
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
			fromX = arg0.getX();
			fromY = arg0.getY();
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if(goFullScreen)
				maximize();
		}
	}
	
}
