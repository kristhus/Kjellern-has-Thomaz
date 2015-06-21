package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.DesktopPaneUI;

import listeners.KeyBoardListener;
import data.Updater;
import reader.Reader;

public class MainFrame extends JFrame{
	
	public static MainFrame mainFrame;
	public static JDesktopPane mainPanel;
	public static Reader reader;
	public static Updater updater;
	
	private static final boolean DEV_MODE = false;
	private static boolean DEV_MODE_INITIALIZED = false;
	private static KeyBoardListener keyBoardListener;
	public static final int FPS = 30;
	
	public static LeftPanel leftPanel;
	public static RightPanel rightPanel;
	
	public static void main(String args[]) {
		mainFrame = new MainFrame();
		reader = new Reader();
		updater = new Updater();
		updater.calculateFPS();
		
	}
	
	public MainFrame() {
		// SPLASH SCREEN  TODO: Add loading sequence
		super();
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
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(800, 600));
		JFrame.setDefaultLookAndFeelDecorated(true);
	//	mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("")));  //Need an icon
		setVisible(true);
		keyBoardListener = new KeyBoardListener();
		addKeyListener(keyBoardListener);
		mainPanel = new JDesktopPane();
		mainPanel.addKeyListener(MainFrame.getKeyBoardListener());
		mainPanel.setOpaque(true);       
        mainPanel.setFocusable(true);
        add(mainPanel);
        leftPanel = new LeftPanel();
        rightPanel = new RightPanel();
        mainPanel.add(leftPanel, new Integer(1));
        mainPanel.add(rightPanel, new Integer(1));
		pack();
		revalidate();
        
		CreateMenu cm = new CreateMenu();
		setJMenuBar(cm.createMenu());
	}
	
	public static void draw(Graphics g) {
		getRightPanel().draw(g);
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
	
}
