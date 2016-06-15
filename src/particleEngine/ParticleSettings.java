package particleEngine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class ParticleSettings extends JFrame {

	private int screenWidth = 520;
	private int screenHeight = 500;
	private Box box;
	private ColorChooser cc;
	private SettingsPanel sp;
	
	public ParticleSettings(String headerStr, Box b) {
		box = b;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		sp = new SettingsPanel(headerStr, screenWidth, screenHeight);
		add(sp);
		int x = Toolkit.getDefaultToolkit().getScreenSize().width-520;
		setBounds(x,0,520,500);
		setTitle("Settings");
//		pack();
	}
	
	public void update(double dt) {
		sp.getColorChooser().update(dt);
		box.setColor(sp.getColorChooser().getColor());
		System.out.println(sp.getColorChooser().getColor());
	}
	

	
	
	
	private class SettingsPanel extends JPanel {
		
		private ColorChooser cc;
		
		public SettingsPanel(String headerStr, int width, int height) {
			super();
			cc = new ColorChooser();
			SpringLayout spl = new SpringLayout();
			add(new ColorChooser());
			setLayout(spl);
			
		}
		public ColorChooser getColorChooser() {
			return cc;
		}
		
	}
	
}
