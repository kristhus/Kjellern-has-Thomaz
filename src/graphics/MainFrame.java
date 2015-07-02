package graphics;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JWindow;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.DesktopPaneUI;

import listeners.CanvasMouseListener;
import listeners.KeyBoardListener;
import data.Updater;
import reader.Reader;

public class MainFrame extends JFrame implements MouseMotionListener, MouseListener{
	
	public static MainFrame mainFrame;
	public static JDesktopPane mainPanel;
	public static Reader reader;
	public static Updater updater;
	
	public static final boolean DEV_MODE = true;
	private static boolean DEV_MODE_INITIALIZED = false;
	private static KeyBoardListener keyBoardListener;
	public static final int FPS = 30;
	
	public static LeftPanel leftPanel;
	public static RightPanel rightPanel;
	private static WindowToolbar windowToolbar;
	
	public static CanvasMouseListener mouseListener = new CanvasMouseListener();
	
	
	private boolean resizeR2L;
	private boolean resizeL2R;
	private boolean resizeT2B;
	private boolean resizeB2T;
	
	public static void main(String args[]) {
		mainFrame = new MainFrame();
		reader = new Reader();
		updater = new Updater();
		updater.calculateFPS();
		
	}
	
	public MainFrame() {
		// SPLASH SCREEN  TODO: Add loading sequence
		super();
		
		if(!DEV_MODE) {
			JWindow window = new JWindow();
			window.getContentPane().add(new JLabel("", new ImageIcon(getClass().getResource("/splash.png")), SwingConstants.CENTER));
			window.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2-200, Toolkit.getDefaultToolkit().getScreenSize().height/2-100, 400, 200);
			window.setVisible(true);
			try {
			    Thread.sleep(2000);
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
			window.setVisible(false);
			window.dispose();
		}
		
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception ex) {}
		}
		
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
//		setExtendedState(JFrame.MAXIMIZED_BOTH);  //Fullscreen
		setUndecorated(true);
		
		BorderLayout bl = new BorderLayout();
		setLayout(bl);
		windowToolbar = new WindowToolbar(this);
		windowToolbar.setBackground(Color.WHITE);
		windowToolbar.setPreferredSize(new Dimension(1400,32));
		add(windowToolbar, bl.NORTH);
		
		getRootPane().setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(255, 150,150, 100)));
		getRootPane().setOpaque(false);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icon/appIcon.png")));  //Need an icon
		keyBoardListener = new KeyBoardListener();
		addKeyListener(keyBoardListener);
		mainPanel = new JDesktopPane();
		mainPanel.addKeyListener(MainFrame.getKeyBoardListener());
		mainPanel.setOpaque(true);       
        mainPanel.setFocusable(true);
        add(mainPanel, bl.CENTER);
        leftPanel = new LeftPanel();
        rightPanel = new RightPanel();
        mainPanel.add(leftPanel, new Integer(1));
        mainPanel.add(rightPanel, new Integer(2));
		setBounds(0,0,1400, 800);
		CreateMenu cm = new CreateMenu();
//		setJMenuBar(cm.createMenu());
		revalidate();
		setVisible(true);
		
		addMouseMotionListener(this);
		addMouseListener(this);
		 setBackground(new Color(0,0,0,0));
//		pack();
	}
	
	@Override
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
		if(DEV_MODE) {
			int fontSize = 14;
			Graphics2D g2d = (Graphics2D) g.create();
		    g2d.setColor(new Color(100,90,100, 5));
		    g2d.fillRect(0, 50, 100, 50);
		    g2d.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
		    g2d.setColor(Color.red);
		    
		//	g2d.drawString("X: " + mouseX, 20, 70);
		//	g2d.drawString("Y: " + mouseY, 20, 95);
			g2d.dispose();
		}
		final int R = 240;
        final int G = 240;
        final int B = 240;
		Paint p =
                new GradientPaint(0.0f, 0.0f, new Color(R, G, B, 0),
                    0.0f, getHeight(), new Color(R, G, B, 255), true);
            Graphics2D g2d = (Graphics2D)g;
            g2d.setPaint(p);
            g2d.fillRect(0, 0, getWidth(), getHeight());
	}

	

	public static void draw(Graphics g) {
		getRightPanel().draw(g);
		windowToolbar.draw(g);
	}
	
	public static void update(double dt) {
		getRightPanel().update(dt);
	}
	
	public static Reader getReader() {
		return reader;
	}
	
	public static void recreateRightPanel() {
		mainPanel.add(new RightPanel(), new Integer(1));
	}
	
	public static RightPanel getRightPanel() {
		return rightPanel;
	}
	public static LeftPanel getLeftPanel() {
		return leftPanel;
	}

	public static MainFrame getmainFrame() {
		return mainFrame;
	}

	public static JComponent getMainPanel() {
		return mainPanel;
	}
	
	public static KeyBoardListener getKeyBoardListener(){
		return keyBoardListener;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Add support for dragging corners
		if(resizeR2L)
			setBounds(getLocation().x, getLocation().y, arg0.getXOnScreen()-getLocation().x+5, getHeight());
		if(resizeL2R) {
			int oldX = getX();
			setLocation(arg0.getXOnScreen(), getLocation().y);
			setSize(getWidth() + oldX-arg0.getXOnScreen(), getHeight());
//			setBounds(getLocation().x, getLocation().y, getWidth() + oldX-getLocation().x, getHeight());
//			System.out.println(oldX-getLocation().x);
		}
		if(resizeB2T) {
			setSize(getWidth(), arg0.getYOnScreen()-getLocation().y);
		}if(resizeT2B) {
			int oldY = getY();
			setLocation(getLocation().x,arg0.getYOnScreen());
			setSize(getWidth(), getHeight() + oldY-arg0.getYOnScreen());
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if((arg0.getXOnScreen() < getLocation().x + getWidth() && arg0.getXOnScreen() > getLocation().x + getWidth()-10) ||
				(arg0.getXOnScreen() < getLocation().x + 10 && arg0.getXOnScreen() > getLocation().x)){
			setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
		}
		else if((arg0.getYOnScreen() < getLocation().y + getHeight() && arg0.getYOnScreen() > getLocation().y + getHeight()-10) ||
				(arg0.getYOnScreen() < getLocation().y + 10 && arg0.getYOnScreen() > getLocation().y)) {
			setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
		}else
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
		if(arg0.getXOnScreen() < getLocation().x + getWidth() && arg0.getXOnScreen() > getLocation().x + getWidth()-10)
			resizeR2L = true;
		else if (arg0.getXOnScreen() < getLocation().x + 10 && arg0.getXOnScreen() > getLocation().x)
			resizeL2R = true;
		else if (arg0.getYOnScreen() < getLocation().y + getHeight() && arg0.getYOnScreen() > getLocation().y + getHeight()-10)
			resizeB2T = true;
		else if (arg0.getYOnScreen() < getLocation().y + 10 && arg0.getYOnScreen() > getLocation().y)
			resizeT2B = true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		resizeT2B = false;
		resizeB2T = false;
		resizeL2R = false;
		resizeR2L = false;
	}
	
}
