package handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	
	public boolean ctrlDown;
	public boolean hDown;
	
	
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getKeyCode()) {
			case KeyEvent.VK_CONTROL:
				ctrlDown = true;
	//		case KeyEvent.VK_H:
	//			hDown = true;
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getKeyCode()) {
			case KeyEvent.VK_CONTROL:
				ctrlDown = false;
	//		case KeyEvent.VK_H:
	//			hDown = false;
		}
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(Character.toUpperCase(arg0.getKeyChar())) {
			case 'H':
				hDown = !hDown;
		}
	}
	
}
