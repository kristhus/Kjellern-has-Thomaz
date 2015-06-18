package graphics;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ListItem extends JPanel implements MouseListener{

	public String pathName;
	public String title;
	public JLabel label;
	public String actionCommand;
	
	public ListItem (String pathName, String title, String actionCommand) {
		this.pathName = pathName;
		this.title = title;
		this.actionCommand = actionCommand;
		createLabel();
		revalidate();
		setBackground(Color.white);
		addMouseListener(this);
	}
	
	public void createLabel() {
		label = new JLabel(title);
		Font font = new Font("Arial", Font.BOLD, 14); // Manage fonts and size through user prefs?
		label.setFont(font);
		add(label);
	}
	
	
	public void mouseClicked(MouseEvent arg0) {
		// Perform action relevant to the item selected		
		if(MainFrame.getRightPanel().isClosed()) {
				MainFrame.getRightPanel().setVisible(true);
				MainFrame.getRightPanel().setBounds(150,0,600,600);
		}
		MainFrame.getRightPanel().decide(actionCommand);
		MainFrame.getMainPanel().revalidate();
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		setBackground(new Color(63, 152, 207));
		label.setForeground(Color.white);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		setBackground(Color.white);
		label.setForeground(Color.black);
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); 
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Create bevel effect
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Remove bevel effect
		
	}

}
