package data;

import javax.swing.JPanel;

import moveable.Player;
import graphics.MainFrame;

public class DecisionMaker {

	public Object decide(String actionCommand) {
		switch(actionCommand) {
			case "gif":
				System.out.println("HEJ");
				return MainFrame.getReader().readImage("ajax-loader.gif");
				
			case "move" :
				JPanel p = new JPanel();
				MainFrame.getRightPanel().add(new Player(30, 30, 100, 100, 2, 2));
				
		}
		return null;
	}
	
}
