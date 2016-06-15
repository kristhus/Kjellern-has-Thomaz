package handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FrameMenuListener implements ActionListener{


	public static boolean choiceWindow(Object various, String description) {
		int choice = JOptionPane.showConfirmDialog(null, various, description, JOptionPane.YES_NO_OPTION);
		if (choice == 0)
			return true;
		else
			return false;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) { // pre requisite that the events comes accomodated with an action command!
		System.out.println("Action performed");
		switch(e.getActionCommand()) {
		case "New Project":
			
			break;
		case "Add tileset":
			System.out.println("opening explorer, please wait");
			break;	
		}
	}
	
}
