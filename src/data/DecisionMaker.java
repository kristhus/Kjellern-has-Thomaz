package data;

import graphics.MainFrame;

public class DecisionMaker {

	public Object decide(String actionCommand) {
		switch(actionCommand) {
			case "gif":
				System.out.println("HEJ");
				return MainFrame.getReader().readImage("ajax-loader.gif");
		}
		return null;
	}
	
}
