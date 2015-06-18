package listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardListener implements KeyListener {
	
	private boolean keyDown;
	private boolean keyLeftPressed; 
	private boolean keyUpPressed;
	private boolean keyRightPressed;
	private boolean keyDownPressed;

	public KeyBoardListener(){
		System.out.println("PENIS");
	}
	
	public void keyPressed(KeyEvent e) {
		
		System.out.println("PRESSED A KEY MOFO");
		keyDown = true; 
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT:
			keyLeftPressed = true;
			 break;
		case KeyEvent.VK_UP:
			keyUpPressed = true;
			 break;
		case KeyEvent.VK_RIGHT:
			keyRightPressed = true;
			 break;
		case KeyEvent.VK_DOWN:
			keyDownPressed = true;
			 break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		keyLeftPressed = keyUpPressed = keyRightPressed = keyDownPressed = false;
		keyDown = false; 
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean isKeyDown() {
		return keyDown;
	}

	public boolean isKeyLeftPressed() {
		return keyLeftPressed;
	}

	public boolean isKeyUpPressed() {
		return keyUpPressed;
	}

	public boolean isKeyRightPressed() {
		return keyRightPressed;
	}

	public boolean isKeyDownPressed() {
		return keyDownPressed;
	}

}
