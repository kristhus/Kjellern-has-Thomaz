package listeners;

import java.util.EventListener;
import events.CustomMouseEvent;

public interface CustomMouseListener extends EventListener {

	public void MouseClicked(CustomMouseEvent e);
	public void MouseDragged(CustomMouseEvent e);
	public void MouseEntered(CustomMouseEvent e);
	public void MouseExited(CustomMouseEvent e);
	
}
