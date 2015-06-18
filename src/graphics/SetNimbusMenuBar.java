package graphics;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.MenuBarUI;

public class SetNimbusMenuBar extends MenuBarUI{

	@Override 
	public void installUI(JComponent c) {
	    try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.installUI(c);
	}
	
}
