package graphics;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.plaf.MenuBarUI;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import listeners.FrameMenuListener;

public class CreateMenu {
	
	private final FrameMenuListener menuListener = new FrameMenuListener(); //TODO check if actually need the variable 
	
	public JMenuBar createMenu() { // by adding action commands, one does not have to cast (which is rather difficult with different object classes sharing the same listener)

//		JMenu subMenu;
//		JRadioButtonMenuItem rbMenuItem; // This was commented out, due to the fact that menu options should not be focused on yet
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu;
		JMenuItem menuItem;
		
		menu = new JMenu("File");
		menu.getAccessibleContext().setAccessibleDescription("The menu for various shit");
		menu.setMnemonic(KeyEvent.VK_ALT);
		menuBar.add(menu);
		
		
		// New
		menuItem = new JMenuItem("New Project");
		menuItem.getAccessibleContext().setAccessibleDescription("Create new project and open the various windows");
		menuItem.setMnemonic(KeyEvent.VK_N);
		menuItem.setActionCommand("New Project");
		menu.add(menuItem);
		menuItem.addActionListener(menuListener);
		
		menu = new JMenu("Add");
		menu.getAccessibleContext().setAccessibleDescription("Add resources to the project");
		menuBar.add(menu);
		
		menuItem = new JMenuItem("Add new ???");
		menuItem.setMnemonic(KeyEvent.VK_T);
		menuItem.setActionCommand("Add ???");
		menuItem.addActionListener(menuListener);
		menu.add(menuItem);
		
		menuBar.add(menu);
		
		return menuBar;		
	}
	
	
}
