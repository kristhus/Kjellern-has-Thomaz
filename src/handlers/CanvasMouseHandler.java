package handlers;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JComponent;

import events.CustomMouseEvent;
import graphics.MainFrame;
import graphics.ParticleCanvas;
import listeners.CustomMouseListener;

public class CanvasMouseHandler implements MouseMotionListener, MouseListener {

	
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
	
	
	//Acts as an intermediate to translate mouse events to non swing objects (won't disable for objects over)
	private ArrayList<CustomMouseListener> cmListenerList = new ArrayList<CustomMouseListener>();
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		doubleClick = arg0.getClickCount()%2 == 0;
		mousePosition = arg0.getPoint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(relativeToSource) {
			btnFromSource = true;
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
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
	
	
	public synchronized void addCustomMouseListener(CustomMouseListener listener) {
		if (!cmListenerList.contains(listener)) {
			cmListenerList.add(listener);
		}
	}
}
