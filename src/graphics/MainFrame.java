package graphics;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import reader.Reader;

public class MainFrame extends JFrame{
	
	public static MainFrame mainFrame;
	public static JSplitPane mainPanel;
	public static Reader reader;
	
	public static void main(String args[]) {
		mainFrame = new MainFrame();
	}
	
	public MainFrame() {
		// SPLASH SCREEN  TODO: Add loading sequence
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
		setVisible(true);
		mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new LeftPanel(), new RightPanel());
		add(mainPanel);
		pack();
		revalidate();
		
		reader = new Reader();
	}
	
	public static Reader getReader() {
		return reader;
	}
	
	public static RightPanel getRightPanel() {
		return (RightPanel) mainPanel.getRightComponent();
	}
	public static LeftPanel getLeftPanel() {
		return (LeftPanel) mainPanel.getLeftComponent();
	}
	
}
