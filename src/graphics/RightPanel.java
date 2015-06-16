package graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import moveable.Player;

public class RightPanel extends JPanel{

	
	public RightPanel() {
		super();
		setBackground(Color.white);
		
	}
	
	public void decide(String actionCommand) {
		removeAll();
		switch(actionCommand) {
		case "gif":
			ImageIcon gif = MainFrame.getReader().readGif("/gifs/ajax-loader.gif");
			JLabel gifLabel = new JLabel();
			gifLabel.setIcon(gif);
			add(gifLabel);
			revalidate();
		}
	}

	public void add(Player player) {
		// TODO Auto-generated method stub
		add(player);
	}
	
}
