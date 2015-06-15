package graphics;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class MainFrame extends JFrame{
	
	public static MainFrame mainFrame;
	public JSplitPane mainPanel;
	
	
	public static void main(String args[]) {
		mainFrame = new MainFrame();
	}
	
	public MainFrame() {
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
		
		
		setPreferredSize(new Dimension(800, 600));
		setVisible(true);
		
		mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new LeftPanel(), new RightPanel());
		add(mainPanel);
		pack();
		revalidate();
		
		
	}
	
}
