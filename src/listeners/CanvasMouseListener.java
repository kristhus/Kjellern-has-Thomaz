package listeners;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

public class CanvasMouseListener implements MouseMotionListener, MouseListener {

	
	public Point mousePosition = new Point();
	
	public boolean leftMouseDown;
	public boolean rightMouseDown;
	public boolean doubleClick;
	public boolean relativeToSource;
	public boolean btnFromSource;
	public boolean waitForRelease;
	public boolean takenAction = true;
	
	public Point releasedAt = new Point();

	public JComponent externalSource;
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		doubleClick = arg0.getClickCount()%2 == 0;
		mousePosition = arg0.getPoint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Enter Sandman");
		if(relativeToSource) {
			btnFromSource = true;
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Exit Sandman");
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getButton()) {
		case MouseEvent.BUTTON1:
			leftMouseDown = true;
			break;
		case MouseEvent.BUTTON2:
			rightMouseDown = true;
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getButton()) {
		case MouseEvent.BUTTON1:
			leftMouseDown = false;
			break;
		case MouseEvent.BUTTON2:
			rightMouseDown = false;
			break;
		}
		mousePosition = arg0.getPoint();
		JComponent c = (JComponent) arg0.getSource();
//		releasedAt = new Point(c.getLocation())
		System.out.println("Btn released");
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mousePosition = arg0.getPoint();
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mousePosition = arg0.getPoint();
	}

}
